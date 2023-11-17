package com.example.sahinhotel.ControllersPackage;

import com.example.sahinhotel.DBUtils;
import com.example.sahinhotel.ScreenManager;
import com.example.sahinhotel.Services;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @since 10/25/2023
 */

public class ServiceController implements Initializable {
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
    private TableView<Services> serviceTableView;
    @FXML
    private TableColumn<Services, Integer> serviceId;
    @FXML
    private TableColumn<Services, String> serviceName;
    @FXML
    private TableColumn<Services, Double> servicePrice;
    @FXML
    private Button buttonNewService;
    @FXML
    private Button buttonEditService;
    @FXML
    private Button buttonDeleteService;



    public void initialize() {

        serviceId.setCellValueFactory(new PropertyValueFactory<>("ServiceId"));
        serviceName.setCellValueFactory(new PropertyValueFactory<>("ServiceName"));
        servicePrice.setCellValueFactory(new PropertyValueFactory<>("ServicePrice"));
        List<Services> services = DBUtils.getAllServices();
        ObservableList<Services> observableServices =FXCollections.observableArrayList(services);
        serviceTableView.setItems(observableServices);

        buttonRooms.setOnAction(e -> ScreenManager.showRoomsPage());
        buttonFeatures.setOnAction(e -> ScreenManager.showFeaturesPage());
        buttonCustomers.setOnAction(e -> ScreenManager.showCustomersPage());
        buttonServices.setOnAction(e -> ScreenManager.showServicesPage());
        buttonDeleteService.setOnAction(e -> ScreenManager.showDeleteServicesPage());
        buttonEditService.setOnAction(e -> ScreenManager.showEditServicesPage());
        buttonNewService.setOnAction(e -> ScreenManager.showNewServicePage());
        buttonAllReservations.setOnAction(e->ScreenManager.showAllReservations());
        reservationsServicesButton.setOnAction(e -> ScreenManager.showReservationsServicesPage());
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Services> services = DBUtils.getAllServices();
        if(services!=null){
            ObservableList<Services> observableServices =FXCollections.observableArrayList(services);
            serviceTableView.setItems(observableServices);
        }else {
            System.out.println("An error occurred while retrieving services fom the database");
        }
    }
}
