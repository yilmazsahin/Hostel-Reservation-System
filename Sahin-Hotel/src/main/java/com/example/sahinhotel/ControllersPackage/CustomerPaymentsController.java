package com.example.sahinhotel.ControllersPackage;

import com.example.sahinhotel.CustomerPayments;
import com.example.sahinhotel.DBUtils;
import com.example.sahinhotel.ScreenManager;
import com.example.sahinhotel.ServiceDetail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


/**
 * @since 11/13/2023
 */

public class CustomerPaymentsController implements Initializable {
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
    private Button buttonReservation;
    @FXML
    private Button buttonEdit;
    @FXML
    private Button buttonDelete;
    @FXML
    private ComboBox<Integer> reservationIdComboBox;
    @FXML
    private Label customerNameLabel;
    @FXML
    private Label roomNameLabel;
    @FXML
    private Label roomPriceLabel;
    @FXML
    private Label stayedDaysLabel;
    @FXML
    private Label roomsWageLabel;
    @FXML
    private Label serviceNameLabel;
    @FXML
    private Label servicePriceLabel;
    @FXML
    private Label quantityLabel;
    @FXML
    private Label totalServiceWageLabel;
    @FXML
    private Label totalAmountLabel;
    private CustomerPayments model = new CustomerPayments();

    public void initialize() {

        buttonRooms.setOnAction(e -> ScreenManager.showRoomsPage());
        buttonFeatures.setOnAction(e -> ScreenManager.showFeaturesPage());
        buttonCustomers.setOnAction(e -> ScreenManager.showCustomersPage());
        buttonServices.setOnAction(e -> ScreenManager.showServicesPage());

        buttonDelete.setOnAction(e -> ScreenManager.showDeleteReservationPage());
        buttonEdit.setOnAction(e -> ScreenManager.showEditPage());
        buttonReservation.setOnAction(e -> ScreenManager.showNewReservationPage());
        buttonAllReservations.setOnAction(e -> ScreenManager.showAllReservations());
        reservationsServicesButton.setOnAction(e -> ScreenManager.showReservationsServicesPage());
        populateReservationIds();
        reservationIdComboBox.setOnAction(event -> calculateTotalAmount(reservationIdComboBox.getValue()));
        reservationIdComboBox.setOnAction(event -> setReservationDetailsFromDatabase());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        model = new CustomerPayments();
        reservationIdComboBox.setOnAction(event -> calculateTotalAmount(reservationIdComboBox.getValue()));
        reservationIdComboBox.setOnAction(event -> setReservationDetailsFromDatabase());
    }

