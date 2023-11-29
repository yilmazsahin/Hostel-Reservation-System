package com.example.sahinhotel.ControllersPackage;

import com.example.sahinhotel.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Date;

import static java.util.Date.*;

/**
 * @since 10/23/2023
 */

public class MainController implements Initializable {
    private int id;
    private Room room;
    private LocalDate checkIn_Date;
    private LocalDate checkOut_Date;
    private LocalDate Checked_In;
    private LocalDate Checked_Out;
    private Customers customer;
    @FXML
    private DatePicker filterDateFrom;
    @FXML
    private DatePicker filterDateTo;
    @FXML
    private Button buttonRooms;
    @FXML
    private Button buttonFeatures;
    @FXML
    private Button buttonServices;
    @FXML
    private Button buttonCustomers;
    @FXML
    private Button reservationsServicesButton;
    @FXML
    private TableView<Reservations> reservationsTableView;
    @FXML
    private Button accommodationInvoiceButton;
    @FXML
    private TextField tf_searchByName;


    private ObservableList<Room> roomsList = FXCollections.observableArrayList();
    private ObservableList<Reservations> reservationsList = FXCollections.observableArrayList();
    private ObservableList<Customers> customersList = FXCollections.observableArrayList();
    private ObservableList<Services> servicesList = FXCollections.observableArrayList();
    private ObservableList<Features> featuresList = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Reservations, Integer> columnId;
    @FXML
    private TableColumn<Reservations, Integer> columnRoom;
    @FXML
    private TableColumn<Reservations, Date> columnCheckInDate;
    @FXML
    private TableColumn<Reservations, Date> columnCheckOutDate;
    @FXML
    private TableColumn<Reservations, Date> columnCheckedIn;
    @FXML
    private TableColumn<Reservations, Date> columnCheckedOut;
    @FXML
    private TableColumn<Reservations, String> columnCustomers;
    @FXML
    private Button buttonReservation;
    @FXML
    private Button buttonEdit;
    @FXML
    private Button buttonDelete;


    public Button getAccommodationInvoiceButton() {
        return accommodationInvoiceButton;
    }

