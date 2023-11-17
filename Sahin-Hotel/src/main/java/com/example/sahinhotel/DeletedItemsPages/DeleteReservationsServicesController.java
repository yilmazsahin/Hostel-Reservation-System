package com.example.sahinhotel.DeletedItemsPages;

import com.example.sahinhotel.DBUtils;
import com.example.sahinhotel.ScreenManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

/**
 * @since 11/13/2023
 */

public class DeleteReservationsServicesController implements Initializable {
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
    private Button buttonDeleteReservationsServices;
    @FXML
    private Button buttonNewReservationsServices;
    @FXML
    private Button buttonEditReservationsServices;
    @FXML
    private Button reservationsServicesButton;
    @FXML
    private TextField tf_searchReservationServiceById;
    @FXML
    private ComboBox<Integer> reservationServiceIdComboBox;
    @FXML
    private Button accommodationInvoiceButton;
    //

    public void initialize() {
        buttonRooms.setOnAction(e -> ScreenManager.showRoomsPage());
        buttonFeatures.setOnAction(e -> ScreenManager.showFeaturesPage());
        buttonCustomers.setOnAction(e -> ScreenManager.showCustomersPage());
        buttonServices.setOnAction(e -> ScreenManager.showServicesPage());
        buttonDeleteReservationsServices.setOnAction(e -> ScreenManager.showDeleteReservationsServicesPage());
        buttonEditReservationsServices.setOnAction(e -> ScreenManager.showEditReservationsServicesPage());
        buttonNewReservationsServices.setOnAction(e -> ScreenManager.showNewReservationsServicesPage());
        buttonAllReservations.setOnAction(e -> ScreenManager.showAllReservations());
        reservationsServicesButton.setOnAction(e -> ScreenManager.showReservationsServicesPage());
        accommodationInvoiceButton.setOnAction(e->ScreenManager.showCustomerPaymentsPage());
        populateReservationServiceIds();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void handleDeleteReservationsServicesButtonClick(ActionEvent event) {
        try {
            int reservationServiceId = Integer.parseInt(tf_searchReservationServiceById.getText());
            deleteReservationsServices(reservationServiceId);
        } catch (NumberFormatException e) {
            DBUtils.showErrorAlert("Error", "Invalid Input", "Please enter a valid ReservationServiceId");
        }
    }

    private void deleteReservationsServices(int reservationServiceId) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y")) {
            String sql = "DELETE FROM reservations_services WHERE ReservationServiceId=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, reservationServiceId);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Reservation service deleted successfully.");
                    DBUtils.showSuccessAlert("Success", "Reservation service Deleted", "Reservation service has been successfully deleted.");
                } else {
                    System.out.println("Failed to delete Reservation service.");
                    DBUtils.showErrorAlert("Error", "Failed to Delete Reservation service", "Failed to delete the Reservation service.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            DBUtils.showErrorAlert("Error", "Failed to delete Reservation service", "Failed to delete the Reservation service from the database");
        }
    }

    private void populateReservationServiceIds() {
        ObservableList<Integer> reservationServiceIds = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y")) {
            String sql = "SELECT ReservationServiceId FROM reservations_services";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int reservationServiceId = resultSet.getInt("ReservationServiceId");
                        reservationServiceIds.add(reservationServiceId);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        reservationServiceIdComboBox.setItems(reservationServiceIds);
    }

}
