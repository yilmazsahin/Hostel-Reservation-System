package com.example.sahinhotel.AddingNewItemsPages;

import com.example.sahinhotel.DBUtils;
import com.example.sahinhotel.EditItemsPages.EditRoomController;
import com.example.sahinhotel.Room;
import com.example.sahinhotel.RoomTypes.*;
import com.example.sahinhotel.ScreenManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @since 11/11/2023
 */

public class NewRoomController implements Initializable {
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
    private TextField tf_totalRooms;
    @FXML
    private Button buttonNewRoom;
    @FXML
    private Button buttonEditRoom;
    @FXML
    private Button buttonDeleteRoom;
    @FXML
    private TextField roomNameTextField;

    @FXML
    private TextField roomCapacityTextField;

    @FXML
    private TextField roomPriceTextField;

    @FXML
    private TextField roomFeaturesTextField;

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
    }

    public void handleNewRoomButtonClick(ActionEvent event) {
        String roomName = roomNameTextField.getText();
        String capacityString = roomCapacityTextField.getText();
        String priceString = roomPriceTextField.getText();
        String totalRoomsTf = tf_totalRooms.getText();
        try {
            int capacity = Integer.parseInt(capacityString);
            double price = Double.parseDouble(priceString);
            int totalRooms = Integer.parseInt(totalRoomsTf);
            int availableRooms = totalRooms;
            List<String> featuresList = EditRoomController.getFeaturesFromTextField(roomFeaturesTextField);
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y");
            String sql = "INSERT INTO rooms (RoomName, Capacity, Price, Features,TotalRooms,AvailableRooms) VALUES (?, ?, ?, ?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, roomName);
            preparedStatement.setInt(2, capacity);
            preparedStatement.setDouble(3, price);
            preparedStatement.setString(4, String.join(", ", featuresList));
            preparedStatement.setInt(5, totalRooms);
            preparedStatement.setInt(6, availableRooms);

            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
            DBUtils.showSuccessAlert("Success", "Room Added", "Room has been successfully added to the database.");

        } catch (NumberFormatException e) {
            DBUtils.showErrorAlert("Error", "Invalid Input", "Please enter valid numeric values for Capacity and Price.");
        } catch (SQLException e) {
            DBUtils.showErrorAlert("Error", "Database Error", "Failed to add room to the database.");
            e.printStackTrace();
        }
    }
}
