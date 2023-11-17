package com.example.sahinhotel.DeletedItemsPages;

import com.example.sahinhotel.DBUtils;
import com.example.sahinhotel.ScreenManager;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * @since 11/13/2023
 */

public class DeleteCustomerController implements Initializable {
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
    private TextField txt_searching;
    @FXML
    private Button buttonNewCustomer;
    @FXML
    private Button buttonEditCustomer;
    @FXML
    private Button buttonDeleteCustomer;

    @FXML
    private ComboBox<String> customersComboBox;
    @FXML
    private Button accommodationInvoiceButton;


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
        accommodationInvoiceButton.setOnAction(e -> ScreenManager.showCustomerPaymentsPage());
        populateCustomerComboBox();

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void handleDeleteCustomerButtonClick(ActionEvent event) {
        String selectedCustomer = customersComboBox.getValue();
        if (selectedCustomer != null) {
            deleteCustomer(selectedCustomer);
        }
    }

    private void populateCustomerComboBox() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y")) {
            String sql = "SELECT FullName FROM customers";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                var resultSet = preparedStatement.executeQuery();
                customersComboBox.setItems(FXCollections.observableArrayList());
                while (resultSet.next()) {
                    customersComboBox.getItems().add(resultSet.getString("FullName"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            DBUtils.showErrorAlert("Error", "Database Error", "An error occurred while accessing the database.");
        }
    }
    private void deleteCustomer(String selectedCustomer) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y")) {
            String sql = "DELETE FROM customers WHERE FullName=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, selectedCustomer);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Customer deleted successfully.");
                    DBUtils.showSuccessAlert("Success", "Customer Deleted", "Customer has been successfully deleted.");
                } else {
                    System.out.println("Failed to delete customer.");
                    DBUtils.showErrorAlert("Error", "Failed to Delete Customer", "Failed to delete the customer.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            DBUtils.showErrorAlert("Error", "Database Error", "An error occurred while accessing the database.");
        }
    }
}
