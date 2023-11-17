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
import java.sql.*;
import java.util.ResourceBundle;

/**
 * @since 11/12/2023
 */

public class DeleteFeatureController implements Initializable {
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
    private TextField featureNameTextField;
    @FXML
    private ComboBox<String> featuresComboBox;

    @FXML
    private Button buttonNewFeature;
    @FXML
    private Button buttonEditFeature;
    @FXML
    private Button buttonDeleteFeature;

    public void initialize() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonRooms.setOnAction(e -> ScreenManager.showRoomsPage());
        buttonFeatures.setOnAction(e -> ScreenManager.showFeaturesPage());
        buttonCustomers.setOnAction(e -> ScreenManager.showCustomersPage());
        buttonServices.setOnAction(e -> ScreenManager.showServicesPage());
        buttonDeleteFeature.setOnAction(e -> ScreenManager.showDeleteFeaturePage());
        buttonEditFeature.setOnAction(e -> ScreenManager.showEditFeaturePage());
        buttonNewFeature.setOnAction(e -> ScreenManager.showNewFeaturePage());
        buttonAllReservations.setOnAction(e -> ScreenManager.showAllReservations());
        reservationsServicesButton.setOnAction(e -> ScreenManager.showReservationsServicesPage());
        initializeComboBox();

    }

    @FXML
    public void handleDeleteFeatureButtonClick(ActionEvent event) {
        String selectedFeature = featuresComboBox.getSelectionModel().getSelectedItem();
        String enteredFeatureName = featureNameTextField.getText();

        if ((selectedFeature != null && !selectedFeature.isEmpty()) || (enteredFeatureName != null && !enteredFeatureName.isEmpty())) {
            deleteFeature(selectedFeature, enteredFeatureName);
        } else {
            DBUtils.showErrorAlert("Error", "Invalid Input", "Please select a feature or enter a valid feature name.");
        }
    }

    private void deleteFeature(String selectedFeature, String enteredFeatureName) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y")) {
            String sql;
            if (selectedFeature != null && !selectedFeature.isEmpty()) {
                sql = "DELETE FROM features WHERE FeatureName = ?";
            } else {
                sql = "DELETE FROM features WHERE FeatureName = ?";
                selectedFeature = enteredFeatureName;
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, selectedFeature);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    DBUtils.showSuccessAlert("Success", "Feature Deleted", "Feature has been successfully deleted.");
                    initializeComboBox();
                } else {
                    DBUtils.showErrorAlert("Error", "Failed to Delete Feature", "Failed to delete the feature.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initializeComboBox() {
        ObservableList<String> featureNames = getAllFeatureNames();
        featuresComboBox.setItems(featureNames);
    }

    private ObservableList<String> getAllFeatureNames() {
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
}
