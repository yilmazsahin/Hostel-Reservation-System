package com.example.sahinhotel.ControllersPackage;

import com.example.sahinhotel.DBUtils;
import com.example.sahinhotel.Features;
import com.example.sahinhotel.ScreenManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @since 10/25/2023
 */

public class FeatureController implements Initializable {
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
    private TableView<Features> featuresTableView;
    @FXML
    private TableColumn<Features, Integer> featureId;
    @FXML
    private TableColumn<Features, String> featureName;
    @FXML
    private Button buttonNewFeature;
    @FXML
    private Button buttonEditFeature;
    @FXML
    private Button buttonDeleteFeature;

    public void initialize() {
        featureId.setCellValueFactory(new PropertyValueFactory<>("FeatureId"));
        featureName.setCellValueFactory(new PropertyValueFactory<>("FeatureName"));
        List<Features> features = DBUtils.getAllFeatures();
        ObservableList<Features> observableFeatures = FXCollections.observableArrayList(features);
        featuresTableView.setItems(observableFeatures);

        buttonRooms.setOnAction(e -> ScreenManager.showRoomsPage());
        buttonFeatures.setOnAction(e -> ScreenManager.showFeaturesPage());
        buttonCustomers.setOnAction(e -> ScreenManager.showCustomersPage());
        buttonServices.setOnAction(e -> ScreenManager.showServicesPage());
        buttonDeleteFeature.setOnAction(e -> ScreenManager.showDeleteFeaturePage());
        buttonEditFeature.setOnAction(e -> ScreenManager.showEditFeaturePage());
        buttonNewFeature.setOnAction(e -> ScreenManager.showNewFeaturePage());
        buttonAllReservations.setOnAction(e -> ScreenManager.showAllReservations());
        reservationsServicesButton.setOnAction(e -> ScreenManager.showReservationsServicesPage());

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Features> features = DBUtils.getAllFeatures();
        if (features != null) {
            ObservableList<Features> observableFeatures = FXCollections.observableArrayList(features);
            featuresTableView.setItems(observableFeatures);
        } else {
            System.out.println("An error occurred while retrieving features from the database.");
        }
    }
}