    public void setAccommodationInvoiceButton(Button accommodationInvoiceButton) {
        this.accommodationInvoiceButton = accommodationInvoiceButton;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public LocalDate getChecked_In() {
        return Checked_In;
    }

    public void setChecked_In(LocalDate checked_In) {
        Checked_In = checked_In;
    }

    public Customers getCustomer() {
        return customer;
    }

    public void setCustomer(Customers customer) {
        this.customer = customer;
    }

    public MainController(Room room, Customers customer, Button buttonRooms, Button buttonFeatures, Button buttonServices, Button buttonCustomers, Button buttonReservation, Button accommodationInvoiceButton) {
        this.room = room;
        this.customer = customer;
        this.buttonRooms = buttonRooms;
        this.buttonFeatures = buttonFeatures;
        this.buttonServices = buttonServices;
        this.buttonCustomers = buttonCustomers;
        this.buttonReservation = buttonReservation;
        this.accommodationInvoiceButton = accommodationInvoiceButton;
    }

    public DatePicker getFilterDateFrom() {
        return filterDateFrom;
    }

    public void setFilterDateFrom(DatePicker filterDateFrom) {
        this.filterDateFrom = filterDateFrom;
    }

    public DatePicker getFilterDateTo() {
        return filterDateTo;
    }

    public void setFilterDateTo(DatePicker filterDateTo) {
        this.filterDateTo = filterDateTo;
    }

    public Button getButtonRooms() {
        return buttonRooms;
    }

    public void setButtonRooms(Button buttonRooms) {
        this.buttonRooms = buttonRooms;
    }

    public Button getButtonFeatures() {
        return buttonFeatures;
    }

    public void setButtonFeatures(Button buttonFeatures) {
        this.buttonFeatures = buttonFeatures;
    }

    public Button getButtonServices() {
        return buttonServices;
    }

    public void setButtonServices(Button buttonServices) {
        this.buttonServices = buttonServices;
    }

    public Button getButtonCustomers() {
        return buttonCustomers;
    }

    public void setButtonCustomers(Button buttonCustomers) {
        this.buttonCustomers = buttonCustomers;
    }


    public Button getButtonReservation() {
        return buttonReservation;
    }

    public void setButtonReservation(Button buttonReservation) {
        this.buttonReservation = buttonReservation;
    }

    public Button getButtonEdit() {
        return buttonEdit;
    }

    public void setButtonEdit(Button buttonEdit) {
        this.buttonEdit = buttonEdit;
    }

    public Button getButtonDelete() {
        return buttonDelete;
    }

    public void setButtonDelete(Button buttonDelete) {
        this.buttonDelete = buttonDelete;
    }

    public MainController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Reservations> reservations = DBUtils.getAllReservations();
        ObservableList<Reservations> observableReservations = FXCollections.observableArrayList(reservations);
        reservationsTableView.setItems(observableReservations);
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnRoom.setCellValueFactory(new PropertyValueFactory<>("Room"));
        columnCheckInDate.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
        columnCheckOutDate.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));
        columnCheckedIn.setCellValueFactory(new PropertyValueFactory<>("checkedIn"));
        columnCheckedOut.setCellValueFactory(new PropertyValueFactory<>("checkedOut"));
        columnCustomers.setCellValueFactory(cellData -> {
            int customerId = cellData.getValue().getCustomerId();
            String fullName = DBUtils.getCustomerNameById(customerId);
            return new SimpleStringProperty(fullName);
        });
        filterReservationsByDateRange();
    }

    public void initialize() {
        TableColumn<Reservations, Integer> columnId = new TableColumn<>("ID");
        TableColumn<Reservations, Room> columnRoom = new TableColumn<>("Room");
        TableColumn<Reservations, LocalDateTime> columnCheckInDate = new TableColumn<>("Check-In Date");
        TableColumn<Reservations, LocalDateTime> columnCheckOutDate = new TableColumn<>("Check-Out Date");
        TableColumn<Reservations, LocalDateTime> columnCheckedIn = new TableColumn<>("Checked-In");
        TableColumn<Reservations, LocalDateTime> columnCheckedOut = new TableColumn<>("Check-Out");
        TableColumn<Reservations, Integer> columnCustomers = new TableColumn<>("Customer");

        List<Reservations> reservationsList = DBUtils.getAllReservations();
        ObservableList<Reservations> observableReservations = FXCollections.observableArrayList(reservationsList);
        reservationsTableView.setItems(observableReservations);

        buttonRooms.setOnAction(e -> ScreenManager.showRoomsPage());
        buttonFeatures.setOnAction(e -> ScreenManager.showFeaturesPage());
        buttonCustomers.setOnAction(e -> ScreenManager.showCustomersPage());
        buttonServices.setOnAction(e -> ScreenManager.showServicesPage());
        buttonDelete.setOnAction(e -> ScreenManager.showDeleteReservationPage());
        buttonEdit.setOnAction(e -> ScreenManager.showEditPage());
        buttonReservation.setOnAction(e -> ScreenManager.showNewReservationPage());
        reservationsServicesButton.setOnAction(e -> ScreenManager.showReservationsServicesPage());
        accommodationInvoiceButton.setOnAction(e -> ScreenManager.showCustomerPaymentsPage());
    }

    public void filterReservationsByDateRange() {
        LocalDate fromDate = filterDateFrom.getValue();
        LocalDate toDate = filterDateTo.getValue();

        if (fromDate != null && toDate != null) {
            List<Reservations> filteredReservations = getReservationsByDateRange(fromDate, toDate);
            ObservableList<Reservations> observableFilteredReservations = FXCollections.observableArrayList(filteredReservations);
            reservationsTableView.setItems(observableFilteredReservations);
        } else {
            List<Reservations> allReservations = DBUtils.getAllReservations();
            ObservableList<Reservations> observableAllReservations = FXCollections.observableArrayList(allReservations);
            reservationsTableView.setItems(observableAllReservations);
        }
    }

    public void handleSearchReservationsByDateButtonClick(ActionEvent event) {
        LocalDate fromDate = filterDateFrom.getValue();
        LocalDate toDate = filterDateTo.getValue();
        if (fromDate != null && toDate != null && !fromDate.isAfter(toDate)) {
            List<Reservations> filteredReservations = getReservationsByCheckedInDate(fromDate, toDate);
            ObservableList<Reservations> observableFilteredReservations = FXCollections.observableArrayList(filteredReservations);
            reservationsTableView.setItems(observableFilteredReservations);
        } else {
            DBUtils.showConfirmationAlert("Attention", "You have not entered the correct fields", "You are viewing all reservations.");
        }}
    private List<Reservations> getReservationsByCheckedInDate(LocalDate fromDate, LocalDate toDate) {
        List<Reservations> allReservations = DBUtils.getAllReservations();
        List<Reservations> filteredReservations = new ArrayList<>();
        for (Reservations reservation : allReservations) {
            LocalDate checkedInDate = reservation.getCheckedIn();
            LocalDate checkedOutDate = reservation.getCheckedOut();
            if (checkedInDate != null && checkedOutDate != null && !checkedOutDate.isBefore(fromDate) && !checkedInDate.isAfter(toDate)) {
                filteredReservations.add(reservation);
            }}return filteredReservations;}

    public void handleSearchReservationsByNameButtonClick(ActionEvent event) {
        String customerName = tf_searchByName.getText();
        if (customerName != null) {
            List<Reservations> filteredReservations = getReservationsListByName(customerName);
            ObservableList<Reservations> observableFilteredReservations = FXCollections.observableArrayList(filteredReservations);
            reservationsTableView.setItems(observableFilteredReservations);
        } else {
            DBUtils.showErrorAlert("Error", "Customer not found", "Please enter a valid customer name.");
        }
    }

    private List<Reservations> getReservationsListByName(String customerName) {
        List<Reservations> reservations = new ArrayList<>();
        String customerIdFromDb = getCustomerIdByName(customerName);
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y");
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM reservations WHERE customer_id=? ");
        ) {
            statement.setString(1, customerIdFromDb);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("Id");
                    String roomName = resultSet.getString("Room");
                    LocalDate checkedIn = resultSet.getDate("Checked_In").toLocalDate();
                    LocalDate checkedOut = resultSet.getDate("Checked_Out").toLocalDate();
                    LocalDate checkInDate = resultSet.getDate("CheckIn_Date").toLocalDate();
                    LocalDate checkOutDate = resultSet.getDate("CheckOut_Date").toLocalDate();
                    int customerId = resultSet.getInt("customer_id");
                    Room room = DBUtils.getRoomByName(roomName);
                    Reservations reservation = new Reservations(id, room, checkInDate, checkOutDate, checkedIn, checkedOut, customerId);
                    reservations.add(reservation);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    private String getCustomerIdByName(String customerName) {
        String customerId = null;
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y");
             PreparedStatement statement = connection.prepareStatement("SELECT CustomerId FROM customers WHERE FullName = ?");
        ) {
            statement.setString(1, customerName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    customerId = resultSet.getString("CustomerId");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerId;
    }

    public static List<Reservations> getReservationsByDateRange(LocalDate fromDate, LocalDate toDate) {
        List<Reservations> reservations = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y");
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM reservations WHERE Checked_In BETWEEN ? AND ?");
        ) {
            statement.setDate(1, java.sql.Date.valueOf(fromDate));
            statement.setDate(2, java.sql.Date.valueOf(toDate));

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("Id");
                    int roomId = resultSet.getInt("RoomId");
                    LocalDate checkedIn = resultSet.getDate("Checked_In").toLocalDate();
                    LocalDate checkedOut = resultSet.getDate("Checked_Out").toLocalDate();
                    LocalDate checkInDate = resultSet.getDate("CheckIn_Date").toLocalDate();
                    LocalDate checkOutDate = resultSet.getDate("CheckOut_Date").toLocalDate();
                    int customerId = resultSet.getInt("CustomerId");
                    Room room = DBUtils.getRoomById(roomId);
                    Reservations reservation = new Reservations(id, room, checkInDate, checkOutDate, checkedIn, checkedOut, customerId);
                    reservations.add(reservation);
                }
            }
            return reservations;

        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public void setFeaturesButtonAction(Runnable action) {
        buttonFeatures.setOnAction(e -> action.run());
    }

    public void setRoomsButtonAction(Runnable action) {
        buttonRooms.setOnAction(e -> action.run());
    }

    public void setCustomersButtonAction(Runnable action) {
        buttonCustomers.setOnAction(e -> action.run());
    }

    public void setServicesButtonAction(Runnable action) {
        buttonServices.setOnAction(e -> action.run());
    }

    public void setButtonEditAction(Runnable action) {
        buttonEdit.setOnAction(e -> action.run());
    }

    public void setButtonReservationAction(Runnable action) {
        buttonReservation.setOnAction(e -> action.run());
    }

    public void setButtonDeleteAction(Runnable action) {
        buttonDelete.setOnAction(e -> action.run());
    }

    public void setReservationsServicesButton(Runnable action) {
        reservationsServicesButton.setOnAction(e -> action.run());
    }

    public void setAccommodationInvoiceButton(Runnable action) {
        accommodationInvoiceButton.setOnAction(e -> action.run());
    }


}
