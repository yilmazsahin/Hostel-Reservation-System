package com.example.sahinhotel.EditItemsPages;

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

public class EditReservationsServicesController implements Initializable {
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
    private ComboBox<Integer> reservationServiceIdComboBox;
    @FXML
    private ComboBox<Integer> reservationIdComboBox;
    @FXML
    private ComboBox<String> serviceNameComboBox;
    @FXML
    private TextField tf_quantity;
    @FXML
    private TextField tf_searchReservationServiceById;
    @FXML
    private TextField tf_reservationId;
    @FXML
    private TextField tf_serviceName;
    @FXML
    private Button accommodationInvoiceButton;


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
        accommodationInvoiceButton.setOnAction(e -> ScreenManager.showCustomerPaymentsPage());
        populateReservationServiceIds();
        populateReservationIds();
        populateServiceNames();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void handleEditReservationsServicesButtonClick(ActionEvent event) {
        try {
            int reservationServiceId = reservationServiceIdComboBox.getValue();
            int reservationId = reservationIdComboBox.getValue();
            String serviceName = serviceNameComboBox.getValue();
            int quantity = Integer.parseInt(tf_quantity.getText());
            if (reservationServiceId == 0 || reservationId == 0 || serviceName == null || serviceName.isEmpty()) {
                DBUtils.showErrorAlert("Error", "Invalid Input", "Please fill in all fields.");
                return;
            }
            updateReservationsServices(reservationServiceId, reservationId, serviceName, quantity);
            clearFields();
            DBUtils.showSuccessAlert("Success", "ReservationService Updated", "Reservation service has been successfully updated.");

        } catch (NumberFormatException e) {
            DBUtils.showErrorAlert("Error", "Invalid Input", "Please enter valid numeric values.");
        }
    }
    private void populateReservationIds() {
        ObservableList<Integer> reservationIds = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y")) {
            String sql = "SELECT Id FROM reservations";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int reservationId = resultSet.getInt("Id");
                        reservationIds.add(reservationId);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        reservationIdComboBox.setItems(reservationIds);
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

    private void populateServiceNames() {
        ObservableList<String> serviceNames = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y")) {
            String sql = "SELECT ServiceName FROM services";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String serviceName = resultSet.getString("ServiceName");
                        serviceNames.add(serviceName);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        serviceNameComboBox.setItems(serviceNames);
    }

    private void updateReservationsServices(int reservationServiceId, int reservationId, String serviceName, int quantity) {
        int[] serviceInfo = getServiceIdAndServicePriceByName(serviceName);
        int serviceId = serviceInfo[0];
        double servicePrice = serviceInfo[1];

        if (serviceId != -1) {
            double unitPrice = servicePrice;
            double totalPrice = unitPrice * quantity;
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y")) {
                String sql = "UPDATE reservations_services SET ReservationId=?, ServiceId=?, ServiceName=?, UnitPrice=?, Quantity=?, TotalPrice=? WHERE ReservationServiceId=?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setInt(1, reservationId);
                    preparedStatement.setInt(2, serviceId);
                    preparedStatement.setString(3, serviceName);
                    preparedStatement.setDouble(4, unitPrice);
                    preparedStatement.setInt(5, quantity);
                    preparedStatement.setDouble(6, totalPrice);
                    preparedStatement.setInt(7, reservationServiceId);
                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        DBUtils.showSuccessAlert("Success", "Reservation service Updated", "Reservation service has been successfully updated.");
                    } else {
                        DBUtils.showErrorAlert("Error", "Failed to Update Reservation service", "Failed to update the Reservation service.");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                DBUtils.showErrorAlert("Error", "Failed to update Reservation services", "Failed to update the reservation service in the database");
            }
        } else {
            System.out.println("ServiceId not found for ServiceName: " + serviceName);
        }
    }

    private int[] getServiceIdAndServicePriceByName(String serviceName) {
        int[] result = {-1, -1};
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y")) {
            String sql = "SELECT ServiceId, ServicePrice FROM services WHERE ServiceName = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, serviceName);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        result[0] = resultSet.getInt("ServiceId");
                        result[1] = (int) resultSet.getDouble("ServicePrice");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    private void clearFields() {
        tf_quantity.clear();
        tf_searchReservationServiceById.clear();
        tf_reservationId.clear();
        tf_serviceName.clear();
    }
}
