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

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

/**
 * @since 11/11/2023
 */

public class DeleteRoomController implements Initializable {
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
    private ComboBox<String> roomNameComboBox;
    @FXML
    private Button buttonNewRoom;
    @FXML
    private Button buttonEditRoom;
    @FXML
    private Button buttonDeleteRoom;

    public void initialize() {
        buttonRooms.setOnAction(e -> ScreenManager.showRoomsPage());
        buttonFeatures.setOnAction(e -> ScreenManager.showFeaturesPage());
        buttonCustomers.setOnAction(e -> ScreenManager.showCustomersPage());
        buttonServices.setOnAction(e -> ScreenManager.showServicesPage());
        buttonDeleteRoom.setOnAction(e -> ScreenManager.showDeleteRoomPage());
        buttonEditRoom.setOnAction(e -> ScreenManager.showEditRoomPage());
        buttonNewRoom.setOnAction(e -> ScreenManager.showNewRoomPage());
        buttonAllReservations.setOnAction(e -> ScreenManager.showAllReservations());
        reservationsServicesButton.setOnAction(e -> ScreenManager.showReservationsServicesPage());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeComboBox();
    }

    private void initializeComboBox() {
        ObservableList<String> roomNames = getAllRoomNames();
        roomNameComboBox.setItems(roomNames);
    }

    public void handleDeleteRoomButtonClick(ActionEvent event) {
        String roomName = roomNameComboBox.getValue();
        if (roomName != null && !roomName.isEmpty()) {
            boolean confirmation = DBUtils.showConfirmationAlert("Delete Confirmation", "Are you sure you want to delete the room?", "This action cannot be undone.");
            if (confirmation) {
                deleteRoom(roomName);
            }
        } else {
            DBUtils.showErrorAlert("Error", "No Room Selected", "Please select a room to delete.");
        }
    }

    private void deleteRoom(String roomName) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y");
            String deleteRoomSql = "DELETE FROM rooms WHERE RoomName = ?";
            try (PreparedStatement deleteRoomStatement = connection.prepareStatement(deleteRoomSql);
            ) {
                deleteRoomStatement.setString(1, roomName);
                int rowsAffected = deleteRoomStatement.executeUpdate();
                if (rowsAffected > 0) {
                    DBUtils.showSuccessAlert("Success", "Room Deleted", "Room has been successfully deleted.");
                } else {
                    DBUtils.showErrorAlert("Error", "Room Not Found", "Room with name '" + roomName + "' was not found.");
                }
            }
        } catch (SQLException e) {
            DBUtils.showErrorAlert("Error", "Database Error", "An error occurred while accessing the database.");
            e.printStackTrace();
        }
    }

    private ObservableList<String> getAllRoomNames() {
        ObservableList<String> roomNames = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y")) {
            String sql = "SELECT RoomName FROM rooms";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String roomName = resultSet.getString("RoomName");
                        roomNames.add(roomName);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomNames;
    }
}