    public void calculateTotalAmount(int reservationId) {
        reservationId = reservationIdComboBox.getValue();
        String serviceQuery = "SELECT ServiceName, UnitPrice, Quantity, TotalPrice FROM reservations_services WHERE ReservationId = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y");
             PreparedStatement serviceStatement = connection.prepareStatement(serviceQuery)) {

            serviceStatement.setInt(1, reservationId);
            try (ResultSet serviceResult = serviceStatement.executeQuery()) {
                double totalServiceWage = 0;
                while (serviceResult.next()) {
                    String serviceName = serviceResult.getString("ServiceName");
                    double unitPrice = serviceResult.getDouble("UnitPrice");
                    int quantity = serviceResult.getInt("Quantity");
                    double totalServicePrice = serviceResult.getDouble("TotalPrice");
                    model.setServiceName(serviceName);
                    model.setUnitPrice(unitPrice);
                    ;
                    model.setQuantity(quantity);
                    model.setTotalServicePrice(totalServicePrice);
                    totalServiceWage += totalServicePrice;
                }
                model.setTotalAmount(model.getTotalRoomWage() + totalServiceWage);
                updateLabels();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            DBUtils.showErrorAlert("Error", "Failed to calculate reservation invoice ", "Failed to calculate the reservation invoice because of database.");
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
            DBUtils.showErrorAlert("Error", "Failed to get data ", "Failed to get data from the database");
        }
        reservationIdComboBox.setItems(reservationIds);
    }

    private int calculateStayedDays(LocalDate checkedInDate, LocalDate checkedOutDate) {
        return (int) ChronoUnit.DAYS.between(checkedInDate, checkedOutDate);

    }

    private void setReservationDetailsFromDatabase() {
        int selectedReservationId = reservationIdComboBox.getValue();
        String reservationQuery = "SELECT r.*, c.FullName, rooms.Price " +
                "FROM reservations r " +
                "JOIN customers c ON r.customer_id = c.CustomerId " +
                "JOIN rooms ON r.Room = rooms.RoomName " +
                "WHERE r.Id = ?";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y");
             PreparedStatement reservationStatement = connection.prepareStatement(reservationQuery)) {
            reservationStatement.setInt(1, selectedReservationId);

            try (ResultSet reservationResult = reservationStatement.executeQuery()) {
                if (reservationResult.next()) {
                    model.setCustomerName(reservationResult.getString("FullName"));
                    model.setRoomName(reservationResult.getString("Room"));
                    model.setStayedDays(calculateStayedDays(reservationResult.getDate("Checked_In").toLocalDate(), reservationResult.getDate("Checked_Out").toLocalDate()));
                    model.setRoomPrice(reservationResult.getDouble("Price"));
                    model.setTotalRoomWage(model.getStayedDays() * model.getRoomPrice());

                    updateLabels();

                    List<ServiceDetail> serviceDetailsList = model.getServiceDetailsList(reservationIdComboBox.getValue());

                    serviceDetailsList.clear();
                    String serviceQuery = "SELECT * FROM reservations_services WHERE ReservationId = ?";
                    try (PreparedStatement serviceStatement = connection.prepareStatement(serviceQuery)) {
                        serviceStatement.setInt(1, selectedReservationId);
                        try (ResultSet serviceResult = serviceStatement.executeQuery()) {
                            while (serviceResult.next()) {
                                String serviceName = serviceResult.getString("ServiceName");
                                double servicePrice = serviceResult.getDouble("UnitPrice");
                                int quantity = serviceResult.getInt("Quantity");
                                double totalServicePrice = serviceResult.getDouble("TotalPrice");
                                serviceDetailsList.add(new ServiceDetail(serviceName, servicePrice, quantity, totalServicePrice));
                            }
                        }
                    }
                    double totalServiceWage = 0;
                    for (ServiceDetail serviceDetail : serviceDetailsList) {
                        totalServiceWage += serviceDetail.getTotalServiceWage();
                    }
                    model.setTotalServiceWage(totalServiceWage);
                    model.setTotalAmount(model.getTotalRoomWage() + totalServiceWage);
                    updateLabels();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            DBUtils.showErrorAlert("Error", "Failed to fetch reservation details", "Failed to fetch reservation details from the database.");
        }
    }

    private void updateLabels() {
        customerNameLabel.setText(model.getCustomerName());
        roomNameLabel.setText(model.getRoomName());
        roomPriceLabel.setText(String.valueOf(model.getRoomPrice()));
        stayedDaysLabel.setText(String.valueOf(model.getStayedDays()));
        roomsWageLabel.setText(String.valueOf(model.getTotalRoomWage()));
        StringBuilder serviceNameText = new StringBuilder();
        StringBuilder servicePriceText = new StringBuilder();
        StringBuilder quantityText = new StringBuilder();
        StringBuilder totalServiceWageText = new StringBuilder();
        for (ServiceDetail serviceDetail : model.getServiceDetailsList(reservationIdComboBox.getValue())) {
            serviceNameText.append(serviceDetail.getServiceName()).append("\n");
            servicePriceText.append(serviceDetail.getServicePrice()).append("\n");
            quantityText.append(serviceDetail.getQuantity()).append("\n");
            totalServiceWageText.append(serviceDetail.getTotalServiceWage()).append("\n");
        }
        serviceNameLabel.setText(serviceNameText.toString());
        servicePriceLabel.setText(servicePriceText.toString());
        quantityLabel.setText(quantityText.toString());
        totalServiceWageLabel.setText(totalServiceWageText.toString());
        totalAmountLabel.setText(String.valueOf(model.getTotalAmount()));
    }
}
