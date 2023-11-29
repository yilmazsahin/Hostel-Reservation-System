package com.example.sahinhotel;

import com.example.sahinhotel.RoomTypes.*;
import javafx.scene.control.Alert;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.Date;

/**
 * @since 10/23/2023
 */

public class DBUtils {
    public static List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y");
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM rooms");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("RoomId");
                String roomName = resultSet.getString("RoomName");
                int capacity = resultSet.getInt("Capacity");
                double price = resultSet.getDouble("Price");
                int totalRooms = resultSet.getInt("TotalRooms");
                int availableRooms = resultSet.getInt("AvailableRooms");
                String featuresString = resultSet.getString("Features");
                List<String> features = Arrays.asList(featuresString.split(","));
                Room room = createRoom(id, roomName, capacity, price, features, totalRooms, availableRooms);
                rooms.add(room);
            }
            return rooms;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public static Room createRoom(int id, String roomName, int capacity, double price, List<String> features, int totalRooms, int availableRooms) {
        try {
            Class<?> roomClass = Class.forName("com.example.sahinhotel.RoomTypes." + roomName.replace(" ", ""));
            Constructor<?> constructor = roomClass.getConstructor(int.class, String.class, int.class, double.class, List.class, int.class, int.class);
            Room room = (Room) constructor.newInstance(id, roomName, capacity, price, features, totalRooms, availableRooms);
            room.setFeatures(features);
            return room;
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            e.printStackTrace();
            return new SingleRoom(id, roomName, capacity, price, features, totalRooms, availableRooms);
        }
    }

    public static List<Customers> getAllCustomers() {
        List<Customers> customers = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y");
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM customers");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int customerId = resultSet.getInt("CustomerId");
                String fullName = resultSet.getString("FullName");
                String identityNumber = resultSet.getString("IdentityNumber");
                String phoneNumber = resultSet.getString("PhoneNumber");
                Date birthDate = resultSet.getDate("BirthDate");
                String description = resultSet.getString("Description");
                Customers customer = new Customers(customerId, fullName, identityNumber, phoneNumber, birthDate, description);
                customers.add(customer);
            }
            return customers;
        } catch (SQLException e) {
            e.printStackTrace();
            showConfirmationAlert("No data", "Customer List is empty.", "There is not any customer in the database");
            return Collections.emptyList();

        }
    }

    public static List<Services> getAllServices() {
        List<Services> services = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y");
             PreparedStatement statement = connection.prepareStatement("SELECT *FROM services");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("ServiceId");
                String serviceName = resultSet.getString("ServiceName");
                double servicePrice = resultSet.getDouble("ServicePrice");
                Services service = new Services(id, serviceName, servicePrice);
                services.add(service);
            }
            return services;
        } catch (SQLException e) {
            e.printStackTrace();
            showConfirmationAlert("No data", "Services List is empty.", "There is not any services in the database");
            return Collections.emptyList();
        }
    }

    public static List<Reservations> getAllReservations() {
        List<Reservations> reservations = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y");
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM reservations");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("Id");
                String roomName = resultSet.getString("Room");
                Room room;
                switch (roomName) {
                    case "Single Room":
                        room = new SingleRoom();
                        break;
                    case "Double Room":
                        room = new DoubleRoom();
                        break;
                    case "Accessible Room":
                        room = new AccessibleRoom();
                        break;
                    case "Family Room":
                        room = new FamilyRoom();
                        break;
                    case "Honeymoon Room":
                        room = new HoneymoonRoom();
                        break;
                    case "Junior Suite":
                        room = new JuniorSuite();
                        break;
                    case "King Suite":
                        room = new KingSuite();
                        break;
                    case "Triple Room":
                        room = new TripleRoom();
                        break;
                    default:

                        room = new SingleRoom();
                        break;
                }
                room.setRoomName(roomName);
                LocalDate checkInDate = resultSet.getDate("CheckIn_Date").toLocalDate();
                LocalDate checkOutDate = resultSet.getDate("CheckOut_Date").toLocalDate();
                LocalDate checkedInDate = resultSet.getDate("Checked_In").toLocalDate();
                LocalDate checkedOutDate = resultSet.getDate("Checked_Out").toLocalDate();
                int customerId = resultSet.getInt("customer_id");
                Reservations reservation = new Reservations(id, room, checkInDate, checkOutDate, checkedInDate, checkedOutDate, customerId);
                reservations.add(reservation);
            }
            return reservations;
        } catch (SQLException e) {
            e.printStackTrace();
            showConfirmationAlert("No data", "Reservations list is empty.", "There is not any reservations in the database");
            return Collections.emptyList();
        }
    }

    public static List<ReservationServices> getAllReservationsServices() {
        List<ReservationServices> reservationServices = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y");
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM reservations_services");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("ReservationServiceId");
                int reservationId = resultSet.getInt("ReservationId");
                int serviceId = resultSet.getInt("ServiceId");
                String serviceName = resultSet.getString("ServiceName");
                double unitPrice = resultSet.getDouble("UnitPrice");
                int quantity = resultSet.getInt("Quantity");
                double totalPrice = resultSet.getDouble("TotalPrice");
                ReservationServices reservationsService = new ReservationServices(id, reservationId, serviceId, serviceName, unitPrice, quantity, totalPrice);
                reservationServices.add(reservationsService);
            }
            return reservationServices;

        } catch (SQLException e) {
            e.printStackTrace();
            showConfirmationAlert("No data", "Reservation services list is empty.", "There is not any reservation service in the database");
            return Collections.emptyList();
        }
    }

    public static Room getRoomById(int roomId) throws SQLException {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y");
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM rooms WHERE RoomId = ?");
        ) {
            statement.setInt(1, roomId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String roomName = resultSet.getString("RoomName");
                int capacity = resultSet.getInt("Capacity");
                double price = resultSet.getDouble("Price");
                String featuresString = resultSet.getString("Features");
                int totalRooms = resultSet.getInt("TotalRooms");
                int availableRooms = resultSet.getInt("AvailableRooms");
                List<String> features = Arrays.asList(featuresString.split(","));
                Room room = createRoom(roomId, roomName, capacity, price, features, totalRooms, availableRooms);
                return room;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
      showConfirmationAlert("Not found", "No room found with this ID.", "There is not any reservations in the database,Please try another ID.");
        return null;
    }
    public static Room getRoomByName(String roomType) {
        Room room = null;
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y")) {
            String sql = "SELECT * FROM rooms WHERE RoomName = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, roomType);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int roomId = resultSet.getInt("RoomId");
                        String roomName = resultSet.getString("RoomName");
                        int capacity = resultSet.getInt("Capacity");
                        double price = resultSet.getDouble("Price");
                        int totalRooms = resultSet.getInt("TotalRooms");
                        int availableRooms = resultSet.getInt("AvailableRooms");
                        List<String> features = DBUtils.getRoomFeaturesById(roomId);
                        switch (roomType) {
                            case "Single Room":
                                room = new SingleRoom(roomId, roomName, capacity, price, features, totalRooms, availableRooms);
                                break;
                            case "Double Room":
                                room = new DoubleRoom(roomId, roomName, capacity, price, features, totalRooms, availableRooms);
                                break;
                            case "Junior Suite":
                                room = new JuniorSuite(roomId, roomName, capacity, price, features, totalRooms, availableRooms);
                                break;
                            case "Triple Room":
                                room = new TripleRoom(roomId, roomName, capacity, price, features, totalRooms, availableRooms);
                                break;
                            case "King Suite":
                                room = new KingSuite(roomId, roomName, capacity, price, features, totalRooms, availableRooms);
                                break;
                            case "Honeymoon Room":
                                room = new HoneymoonRoom(roomId, roomName, capacity, price, features, totalRooms, availableRooms);
                                break;
                            case "Family Room":
                                room = new FamilyRoom(roomId, roomName, capacity, price, features, totalRooms, availableRooms);
                                break;
                            case "Accessible Room":
                                room = new AccessibleRoom(roomId, roomName, capacity, price, features, totalRooms, availableRooms);
                                break;
                            default:
                                room = new SingleRoom(roomId, roomName, capacity, price, features, totalRooms, availableRooms);
                                break;
                        }
                        room = new SingleRoom(roomId, roomName, capacity, price, features, totalRooms, availableRooms);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return room;
    }

    public static List<Features> getAllFeatures() {
        List<Features> features = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y");
             PreparedStatement statement = connection.prepareStatement("SELECT *FROM features");
             ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                int featureId = resultSet.getInt("FeatureId");
                String featureName = resultSet.getString("FeatureName");
                Features feature = new Features(featureId, featureName);
                features.add(feature);
            }
            return features;
        } catch (SQLException e) {
            e.printStackTrace();
            showConfirmationAlert("Not found", "No features found in this database.", "There is no feature in the database.");
            return Collections.emptyList();
        }
    }

    public static void addNewReservation(Reservations reservation) {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet generatedKeys = null;
        String sql = "INSERT INTO reservations (Room, CheckIn_Date, CheckOut_Date, Checked_In, Checked_Out,customer_id) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y");
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, reservation.getRoom().getRoomName());
            preparedStatement.setDate(2, java.sql.Date.valueOf(reservation.getCheckInDate()));
            preparedStatement.setDate(3, java.sql.Date.valueOf(reservation.getCheckOutDate()));
            preparedStatement.setDate(4, java.sql.Date.valueOf(reservation.getCheckedIn()));
            preparedStatement.setDate(5, java.sql.Date.valueOf(reservation.getCheckedOut()));
            preparedStatement.setInt(6, reservation.getCustomerId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating reservation failed, no raws affected");
            }
            generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                reservation.setId(generatedId);
            } else {
                throw new SQLException("Creating reservation failed, no ID obtained.");
            }
            showSuccessAlert("Success", "New Reservation", "New reservation has been successfully saved.");
            System.out.println("Oluşturuldu.");
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("Unsuccessful", "Record registration error", "New reservation could not be created, please try again.");
            System.err.println("Hata oluştu.");
        } finally {
            try {
                if (generatedKeys != null) {
                    generatedKeys.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void deleteReservationServicesByReservationId(int reservationId) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y");
             PreparedStatement statement = connection.prepareStatement("DELETE FROM reservations_services WHERE ReservationId = ?")) {
            statement.setInt(1, reservationId);
            statement.executeUpdate();
            showConfirmationAlert("Success", "Reservation Service deleted.", "Reservation service has been successfully deleted.");
        } catch (SQLException e) {
            showErrorAlert("Unsuccessful", "Reservation Service couldn't deleted.", "Reservation service hasn't been successfully deleted.Please try again.");
            e.printStackTrace();
        }
    }



    public static List<Customers> getCustomersWithReservations() {
        List<Customers> customersWithReservations = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y");
            String sql = "SELECT DISTINCT c.* FROM customers c INNER JOIN reservations r ON c.customerId = r.customer_id";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int customerId = resultSet.getInt("customerId");
                String fullName = resultSet.getString("fullName");
                Customers customer = new Customers(customerId, fullName);
                customersWithReservations.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return customersWithReservations;
    }

    public static String getCustomerNameById(int customerId) {
        String customerName = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y");
            String sql = "SELECT FullName FROM customers WHERE CustomerId = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, customerId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                customerName = resultSet.getString("FullName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return customerName;
    }

    public static void showErrorAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public static void showSuccessAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public static boolean showConfirmationAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
        return true;

    }

    public static void updateRoomAvailableRooms(int roomId, int newAvailableRooms) {
        if (roomId <= 0) {
            System.out.println("Error: Invalid Room ID.");
            return;
        }
        String sql = "UPDATE rooms SET AvailableRooms = ? WHERE RoomId = ?";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y");
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, newAvailableRooms);
            statement.setInt(2, roomId);

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Room update successful.");
            } else {
                System.out.println("Room update failed, no rows affected.");
                System.out.println("Room ID: " + roomId);
                System.out.println("New Available Rooms: " + newAvailableRooms);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getRoomFeaturesById(int roomId) {
        List<String> features = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y")) {
            String sql = "SELECT Features FROM rooms WHERE RoomId = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, roomId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String featureName = resultSet.getString("Features");
                        features.add(featureName);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return features;
    }

    public static List<String> getRoomFeaturesByName(String roomName) {
        List<String> features = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y")) {
            String sql = "SELECT Features FROM rooms WHERE  RoomName= ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, roomName);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        String feature = resultSet.getString("Features");
                        features.add(feature);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return features;
    }

    public static boolean validateDates(LocalDate checkInDate, LocalDate checkOutDate, LocalDate checkedInDate, LocalDate checkedOutDate) {
        if (checkInDate == null || checkOutDate == null || checkedInDate == null || checkedOutDate == null) {
            DBUtils.showErrorAlert("Error", "Missing Dates", "Please fill in all date fields.");
            return false;
        }
        if (checkInDate.isAfter(checkedOutDate) || checkedInDate.isAfter(checkOutDate) || checkedOutDate.isBefore(checkInDate) || checkedOutDate.isBefore(checkedInDate)) {
            return false;
        }
        return true;
    }
}

