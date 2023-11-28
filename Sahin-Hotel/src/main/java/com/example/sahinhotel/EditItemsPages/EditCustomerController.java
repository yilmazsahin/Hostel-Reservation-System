package com.example.sahinhotel.EditItemsPages;

import com.example.sahinhotel.DBUtils;
import com.example.sahinhotel.ScreenManager;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

/**
 * @since 11/13/2023
 */

public class EditCustomerController implements Initializable {
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
    private Button buttonNewCustomer;
    @FXML
    private Button buttonEditCustomer;
    @FXML
    private Button buttonDeleteCustomer;
    @FXML
    private ComboBox<String> customersComboBox;
    @FXML
    private TextField tf_customerFullName;
    @FXML
    private TextField tf_customerIdentityNumber;
    @FXML
    private TextField tf_customerPhoneNumber;
    @FXML
    private DatePicker dt_customerBirthDate;
    @FXML
    private TextField tf_customerDescription;

    public void initialize() {
        buttonRooms.setOnAction(e -> ScreenManager.showRoomsPage());
        buttonFeatures.setOnAction(e -> ScreenManager.showFeaturesPage());
        buttonCustomers.setOnAction(e -> ScreenManager.showCustomersPage());
        buttonServices.setOnAction(e -> ScreenManager.showServicesPage());
        buttonDeleteCustomer.setOnAction(e -> ScreenManager.showDeleteCustomerPage());
        buttonEditCustomer.setOnAction(e -> ScreenManager.showEditCustomerPage());
        buttonNewCustomer.setOnAction(e -> ScreenManager.showNewCustomerPage());
        buttonAllReservations.setOnAction(e -> ScreenManager.showAllReservations());
        reservationsServicesButton.setOnAction(e -> ScreenManager.showReservationsServicesPage());
        populateCustomerComboBox();

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void handleEditCustomerButtonClick(ActionEvent event) {
        String selectedCustomer = customersComboBox.getValue();
        String fullName = tf_customerFullName.getText();
        String identityNumber = tf_customerIdentityNumber.getText();
        String phoneNumber = tf_customerPhoneNumber.getText();
        String birthDate = (dt_customerBirthDate.getValue() != null) ? dt_customerBirthDate.getValue().toString() : null;
        String description = tf_customerDescription.getText();
        editCustomer(selectedCustomer, fullName, identityNumber, phoneNumber, birthDate, description);
    }
    private void editCustomer(String selectedCustomer, String fullName, String identityNumber, String phoneNumber, String birthDate, String description) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y")) {
            String sql = "UPDATE customers SET FullName=?, IdentityNumber=?, PhoneNumber=?, BirthDate=?, Description=? WHERE FullName=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, fullName);
                preparedStatement.setString(2, identityNumber);
                preparedStatement.setString(3, phoneNumber);
                preparedStatement.setString(4, birthDate);
                preparedStatement.setString(5, description);
                preparedStatement.setString(6, selectedCustomer);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Customer updated successfully.");
                    DBUtils.showSuccessAlert("Success", "Customer Updated", "Customer has been successfully updated.");
                } else {
                    System.out.println("Failed to update customer.");
                    DBUtils.showErrorAlert("Error", "Failed to Update Customer", "Failed to update the customer.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            DBUtils.showErrorAlert("Error", "Database Error", "An error occurred while accessing the database.");
        }
    }

    private void populateCustomerComboBox() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y")) {
            String sql = "SELECT FullName FROM customers";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    customersComboBox.setItems(FXCollections.observableArrayList());
                    while (resultSet.next()) {
                        customersComboBox.getItems().add(resultSet.getString("FullName"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            DBUtils.showErrorAlert("Error", "Database Error", "An error occurred while accessing the database.");
        }
    }
}
