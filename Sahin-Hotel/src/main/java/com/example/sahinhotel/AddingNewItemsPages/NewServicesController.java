package com.example.sahinhotel.AddingNewItemsPages;

import com.example.sahinhotel.DBUtils;
import com.example.sahinhotel.ScreenManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * @since 11/13/2023
 */

public class NewServicesController implements Initializable {
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
    private TextField serviceNameTextField;

    @FXML
    private TextField servicePriceTextField;


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

    }

    public void handleNewServicesButtonClick(ActionEvent event) {
        String serviceName = serviceNameTextField.getText();
        String servicePriceString = servicePriceTextField.getText();

        try {
            double servicePrice = Double.parseDouble(servicePriceString);
            addNewService(serviceName, servicePrice);
            DBUtils.showSuccessAlert("Success", "Service Added", "Service has been successfully added.");
            clearTextFields();
        } catch (NumberFormatException e) {
            DBUtils.showErrorAlert("Error", "Invalid Input", "Please enter a valid numeric value for Service Price.");
        }
    }
    private void addNewService(String serviceName, double servicePrice) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y")) {
            String sql = "INSERT INTO services (ServiceName, ServicePrice) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, serviceName);
                preparedStatement.setDouble(2, servicePrice);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            DBUtils.showErrorAlert("Error", "Failed to Add Service", "Failed to add the service to the database.");
        }
    }
    private void clearTextFields() {
        serviceNameTextField.clear();
        servicePriceTextField.clear();
    }
}
