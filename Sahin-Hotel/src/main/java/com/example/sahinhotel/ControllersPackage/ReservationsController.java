package com.example.sahinhotel.ControllersPackage;

import com.example.sahinhotel.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @since 11/4/2023
 */

public class ReservationsController implements Initializable {

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
    private Button accommodationInvoiceButton;
    @FXML
    private Button reservationsServicesButton;
    @FXML
    private TableView<Reservations> reservationsTableView;
    @FXML
    private TableColumn<Reservations, Integer> columnId;
    @FXML
    private TableColumn<Reservations, String> columnRoom;
    @FXML
    private TableColumn<Reservations, LocalDateTime> columnCheckInDate;
    @FXML
    private TableColumn<Reservations, LocalDateTime> columnCheckOutDate;
    @FXML
    private TableColumn<Reservations, LocalDateTime> columnCheckedIn;
    @FXML
    private TableColumn<Reservations, LocalDateTime> columnCheckedOut;
    @FXML
    private TableColumn<Reservations, String> columnCustomers;
    @FXML
    private Button buttonReservation;
    @FXML
    private Button buttonEdit;
    @FXML
    private Button buttonDelete;

    public void setRoom(Room room) {
        this.room = room;
    }

    public LocalDate getCheckIn_Date() {
        return checkIn_Date;
    }

    public void setCheckIn_Date(LocalDate checkIn_Date) {
        this.checkIn_Date = checkIn_Date;
    }

    public LocalDate getCheckOut_Date() {
        return checkOut_Date;
    }

    public void setCheckOut_Date(LocalDate checkOut_Date) {
        this.checkOut_Date = checkOut_Date;
    }

    public LocalDate getChecked_In() {
        return Checked_In;
    }

    public void setChecked_In(LocalDate checked_In) {
        Checked_In = checked_In;
    }

    public LocalDate getChecked_Out() {
        return Checked_Out;
    }

    public void setChecked_Out(LocalDate checked_Out) {
        Checked_Out = checked_Out;
    }

