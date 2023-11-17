package com.example.sahinhotel.EditItemsPages;

import com.example.sahinhotel.DBUtils;
import com.example.sahinhotel.ScreenManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

/**
 * @since 11/13/2023
 */

public class EditServicesController implements Initializable {
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
    private Button reservationsServicesButton;
    @FXML
    private Button buttonNewService;
    @FXML
    private Button buttonEditService;
    @FXML
    private Button buttonDeleteService;
    @FXML
    private ComboBox<String> serviceComboBox;

    @FXML
    private TextField editServiceNameTextField;

    @FXML
    private TextField editServicePriceTextField;

    public void initialize() {
        buttonRooms.setOnAction(e -> ScreenManager.showRoomsPage());
        buttonFeatures.setOnAction(e -> ScreenManager.showFeaturesPage());
        buttonCustomers.setOnAction(e -> ScreenManager.showCustomersPage());
        buttonServices.setOnAction(e -> ScreenManager.showServicesPage());
        buttonDeleteService.setOnAction(e -> ScreenManager.showDeleteServicesPage());
        buttonEditService.setOnAction(e -> ScreenManager.showEditServicesPage());
        buttonNewService.setOnAction(e -> ScreenManager.showNewServicePage());
        buttonAllReservations.setOnAction(e -> ScreenManager.showAllReservations());
        reservationsServicesButton.setOnAction(e -> ScreenManager.showReservationsServicesPage());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> serviceNames = getAllServiceNames();
        serviceComboBox.setItems(serviceNames);
    }

    @FXML
    private void handleEditServicesButtonClick() {
        String selectedService = serviceComboBox.getValue();
        String newServiceName = editServiceNameTextField.getText();
        String newServicePriceString = editServicePriceTextField.getText();

        try {
            double newServicePrice = Double.parseDouble(newServicePriceString);
            updateService(selectedService, newServiceName, newServicePrice);
            DBUtils.showSuccessAlert("Success", "Service Updated", "Service has been successfully updated.");
            ObservableList<String> updatedServiceNames = getAllServiceNames();
            serviceComboBox.setItems(updatedServiceNames);
        } catch (NumberFormatException e) {
            DBUtils.showErrorAlert("Error", "Invalid Input", "Please enter a valid numeric value for New Service Price.");
        }
    }

    private void updateService(String selectedService, String newServiceName, double newServicePrice) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y")) {
            String sql = "UPDATE services SET ServiceName = ?, ServicePrice = ? WHERE ServiceName = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, newServiceName);
                preparedStatement.setDouble(2, newServicePrice);
                preparedStatement.setString(3, selectedService);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            DBUtils.showErrorAlert("Error", "Failed to Update Service", "Failed to update the service in the database.");
        }
    }

    private ObservableList<String> getAllServiceNames() {
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
        return serviceNames;
    }
}
