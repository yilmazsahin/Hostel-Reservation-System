package com.example.sahinhotel.EditItemsPages;

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
import java.sql.*;
import java.util.ResourceBundle;

/**
 * @since 11/12/2023
 */

public class EditFeatureController implements Initializable {
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
    private TextField newFeatureTextField;
    @FXML
    private ComboBox<String> featuresComboBox;
    @FXML
    private Button buttonNewFeature;
    @FXML
    private Button buttonEditFeature;
    @FXML
    private Button buttonDeleteFeature;

    public void initialize() {
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
        initializeComboBox();
        featuresComboBox.setOnAction(event -> handleComboBoxSelection());
    }
    private void handleComboBoxSelection() {
    }
    private void initializeComboBox() {
        ObservableList<String> featureName = getAllFeatures();
        featuresComboBox.setItems(featureName);
    }
    private ObservableList<String> getAllFeatures() {
        ObservableList<String> featureNames = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y")) {
            String sql = "SELECT FeatureName FROM features";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String featureName = resultSet.getString("FeatureName");
                        featureNames.add(featureName);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return featureNames;
    }
    @FXML
    public void handleEditFeatureButtonClick(ActionEvent event) {
        String selectedFeature = featuresComboBox.getSelectionModel().getSelectedItem();
        String newFeatureName = newFeatureTextField.getText();

        if (selectedFeature != null && !newFeatureName.isEmpty()) {
            updateFeature(selectedFeature, newFeatureName);
        } else {
            DBUtils.showErrorAlert("Error", "Invalid Input", "Please select a feature and enter a valid new feature name.");
        }
    }

    private void updateFeature(String selectedFeature, String newFeatureName) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y")) {
            String sql = "UPDATE features SET FeatureName = ? WHERE FeatureName = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, newFeatureName);
                preparedStatement.setString(2, selectedFeature);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    DBUtils.showSuccessAlert("Success", "Feature Updated", "Feature has been successfully updated.");
                    initializeComboBox();
                } else {
                    DBUtils.showErrorAlert("Error", "Failed to Update Feature", "Failed to update the feature.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