    public void setCustomer(Customers customer) {
        this.customer = customer;
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

    public Button getButtonCustomers() {
        return buttonCustomers;
    }

    public void setButtonCustomers(Button buttonCustomers) {
        this.buttonCustomers = buttonCustomers;
    }

    public TableView<Reservations> getReservationsTableView() {
        return reservationsTableView;
    }

    public void setReservationsTableView(TableView<Reservations> reservationsTableView) {
        this.reservationsTableView = reservationsTableView;
    }

    public ReservationsController() {
    }

    public ReservationsController(Room room, Customers customer, Button buttonRooms, Button buttonFeatures, Button buttonServices, Button buttonCustomers, Button buttonReservation) {
        this.room = room;
        this.customer = customer;
        this.buttonRooms = buttonRooms;
        this.buttonFeatures = buttonFeatures;
        this.buttonServices = buttonServices;
        this.buttonCustomers = buttonCustomers;
        this.buttonReservation = buttonReservation;
    }

    public Room getRoom() {
        return room;
    }

    public LocalDate getCheckInDate() {
        return checkIn_Date;
    }

    public LocalDate getCheckOutDate() {
        return checkOut_Date;
    }

    public LocalDate getCheckedIn() {
        return Checked_In;
    }

    public LocalDate getCheckedOut() {
        return Checked_Out;
    }

    public Customers getCustomer() {
        return customer;
    }


    public void initialize() {
        TableColumn<Reservations, Integer> columnId = new TableColumn<>("ID");
        TableColumn<Reservations, Room> columnRoom = new TableColumn<>("Room");
        TableColumn<Reservations, LocalDateTime> columnCheckInDate = new TableColumn<>("Check-In Date");
        TableColumn<Reservations, LocalDateTime> columnCheckOutDate = new TableColumn<>("Check-Out Date");
        TableColumn<Reservations, LocalDateTime> columnCheckedIn = new TableColumn<>("Checked-In");
        TableColumn<Reservations, LocalDateTime> columnCheckedOut = new TableColumn<>("Check-Out");
        TableColumn<Reservations, Integer> columnCustomers = new TableColumn<>("Customer");

        columnId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        columnRoom.setCellValueFactory(new PropertyValueFactory<>("Room"));
        columnCheckInDate.setCellValueFactory(new PropertyValueFactory<>("CheckIn_Date"));
        columnCheckOutDate.setCellValueFactory(new PropertyValueFactory<>("CheckOut_Date"));
        columnCheckedIn.setCellValueFactory(new PropertyValueFactory<>("Checked_In"));
        columnCheckedOut.setCellValueFactory(new PropertyValueFactory<>("Checked_Out"));
        columnCustomers.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        columnCustomers.setCellFactory(col -> new TableCell<Reservations, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {

                    String customerName = DBUtils.getCustomerNameById(item);
                    setText(customerName);
                }
            }
        });

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Reservations> reservations = DBUtils.getAllReservations();
        if (reservations != null) {
            ObservableList<Reservations> observableReservations = FXCollections.observableArrayList(reservations);
            reservationsTableView.setItems(observableReservations);
        } else {
            System.out.println("An error occurred while retrieving reservations from the database");
        }

    }

    public boolean validateDates() {
        if (checkIn_Date.isAfter(Checked_Out) || Checked_In.isAfter(checkOut_Date)) {
            System.out.println("Hatalı tarihler! Lütfen tarihleri kontrol edin.");
            return false;
        }
        return true;
    }

    public boolean validateCheckInOutDates() {
        if (Checked_In.isBefore(LocalDate.now()) && Checked_Out.isAfter(LocalDate.now())) {
            System.out.println("Hata: Müşteri otelden çıkış yapmamış!");
            return false;
        }
        return true;
    }

    public static void updateCheckOutDate(int reservationId) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y")) {
            String sql = "UPDATE reservations SET Checked_Out = ? WHERE ReservationId = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                LocalDate checkOutDate = LocalDate.now();
                preparedStatement.setDate(1, java.sql.Date.valueOf(checkOutDate));
                preparedStatement.setInt(2, reservationId);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Müşteri otelden çıkış yaptı. Checked_Out tarihi güncellendi.");
                } else {
                    System.out.println("Hata: Müşteri otelden çıkış yapmamış veya güncelleme sırasında bir hata oluştu.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Hata: Veritabanına erişim sırasında bir hata oluştu.");
        }
    }

    public static void updateCheckInDate(int reservationId) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y")) {
            String sql = "UPDATE reservations SET CheckIn_Date = ? WHERE ReservationId = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                LocalDate checkInDate = LocalDate.now();
                preparedStatement.setDate(1, java.sql.Date.valueOf(checkInDate));
                preparedStatement.setInt(2, reservationId);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Müşteri otelden çıkış yaptı. Checked_Out tarihi güncellendi.");
                } else {
                    System.out.println("Hata: Müşteri otelden çıkış yapmamış veya güncelleme sırasında bir hata oluştu.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Hata: Veritabanına erişim sırasında bir hata oluştu.");
        }
    }

    public static void updateCheckedInDate(int reservationId) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y")) {
            String sql = "UPDATE reservations SET Checked_In = ? WHERE ReservationId = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                LocalDate checkedIn = LocalDate.now();
                preparedStatement.setDate(1, java.sql.Date.valueOf(checkedIn));
                preparedStatement.setInt(2, reservationId);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Müşteri otelden çıkış yaptı. Checked_Out tarihi güncellendi.");
                } else {
                    System.out.println("Hata: Müşteri otelden çıkış yapmamış veya güncelleme sırasında bir hata oluştu.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Hata: Veritabanına erişim sırasında bir hata oluştu.");
        }
    }

    public static void updateCheckedOutDate(int reservationId) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y")) {
            String sql = "UPDATE reservations SET Checked_Out = ? WHERE Id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                LocalDate checkedOut = LocalDate.now();
                preparedStatement.setDate(1, java.sql.Date.valueOf(checkedOut));
                preparedStatement.setInt(2, reservationId);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Müşteri otelden çıkış yaptı. Checked_Out tarihi güncellendi.");
                } else {
                    System.out.println("Hata: Müşteri otelden çıkış yapmamış.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
