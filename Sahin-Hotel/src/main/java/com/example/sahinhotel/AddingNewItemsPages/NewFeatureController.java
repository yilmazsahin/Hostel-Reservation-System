package com.example.sahinhotel.AddingNewItemsPages;

import com.example.sahinhotel.DBUtils;
import com.example.sahinhotel.ScreenManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;



public class NewFeatureController implements Initializable {
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
    private Button saveNewFeatureButton;
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
        saveNewFeatureButton.setOnAction(this::handleSaveNewFeatureButtonClick);

    }

    private void handleSaveNewFeatureButtonClick(ActionEvent event) {
        String newFeatureName = newFeatureTextField.getText();
        if (!newFeatureName.isEmpty()) {
            saveNewFeatureToDatabase(newFeatureName);
            clearFields();
        } else {
            DBUtils.showErrorAlert("Error", "Invalid Input", "Please enter a valid feature name.");
        }
    }


    private void saveNewFeatureToDatabase(String newFeatureName) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y")) {
            String sql = "INSERT INTO features (FeatureName) VALUES (?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, newFeatureName);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    DBUtils.showSuccessAlert("Success", "New Feature Added", "New feature has been successfully added.");
                } else {
                    DBUtils.showErrorAlert("Error", "Failed to Add Feature", "Failed to add the new feature.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        newFeatureTextField.clear();
    }

}
