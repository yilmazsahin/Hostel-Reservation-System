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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * @since 11/13/2023
 */

public class DeleteServicesController implements Initializable {
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
    private TextField tf_searching;
    @FXML
    private Button buttonNewService;
    @FXML
    private Button buttonEditService;
    @FXML
    private Button buttonDeleteService;
    @FXML
    private ComboBox<String> servicesComboBox;

    @FXML
    private TextField deleteServiceTextField;

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
        servicesComboBox.setItems(serviceNames);

    }


    public void handleDeleteServicesButtonClick(ActionEvent event) {
        String selectedService = (servicesComboBox.getValue() != null) ? servicesComboBox.getValue() : deleteServiceTextField.getText();
        deleteService(selectedService);
        DBUtils.showConfirmationAlert("Success", "Service Deleted", "Service has been successfully deleted.");
        ObservableList<String> updatedServiceNames = getAllServiceNames();
        servicesComboBox.setItems(updatedServiceNames);

    }

    private void deleteService(String serviceName) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y")) {
            String sql = "DELETE FROM services WHERE ServiceName = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, serviceName);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            DBUtils.showErrorAlert("Error", "Failed to Delete Service", "Failed to delete the service from the database.");
        }
    }

    private ObservableList<String> getAllServiceNames() {
        ObservableList<String> serviceNames = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y")) {
            String sql = "SELECT ServiceName FROM services";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (var resultSet = preparedStatement.executeQuery()) {
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

