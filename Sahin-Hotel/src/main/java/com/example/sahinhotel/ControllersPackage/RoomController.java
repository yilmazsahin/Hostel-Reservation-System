package com.example.sahinhotel.ControllersPackage;

import com.example.sahinhotel.DBUtils;
import com.example.sahinhotel.Features;
import com.example.sahinhotel.Room;
import com.example.sahinhotel.ScreenManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @since 10/25/2023
 */

public class RoomController implements Initializable {
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
    private TableView<Room> roomTableView;
    @FXML
    private TableColumn<Room, Integer> columnId;
    @FXML
    private TableColumn<Room, String> columnRoomName;
    @FXML
    private TableColumn<Room, Integer> columnCapacity;
    @FXML
    private TableColumn<Room, Double> columnPrice;
    @FXML
    private TableColumn<Room, List<String>> columnFeatures;
    @FXML
    private TableColumn<Room, String> columnTotalRooms;
    @FXML
    private TableColumn<Room, String> columnAvailableRooms;
    @FXML
    private Button buttonNewRoom;
    @FXML
    private Button buttonEditRoom;
    @FXML
    private Button buttonDeleteRoom;


    public void initialize() {
        columnId.setCellValueFactory(new PropertyValueFactory<>("RoomId"));
        columnRoomName.setCellValueFactory(new PropertyValueFactory<>("RoomName"));
        columnCapacity.setCellValueFactory(new PropertyValueFactory<>("Capacity"));
        columnPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        columnFeatures.setCellValueFactory(new PropertyValueFactory<>("Features"));

        columnTotalRooms.setCellValueFactory(new PropertyValueFactory<>("TotalRooms"));
        columnAvailableRooms.setCellValueFactory(new PropertyValueFactory<>("AvailableRooms"));
        List<Room> rooms = DBUtils.getAllRooms();
        ObservableList<Room> observableRooms = FXCollections.observableArrayList(rooms);
        roomTableView.setItems(observableRooms);

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
        List<Room> rooms = DBUtils.getAllRooms();
        if (rooms != null) {
            ObservableList<Room> observableRooms = FXCollections.observableArrayList(rooms);
            roomTableView.setItems(observableRooms);
        } else {
            System.out.println("An error occurred while retrieving rooms from the database.");
        }
    }
}
