package com.example.sahinhotel;

import com.example.sahinhotel.ControllersPackage.MainController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

import static com.example.sahinhotel.ScreenManager.*;

public class HelloApplication extends Application {
    private ObservableList<Room> roomsList = FXCollections.observableArrayList();
    private ObservableList<Customers> customersList = FXCollections.observableArrayList();
    private ObservableList<Reservations> reservationsList = FXCollections.observableArrayList();
    private ObservableList<Features> featuresList = FXCollections.observableArrayList();
    private ObservableList<Services> servicesList = FXCollections.observableArrayList();


    @Override
    public void start(Stage stage) throws IOException {
        ScreenManager.closeOpenStages();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-screen.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 800, 550);
        stage.setTitle("Reservations");  openStages.add(stage);
        stage.setScene(scene);


        roomsList.addAll(DBUtils.getAllRooms());
        customersList.addAll(DBUtils.getAllCustomers());
        reservationsList.addAll(DBUtils.getAllReservations());
        servicesList.addAll(DBUtils.getAllServices());
        featuresList.addAll(DBUtils.getAllFeatures());


        MainController mainController = fxmlLoader.getController();
        mainController.setRoomsButtonAction(() -> showRoomsPage());
        mainController.setFeaturesButtonAction(() -> showFeaturesPage());
        mainController.setServicesButtonAction(() -> showServicesPage());
        mainController.setCustomersButtonAction(() -> showCustomersPage());
        mainController.setButtonReservationAction(() -> showNewReservationPage());
        mainController.setButtonDeleteAction(() -> showDeleteReservationPage());
        mainController.setButtonEditAction(() -> showEditPage());
        mainController.setReservationsServicesButton(()->showReservationsServicesPage());
        mainController.setAccommodationInvoiceButton(()->showCustomerPaymentsPage());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}