package com.example.sahinhotel.EditItemsPages;

import com.example.sahinhotel.*;
import com.example.sahinhotel.RoomTypes.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @since 10/26/2023
 */

public class EditReservationController implements Initializable {

    @FXML
    TextField tf_searchingByReservationId;
    @FXML
    private TextField roomNameTextField;
    @FXML
    public ComboBox<Room> roomComboBox;
    @FXML
    private TextField customerNameTextField;
    @FXML
    public ComboBox<Customers> customersComboBox;
    @FXML
    private DatePicker checkInDatePicker;
    @FXML
    private DatePicker checkOutDatePicker;
    @FXML
    private DatePicker checkedInPicker;
    @FXML
    private DatePicker checkedOutPicker;
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
    private Button buttonEdit;
    @FXML
    private Button buttonDelete;
    @FXML
    private Button reservationsServicesButton;
    @FXML
    private Button accommodationInvoiceButton;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateRoomAndCustomerComboBoxes();
    }

    public void initialize() {
        populateRoomAndCustomerComboBoxes();
        roomNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            Room selectedRoom = findRoomByName(newValue);
            roomComboBox.setValue(selectedRoom);
        });
        customerNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            String customerText = customerNameTextField.getText();
            if (customerText != null && customerText.matches("\\\\d+:\\\\s*[\\\\p{L}]+\\\\s+[\\\\p{L}]+")) {
                String[] parts = customerText.split(":");
                int customerId = Integer.parseInt(parts[0].trim());

                Customers selectedCustomer = findCustomerById(customerId);
                if (selectedCustomer != null) {
                    customersComboBox.setValue(selectedCustomer);
                }
            }
        });


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
    }

    private Room findRoomByName(String roomName) {
        List<Room> allRooms = DBUtils.getAllRooms();
        for (Room room : allRooms) {
            if (room.getRoomName().equalsIgnoreCase(roomName)) {
                return room;
            }
        }
        return null;
    }

    private Customers findCustomerById(int customerId) {
        List<Customers> allCustomers = DBUtils.getAllCustomers();
        for (Customers customer : allCustomers) {
            if (customer.getCustomerId() == customerId) {
                return customer;
            }
        }
        return null;
    }

    private void populateRoomAndCustomerComboBoxes() {
        roomComboBox.setConverter(new StringConverter<Room>() {
            @Override
            public String toString(Room room) {
                return room != null ? room.getRoomName() : null;
            }

            @Override
            public Room fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    Room selectedRoom = findRoomByName(string);
                    return selectedRoom;
                } else {
                    return null;
                }
            }
        });
        List<Room> rooms = DBUtils.getAllRooms();
        ObservableList<Room> roomOptions = FXCollections.observableArrayList(rooms);
        roomComboBox.setItems(roomOptions);
        StringConverter<Customers> customersConverter = new StringConverter<>() {
            @Override
            public String toString(Customers customer) {
                if (customer != null) {
                    return customer.getCustomerId() + ": " + customer.getFullName();
                }
                return null;
            }

            @Override
            public Customers fromString(String string) {
                return null;
            }
        };
        List<Customers> customers = DBUtils.getAllCustomers();
        ObservableList<Customers> customerOptions = FXCollections.observableArrayList(customers);
        customersComboBox.setItems(customerOptions);
        customersComboBox.setConverter(customersConverter);
        customersComboBox.setOnAction(event -> {
            Customers selectedCustomer = customersComboBox.getValue();
            if (selectedCustomer != null) {
                customerNameTextField.setText(selectedCustomer.getCustomerId() + ":" + selectedCustomer.getFullName());
            } else {
                customerNameTextField.clear();
            }
        });
    }

    @FXML
    private void handleUpdateReservationButtonClick(ActionEvent event) {
        Room selectedRoom = roomComboBox.getValue();
        LocalDate checkInDateValue = checkInDatePicker.getValue();
        LocalDate checkOutDateValue = checkOutDatePicker.getValue();
        LocalDate checkedInDateValue = checkedInPicker.getValue();
        LocalDate checkedOutDateValue = checkedOutPicker.getValue();
        String customerInfo = customerNameTextField.getText();


        int customerId;
        String fullName;
        if (customerInfo != null && customerInfo.matches("\\d+:\\s*[\\p{L}]+\\s+[\\p{L}]+")) {
            String[] parts = customerInfo.split(":");
            customerId = Integer.parseInt(parts[0].trim());
            fullName = parts[1].trim();
        } else {
            DBUtils.showErrorAlert("Error", "Invalid Customer Format", "Customer format should be in the form of 'ID: Full Name'.");
            return;
        }
        Customers selectedCustomer = new Customers(customerId, fullName);
        int reservationId = getReservationIdFromUserInput();
        Reservations existingReservation = getReservationById(reservationId);
        if (existingReservation != null) {
            existingReservation.setRoom(selectedRoom);
            existingReservation.setCheckInDate(checkInDateValue);
            existingReservation.setCheckOutDate(checkOutDateValue);
            existingReservation.setCheckedIn(checkedInDateValue);
            existingReservation.setCheckedOut(checkedOutDateValue);
            existingReservation.setCustomerId(customerId);

            boolean success = updateReservation(existingReservation);
            if (success) {
                DBUtils.showErrorAlert("Success", "Reservation Updated", "Reservation has been successfully updated.");
            } else {
                DBUtils.showErrorAlert("Error", "Update Failed", "Failed to update the reservation. Please try again.");
            }
        } else {
            DBUtils.showErrorAlert("Error", "Reservation Not Found", "Reservation with ID " + reservationId + " was not found.");
        }
    }

    public boolean updateReservation(Reservations existingReservation) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean success = false;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y");
            String sql = "UPDATE reservations SET Room=?, CheckIn_Date=?, CheckOut_Date=?, Checked_In=?, Checked_Out=?, customer_id=? WHERE Id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, existingReservation.getRoom().getRoomId());
            preparedStatement.setDate(2, Date.valueOf(existingReservation.getCheckInDate()));
            preparedStatement.setDate(3, Date.valueOf(existingReservation.getCheckOutDate()));
            preparedStatement.setDate(4, Date.valueOf(existingReservation.getCheckedIn()));
            preparedStatement.setDate(5, Date.valueOf(existingReservation.getCheckedOut()));
            preparedStatement.setInt(6, existingReservation.getCustomerId());
            preparedStatement.setInt(7, existingReservation.getId());
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


    public Reservations getReservationById(int reservationId) {
        Reservations reservation = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y");
            String sql = "SELECT * FROM reservations WHERE Id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, reservationId);
            resultSet = preparedStatement.executeQuery();


            if (resultSet.next()) {


                int id = resultSet.getInt("Id");
                String roomName = resultSet.getString("Room");
                int customer = resultSet.getInt("customer_id");
                Room room;
                switch (roomName) {
                    case "Single Room":
                        room = new SingleRoom();
                        break;
                    case "Double Room":
                        room = new DoubleRoom();
                        break;
                    case "Accessible Room":
                        room = new AccessibleRoom();
                        break;
                    case "Family Room":
                        room = new FamilyRoom();
                        break;
                    case "Honeymoon Room":
                        room = new HoneymoonRoom();
                        break;
                    case "Junior Suite":
                        room = new JuniorSuite();
                        break;
                    case "King Suite":
                        room = new KingSuite();
                        break;
                    case "Triple Room":
                        room = new TripleRoom();
                        break;
                    default:
                        room = new SingleRoom();
                        break;
                }
                Customers customerId = new Customers(customer);
                LocalDate checkInDate = resultSet.getDate("CheckIn_Date").toLocalDate();
                LocalDate checkOutDate = resultSet.getDate("CheckOut_Date").toLocalDate();
                LocalDate checkedIn = resultSet.getDate("Checked_In").toLocalDate();
                LocalDate checkedOut = resultSet.getDate("Checked_Out").toLocalDate();
                reservation = new Reservations(id, room, checkInDate, checkOutDate, checkedIn, checkedOut, customerId.getCustomerId());
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


    private int getReservationIdFromUserInput() {
        String userInput = tf_searchingByReservationId.getText().trim();

        try {
            return Integer.parseInt(userInput);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return -1;
        }
    }

}
