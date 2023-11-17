package com.example.sahinhotel.ControllersPackage;

import com.example.sahinhotel.Customers;
import com.example.sahinhotel.DBUtils;
import com.example.sahinhotel.Reservations;
import com.example.sahinhotel.ScreenManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @since 10/25/2023
 */

public class CustomerController implements Initializable {
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
    private TextField tf_searchCustomersByName;
    @FXML
    private TableView<Customers> customersTableView;
    @FXML
    private TableColumn<Customers, Integer> columnId;
    @FXML
    private TableColumn<Customers, String> fullName;
    @FXML
    private TableColumn<Customers, String> identityNumber;
    @FXML
    private TableColumn<Customers, String> phoneNumber;
    @FXML
    private TableColumn<Customers, Date> birthDate;
    @FXML
    private TableColumn<Customers, String> description;
    @FXML
    private Button buttonNewCustomer;
    @FXML
    private Button buttonEditCustomer;
    @FXML
    private Button buttonDeleteCustomer;
    @FXML
    private Button accommodationInvoiceButton;


    public void initialize() {
        columnId.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));
        fullName.setCellValueFactory(new PropertyValueFactory<>("FullName"));
        identityNumber.setCellValueFactory(new PropertyValueFactory<>("IdentityNumber"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("PhoneNumber"));
        birthDate.setCellValueFactory(new PropertyValueFactory<>("BirthDate"));
        description.setCellValueFactory(new PropertyValueFactory<>("Description"));
        List<Customers> customers = DBUtils.getAllCustomers();
        ObservableList<Customers> observableCustomers = FXCollections.observableArrayList(customers);
        customersTableView.setItems(observableCustomers);

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
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Customers> customers = DBUtils.getAllCustomers();
        if (customers != null) {
            ObservableList<Customers> observableCustomers = FXCollections.observableArrayList(customers);
            customersTableView.setItems(observableCustomers);
        } else {
            System.out.println("An error occurred while retrieving customers from the databases.");
        }
    }
    public void handleSearchCustomersByNameButtonClick(ActionEvent event) {
        String customerName = tf_searchCustomersByName.getText();
        if (customerName != null) {
            List<Customers> filteredCustomers = getCustomerListByName(customerName);
            ObservableList<Customers> observableFilteredCustomers = FXCollections.observableArrayList(filteredCustomers);
            customersTableView.setItems(observableFilteredCustomers);

        } else {
            DBUtils.showErrorAlert("Error", "Customer not found", "Please enter a valid customer name.");
        }
    }

    private List<Customers> getCustomerListByName(String customerName) {
        List<Customers> allCustomers = DBUtils.getAllCustomers();
        List<Customers> filteredCustomers = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y");
             PreparedStatement statement = connection.prepareStatement("Select*From customers WHERE FullName=?");) {
            statement.setString(1, customerName);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int customerId = resultSet.getInt("customerId");
                    String fullName = resultSet.getString("FullName");
                    String identityNumber = resultSet.getString("IdentityNumber");
                    String phoneNumber = resultSet.getString("PhoneNumber");
                    Date birthDate = resultSet.getDate("BirthDate");
                    String description = resultSet.getString("Description");
                    Customers customer = new Customers(customerId, fullName, identityNumber, phoneNumber, birthDate, description);
                    filteredCustomers.add(customer);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filteredCustomers;
    }
}
