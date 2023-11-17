package com.example.sahinhotel.ControllersPackage;

import com.example.sahinhotel.ReservationServices;
import com.example.sahinhotel.ScreenManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.example.sahinhotel.DBUtils;
import org.w3c.dom.Text;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @since 11/5/2023
 */

public class ReservationsServicesController implements Initializable {
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
    private TableView<ReservationServices> reservationsServicesTableView;

    @FXML
    private TableColumn<ReservationServices, Integer> columnReservationServiceId;
    @FXML
    private TableColumn<ReservationServices, Integer> columnReservationId;
    @FXML
    private TableColumn<ReservationServices, Integer> columnServiceId;
    @FXML
    private TableColumn<ReservationServices, String> columnServiceName;
    @FXML
    private TableColumn<ReservationServices, Double> columnUnitPrice;
    @FXML
    private TableColumn<ReservationServices, Integer> columnQuantity;
    @FXML
    private TableColumn<ReservationServices, Double> columnTotalPrice;
    @FXML
    private Button accommodationInvoiceButton;
    @FXML
    TextField tf_searchingReservationsServicesByServiceName;


    public void initialize() {
        columnReservationServiceId.setCellValueFactory(new PropertyValueFactory<>("ReservationServiceId"));
        columnReservationId.setCellValueFactory(new PropertyValueFactory<>("ReservationId"));
        columnServiceId.setCellValueFactory(new PropertyValueFactory<>("ServiceId"));
        columnServiceName.setCellValueFactory(new PropertyValueFactory<>("ServiceName"));
        columnUnitPrice.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
        columnQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        columnTotalPrice.setCellValueFactory(new PropertyValueFactory<>("TotalPrice"));
        List<ReservationServices> reservationServices = DBUtils.getAllReservationsServices();
        ObservableList<ReservationServices> observableReservationsServices = FXCollections.observableArrayList(reservationServices);
        reservationsServicesTableView.setItems(observableReservationsServices);


        buttonRooms.setOnAction(e -> ScreenManager.showRoomsPage());
        buttonFeatures.setOnAction(e -> ScreenManager.showFeaturesPage());
        buttonCustomers.setOnAction(e -> ScreenManager.showCustomersPage());
        buttonServices.setOnAction(e -> ScreenManager.showServicesPage());
        buttonDeleteReservationsServices.setOnAction(e -> ScreenManager.showDeleteReservationsServicesPage());
        buttonEditReservationsServices.setOnAction(e -> ScreenManager.showEditReservationsServicesPage());
        buttonNewReservationsServices.setOnAction(e -> ScreenManager.showNewReservationsServicesPage());
        buttonAllReservations.setOnAction(e -> ScreenManager.showAllReservations());
        accommodationInvoiceButton.setOnAction(e -> ScreenManager.showCustomerPaymentsPage());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<ReservationServices> reservationServices = DBUtils.getAllReservationsServices();
        if (reservationServices != null) {
            ObservableList<ReservationServices> observableReservationServices = FXCollections.observableArrayList(reservationServices);
            reservationsServicesTableView.setItems(observableReservationServices);

        }else {
            System.out.println("An error occurred while retrieving reservations services from the database.");
                DBUtils.showErrorAlert("Error", "No Data", "An error occurred while retrieving reservations services from the database.");
        }

    }


    public void handleSearchReservationServicesByServiceNameButtonClick(ActionEvent event) {
        String serviceName = tf_searchingReservationsServicesByServiceName.getText();
        if (serviceName != null) {
            List<ReservationServices> filteredReservationServices = getReservationServicesByServiceName(serviceName);
            ObservableList<ReservationServices> observableList = FXCollections.observableArrayList(filteredReservationServices);
            reservationsServicesTableView.setItems(observableList);
        } else DBUtils.showErrorAlert("Error", "Service not found", "Please enter a valid Service name.");
    }

    private List<ReservationServices> getReservationServicesByServiceName(String serviceName) {
        List<ReservationServices> reservationServices = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y");
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM reservations_services WHERE ServiceName=?");
        ) {
            statement.setString(1, serviceName);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int reservationServiceId = resultSet.getInt("ReservationServiceId");
                    int reservationId = resultSet.getInt("ReservationId");
                    int serviceId = resultSet.getInt("ServiceId");
                    String serviceNameFromDb = resultSet.getString("ServiceName");
                    double unitPrice = resultSet.getDouble("UnitPrice");
                    int quantity = resultSet.getInt("Quantity");
                    double totalPrice = resultSet.getDouble("TotalPrice");
                    ReservationServices reservationService = new ReservationServices(reservationServiceId, reservationId, serviceId, serviceNameFromDb, unitPrice, quantity, totalPrice);
                    reservationServices.add(reservationService);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservationServices;
    }
}
