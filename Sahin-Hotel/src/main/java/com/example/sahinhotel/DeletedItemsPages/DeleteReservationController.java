package com.example.sahinhotel.DeletedItemsPages;

import com.example.sahinhotel.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


public class DeleteReservationController implements Initializable {
    @FXML
    private Button buttonRooms;
    @FXML
    private Button buttonFeatures;
    @FXML
    private Button buttonServices;
    @FXML
    private Button buttonCustomers;
    @FXML
    private Button buttonAllReservations;

    @FXML
    private Button buttonReservation;
    @FXML
    private Button deleteReservationButton;
    @FXML
    private Button buttonEdit;
    @FXML
    private Button buttonDelete;
    @FXML
    private Button reservationsServicesButton;
    @FXML
    TextField tf_searchingByReservationId;
    @FXML
    TextField tf_searchingByCustomerName;
    @FXML
    public ComboBox<Customers> customersComboBox;
    @FXML
    public ComboBox<Reservations> reservationsComboBox;
    @FXML
    private Button accommodationInvoiceButton;

    private ObservableList<Reservations> reservationsList = FXCollections.observableArrayList();

    public void initialize() {
        populateReservationsAndCustomersComboBoxes();
        buttonRooms.setOnAction(e -> ScreenManager.showRoomsPage());
        buttonFeatures.setOnAction(e -> ScreenManager.showFeaturesPage());
        buttonCustomers.setOnAction(e -> ScreenManager.showCustomersPage());
        buttonServices.setOnAction(e -> ScreenManager.showServicesPage());
        buttonDelete.setOnAction(e -> ScreenManager.showDeleteReservationPage());
        buttonEdit.setOnAction(e -> ScreenManager.showEditPage());
        buttonReservation.setOnAction(e -> ScreenManager.showNewReservationPage());
        buttonAllReservations.setOnAction(e -> ScreenManager.showAllReservations());
        reservationsServicesButton.setOnAction(e -> ScreenManager.showReservationsServicesPage());
        accommodationInvoiceButton.setOnAction(e -> ScreenManager.showCustomerPaymentsPage());
        deleteReservationButton.setOnAction(event -> handleDeleteReservationButtonClick());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateReservationsAndCustomersComboBoxes();
    }

