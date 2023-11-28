package com.example.sahinhotel.AddingNewItemsPages;

import com.example.sahinhotel.*;
import com.example.sahinhotel.ControllersPackage.ReservationsController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;


public class NewReservationController implements Initializable {
    @FXML
    private TextField roomNameTextField;
    @FXML
    public ComboBox<Room> roomComboBox;
    @FXML
    private TextField customerNameTextField;
    @FXML
    public ComboBox<Customers> customersComboBox;
    @FXML
    private DatePicker checkInDatePicker;
    @FXML
    private DatePicker checkOutDatePicker;
    @FXML
    private DatePicker checkedInPicker;
    @FXML
    private DatePicker checkedOutPicker;
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
    private Button buttonReservation;
    @FXML
    private Button buttonEdit;
    @FXML
    private Button buttonDelete;
    @FXML
    private Button reservationsServicesButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateRoomAndCustomerComboBoxes();
    }

    public void initialize() {
        populateRoomAndCustomerComboBoxes();
        roomNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            Room selectedRoom = findRoomByName(newValue);
            roomComboBox.setValue(selectedRoom);
        });
        customerNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            String customerText = customerNameTextField.getText();
            if (customerText != null && customerText.matches("\\\\d+:\\\\s*[\\\\p{L}]+\\\\s+[\\\\p{L}]+")) {
                String[] parts = customerText.split(":");
                int customerId = Integer.parseInt(parts[0].trim());
                Customers selectedCustomer = findCustomerById(customerId);
                if (selectedCustomer != null) {
                    customersComboBox.setValue(selectedCustomer);
                }
            }
        });
        buttonRooms.setOnAction(e -> ScreenManager.showRoomsPage());
        buttonFeatures.setOnAction(e -> ScreenManager.showFeaturesPage());
        buttonCustomers.setOnAction(e -> ScreenManager.showCustomersPage());
        buttonServices.setOnAction(e -> ScreenManager.showServicesPage());
        buttonDelete.setOnAction(e -> ScreenManager.showDeleteReservationPage());
        buttonEdit.setOnAction(e -> ScreenManager.showEditPage());
        buttonReservation.setOnAction(e -> ScreenManager.showNewReservationPage());
        buttonAllReservations.setOnAction(e -> ScreenManager.showAllReservations());
        reservationsServicesButton.setOnAction(e -> ScreenManager.showReservationsServicesPage());
    }

    private Room findRoomByName(String roomName) {
        List<Room> allRooms = DBUtils.getAllRooms();
        for (Room room : allRooms) {
            if (room.getRoomName().equalsIgnoreCase(roomName)) {
                return room;
            }
        }
        return null;
    }

    private Customers findCustomerById(int customerId) {
        List<Customers> allCustomers = DBUtils.getAllCustomers();
        for (Customers customer : allCustomers) {
            if (customer.getCustomerId() == customerId) {
                return customer;
            }
        }
        return null;
    }

    public void populateRoomAndCustomerComboBoxes() {
        roomComboBox.setConverter(new StringConverter<Room>() {
            @Override
            public String toString(Room room) {
                return room != null ? room.getRoomName() : null;
            }

            @Override
            public Room fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    Room selectedRoom = findRoomByName(string);
                    return selectedRoom;
                } else {
                    return null;
                }
            }
        });
        List<Room> rooms = DBUtils.getAllRooms();
        ObservableList<Room> roomOptions = FXCollections.observableArrayList(rooms);
        roomComboBox.setItems(roomOptions);

        StringConverter<Customers> customersConverter = new StringConverter<>() {
            @Override
            public String toString(Customers customer) {
                if (customer != null) {
                    return customer.getCustomerId() + ": " + customer.getFullName();
                }
                return null;
            }

            @Override
            public Customers fromString(String string) {
                return null;
            }
        };
        List<Customers> customers = DBUtils.getAllCustomers();
        ObservableList<Customers> customerOptions = FXCollections.observableArrayList(customers);
        customersComboBox.setItems(customerOptions);
        customersComboBox.setConverter(customersConverter);
        customersComboBox.setOnAction(event -> {
            Customers selectedCustomer = customersComboBox.getValue();
            if (selectedCustomer != null) {
                customerNameTextField.setText(selectedCustomer.getCustomerId() + ":" + selectedCustomer.getFullName());
            } else {
                customerNameTextField.clear();
            }
        });
    }

    @FXML
    private void handleNewReservationButtonClick(ActionEvent buttonSave) {
        String customerIdString = customerNameTextField.getText();
        int customerId;
        if (customerIdString.contains(":")) {
            String[] parts = customerIdString.split(":");
            customerId = Integer.parseInt(parts[0].trim());
        } else {
            DBUtils.showErrorAlert("Error", "Invalid Customer Format", "Customer format should be in the form of 'ID: Full Name'.");
            return;
        }
        Room selectedRoom = roomComboBox.getValue();
        LocalDate checkInDateValue = checkInDatePicker.getValue();
        LocalDate checkOutDateValue = checkOutDatePicker.getValue();
        LocalDate checkedInDateValue = checkedInPicker.getValue();
        LocalDate checkedOutDateValue = checkedOutPicker.getValue();
        if (!DBUtils.validateDates(checkInDateValue, checkOutDateValue, checkedInDateValue, checkedOutDateValue)) {
            DBUtils.showErrorAlert("Error", "Wrong Date", "The departure date cannot be selected before the entry date.");
            return;
        }
        ReservationsController.updateCheckedOutDate(selectedRoom.getRoomId());
        int currentAvailableRooms = selectedRoom.getAvailableRooms();
        Reservations newReservation = new Reservations(selectedRoom, checkInDateValue, checkOutDateValue, checkedInDateValue, checkedOutDateValue, customerId);
        DBUtils.addNewReservation(newReservation);
        selectedRoom.reserveRoom();
        DBUtils.updateRoomAvailableRooms(selectedRoom.getRoomId(), currentAvailableRooms - 1);
    }
}