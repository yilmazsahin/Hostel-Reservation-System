package com.example.sahinhotel.AddingNewItemsPages;

import com.example.sahinhotel.DBUtils;
import com.example.sahinhotel.ScreenManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;



public class NewReservationsServicesController implements Initializable {
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
    private TextField tf_reservationId;
    @FXML
    private TextField tf_serviceName;
    @FXML
    private TextField tf_quantity;
    @FXML
    private ComboBox<String> serviceNameComboBox;    @FXML
    private ComboBox<String> reservationIdComboBox;

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
        populateServiceNames();
        populateReservationIds();
    }

    private void populateReservationIds() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y")) {
            String sql = "SELECT Id FROM reservations";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int Id = resultSet.getInt("Id");
                        reservationIdComboBox.getItems().add(String.valueOf(Id));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            DBUtils.showErrorAlert("Error", "Database Error", "An error occurred while accessing the database.");
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void handleNewReservationsServicesButtonClick(ActionEvent event) {
        try {
            int reservationId = Integer.parseInt(tf_reservationId.getText());
            String serviceName = serviceNameComboBox.getValue();
            int quantity = Integer.parseInt(tf_quantity.getText());
            if (serviceName == null || serviceName.isEmpty()) {
                DBUtils.showErrorAlert("Error", "Service Name Not Selected", "Please select a service name.");
                return;
            }
            createReservationsService(reservationId, serviceName, quantity);
            clearFields();
            DBUtils.showSuccessAlert("Success", "Reservation Service Added", "Reservation service has been successfully added.");

        } catch (NumberFormatException e) {
            DBUtils.showErrorAlert("Error", "Invalid Input", "Please enter valid numeric values.");
        }
    }
private void createReservationsService(int reservationId, String serviceName, int quantity) {
    int[] serviceInfo = getServiceIdAndServicePriceByName(serviceName);
    int serviceId = serviceInfo[0];
    double servicePrice = serviceInfo[1];

    if (serviceId != -1) {
        double unitPrice = servicePrice;
        double totalPrice = unitPrice * quantity;

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y")) {
            String sql = "INSERT INTO reservations_services (ReservationId, ServiceId, ServiceName, UnitPrice, Quantity, TotalPrice) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, reservationId);
                preparedStatement.setInt(2, serviceId);
                preparedStatement.setString(3, serviceName);
                preparedStatement.setDouble(4, unitPrice);
                preparedStatement.setInt(5, quantity);
                preparedStatement.setDouble(6, totalPrice);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Reservation service created successfully.");
                    DBUtils.showSuccessAlert("Success", "Reservation service Created", "Reservation service has been successfully created.");
                } else {
                    System.out.println("Failed to create Reservation service.");
                    DBUtils.showErrorAlert("Error", "Failed to Create Reservation service", "Failed to create the Reservation service.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            DBUtils.showErrorAlert("Error", "Failed to create Reservation services", "Failed to create the reservation service in the database");
        }
    } else {
        System.out.println("ServiceId not found for ServiceName: " + serviceName);
    }
}

    private void populateServiceNames() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y")) {
            String sql = "SELECT ServiceName FROM services";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String serviceName = resultSet.getString("ServiceName");
                        serviceNameComboBox.getItems().add(serviceName);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            DBUtils.showErrorAlert("Error", "Database Error", "An error occurred while accessing the database.");
        }
    }
    private void clearFields() {
        tf_reservationId.clear();
        tf_serviceName.clear();
        tf_quantity.clear();
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
}