    public void handleDeleteReservationButtonClick() {
        String reservationIdText = tf_searchingByReservationId.getText();
        String customerName = tf_searchingByCustomerName.getText();
        if (!reservationIdText.isEmpty()) {
            try {
                int id = Integer.valueOf(tf_searchingByReservationId.getText());
                Reservations reservationToDeleteById = getReservationById(id);

                if (reservationToDeleteById != null) {
                    boolean deleteConfirmation = showDeleteConfirmationAlert();
                    if (deleteConfirmation) {
                        boolean success = deleteReservationById(id);
                        if (success) {
                            Room room = reservationToDeleteById.getRoom();
                            room.releaseRoom();
                            DBUtils.updateRoomAvailableRooms(room.getRoomId(), 1);
                            DBUtils.showErrorAlert("Success", "Reservation Deleted", "Reservation has been successfully deleted.");

                        } else {
                            DBUtils.showErrorAlert("Error", "Deletion Failed", "Failed to delete the reservation. Please try again.");
                        }
                        return;
                    }
                } else {
                    DBUtils.showErrorAlert("Error", "Reservation Not Found", "Reservation with ID " + id + " was not found.");
                }
            } catch (NumberFormatException e) {
                DBUtils.showErrorAlert("Error", "Invalid Reservation ID", "Please enter a valid reservation ID.");
            }
        }
        if (!customerName.isEmpty()) {
            Reservations reservationToDeleteByName = getReservationByCustomerName(customerName);
            if (reservationToDeleteByName != null) {
                boolean deleteConfirmation = showDeleteConfirmationAlert();
                if (deleteConfirmation) {
                    boolean success = deleteReservation(reservationToDeleteByName);
                    if (success) {
                        DBUtils.showErrorAlert("Success", "Reservation Deleted", "Reservation has been successfully deleted.");
                    } else {
                        DBUtils.showErrorAlert("Error", "Deletion Failed", "Failed to delete the reservation. Please try again.");
                    }
                }
            } else {
                DBUtils.showErrorAlert("Error", "Reservation Not Found", "Reservation with ID " + customerName + " was not found.");
            }
        }
    }
    public static boolean deleteReservationById(int reservationId) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y");
             PreparedStatement statement = connection.prepareStatement("DELETE FROM reservations WHERE Id = ?")) {

            statement.setInt(1, reservationId);
            DBUtils.deleteReservationServicesByReservationId(reservationId);
            int affectedRows = statement.executeUpdate();
            DBUtils.showConfirmationAlert("Success", "Reservation  deleted.", "Reservation has been successfully deleted.");
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();

            DBUtils.showErrorAlert("Unsuccessful", "Reservation  couldn't deleted.", "Reservation  hasn't been successfully deleted.Please try again.");
            return false;
        }
    }
    public Reservations getReservationByCustomerName(String customerName) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Reservations reservation = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y");
            String sql = "SELECT *, customer_id FROM reservations  INNER JOIN customers  ON customer_id = customerId WHERE FullName = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, customerName);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("Id");
                String roomName = resultSet.getString("Room");
                LocalDate checkInDate = resultSet.getDate("CheckIn_Date").toLocalDate();
                LocalDate checkOutDate = resultSet.getDate("CheckOut_Date").toLocalDate();
                LocalDate checkedIn = resultSet.getDate("Checked_In").toLocalDate();
                LocalDate checkedOut = resultSet.getDate("Checked_Out").toLocalDate();
                int customerId = resultSet.getInt("customer_id");

                reservation = new Reservations(id, roomName, checkInDate, checkOutDate, checkedIn, checkedOut, customerId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return reservation;
    }


    public boolean showDeleteConfirmationAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText("Delete Confirmation");
        alert.setContentText("Are you sure you want to delete this reservation?");
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    public boolean deleteReservation(Reservations reservationToDelete) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean success = false;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y");
            String sql = "DELETE FROM reservations WHERE Id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, reservationToDelete.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            success = rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }


    private void populateReservationsAndCustomersComboBoxes() {
        reservationsComboBox.setConverter(new StringConverter<Reservations>() {
            @Override
            public String toString(Reservations reservation) {
                return reservation != null ? String.valueOf(reservation.getId()) : null;
            }

            @Override
            public Reservations fromString(String string) {
                if (string == null || string.trim().isEmpty()) {
                    return null;
                }
                int reservationId = Integer.parseInt(string);
                for (Reservations reservation : reservationsList) {
                    if (reservation.getId() == reservationId) {
                        return reservation;
                    }
                }
                return null;
            }
        });
        List<Reservations> allReservations = DBUtils.getAllReservations();
        ObservableList<Reservations> reservationOptions = FXCollections.observableArrayList(allReservations);
        reservationsComboBox.setItems(reservationOptions);
        reservationsComboBox.setConverter(new StringConverter<Reservations>() {
            @Override
            public String toString(Reservations reservation) {
                return reservation != null ? String.valueOf(reservation.getId()) : null;
            }

            @Override
            public Reservations fromString(String string) {
                return null;
            }
        });


        List<Customers> allCustomersWithReservations = DBUtils.getCustomersWithReservations();
        ObservableList<Customers> customerOptions = FXCollections.observableArrayList(allCustomersWithReservations);
        customersComboBox.setItems(customerOptions);

        customersComboBox.setConverter(new StringConverter<Customers>() {
            @Override
            public String toString(Customers customer) {
                return customer != null ? customer.getFullName() : null;
            }

            @Override
            public Customers fromString(String string) {
                return null;
            }
        });
    }

    private Reservations getReservationById(int Id) {
        List<Reservations> allReservations = DBUtils.getAllReservations();
        for (Reservations reservation : allReservations) {
            if (reservation.getId() == Id) {
                return reservation;
            }
        }
        return null;
    }
}
