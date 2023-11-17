package com.example.sahinhotel;

import com.example.sahinhotel.AddingNewItemsPages.*;
import com.example.sahinhotel.ControllersPackage.*;
import com.example.sahinhotel.DeletedItemsPages.*;
import com.example.sahinhotel.EditItemsPages.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @since 11/3/2023
 */

public class ScreenManager {

    public static void showCustomersPage() {
        try {
            FXMLLoader loader = new FXMLLoader(ScreenManager.class.getResource("customer-screen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Customers");
            stage.setScene(new Scene(root));
            CustomerController customerController = loader.getController();
            customerController.initialize();
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showAllReservations() {
        try {
            FXMLLoader loader = new FXMLLoader(ScreenManager.class.getResource("main-screen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Reservations");
            stage.setScene(new Scene(root));
            MainController mainController = loader.getController();
            mainController.initialize();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showFeaturesPage() {
        try {
            FXMLLoader loader = new FXMLLoader(ScreenManager.class.getResource("feature-screen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Features");
            stage.setScene(new Scene(root));
            FeatureController featureController = loader.getController();
            featureController.initialize();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showReservationsServicesPage() {
        try {
            FXMLLoader loader = new FXMLLoader(ScreenManager.class.getResource("reservationsServices-screen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Reservations Services");
            stage.setScene(new Scene(root));
            ReservationsServicesController reservationsServicesController = loader.getController();
            reservationsServicesController.initialize();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showServicesPage() {
        try {
            FXMLLoader loader = new FXMLLoader(ScreenManager.class.getResource("service-screen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Services");
            stage.setScene(new Scene(root));
            ServiceController serviceController = loader.getController();
            serviceController.initialize();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showRoomsPage() {
        try {
            FXMLLoader loader = new FXMLLoader(ScreenManager.class.getResource("room-screen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Rooms");
            stage.setScene(new Scene(root));
            RoomController roomController = loader.getController();
            roomController.initialize();
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showNewRoomPage() {
        try {
            FXMLLoader loader = new FXMLLoader(ScreenManager.class.getResource("newRoom-screen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Adding New Room");
            stage.setScene(new Scene(root));
            NewRoomController newRoomController = loader.getController();
            newRoomController.initialize();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showEditPage() {
        try {
            FXMLLoader loader = new FXMLLoader(ScreenManager.class.getResource("editReservation-screen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Edit Reservation");
            stage.setScene(new Scene(root));
            EditReservationController editReservationController = loader.getController();
            editReservationController.initialize();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showDeleteRoomPage() {
        try {
            FXMLLoader loader = new FXMLLoader(ScreenManager.class.getResource("deleteRoom-screen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Delete Room");
            stage.setScene(new Scene(root));
            DeleteRoomController deleteRoomController = loader.getController();
            deleteRoomController.initialize();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void showEditRoomPage() {
        try {
            FXMLLoader loader = new FXMLLoader(ScreenManager.class.getResource("editRoom-screen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Edit Room");
            stage.setScene(new Scene(root));
            EditRoomController editRoomController = loader.getController();
            editRoomController.initialize();
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void showNewReservationPage() {
        try {
            FXMLLoader loader = new FXMLLoader(ScreenManager.class.getResource("newReservation-screen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("New Reservation");
            stage.setScene(new Scene(root));
            NewReservationController newReservationController = loader.getController();
            newReservationController.initialize();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showNewFeaturePage() {
        try {
            FXMLLoader loader = new FXMLLoader(ScreenManager.class.getResource("newFeature-screen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("New Feature");
            stage.setScene(new Scene(root));
            NewFeatureController newFeatureController = loader.getController();
            newFeatureController.initialize();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showEditFeaturePage() {
        try {
            FXMLLoader loader = new FXMLLoader(ScreenManager.class.getResource("editFeature-screen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Edit Feature");
            stage.setScene(new Scene(root));
            EditFeatureController editFeatureController = loader.getController();
            editFeatureController.initialize();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showNewServicePage() {
        try {
            FXMLLoader loader = new FXMLLoader(ScreenManager.class.getResource("newServices-screen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("New Service");
            stage.setScene(new Scene(root));
            NewServicesController newServicesController = loader.getController();
            newServicesController.initialize();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showEditServicesPage() {
        try {
            FXMLLoader loader = new FXMLLoader(ScreenManager.class.getResource("editServices-screen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Edit Service");
            stage.setScene(new Scene(root));
            EditServicesController editServicesController = loader.getController();
            editServicesController.initialize();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showDeleteServicesPage() {
        try {
            FXMLLoader loader = new FXMLLoader(ScreenManager.class.getResource("deleteServices-screen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Delete Service");
            stage.setScene(new Scene(root));
            DeleteServicesController deleteServicesController = loader.getController();
            deleteServicesController.initialize();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showDeleteFeaturePage() {
        try {
            FXMLLoader loader = new FXMLLoader(ScreenManager.class.getResource("deleteFeature-screen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Delete Feature");
            stage.setScene(new Scene(root));
            DeleteFeatureController deleteFeatureController = loader.getController();
            deleteFeatureController.initialize();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showDeleteReservationPage() {
        try {
            FXMLLoader loader = new FXMLLoader(ScreenManager.class.getResource("deleteReservation-screen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Delete Reservation");
            stage.setScene(new Scene(root));
            DeleteReservationController deleteReservationController = loader.getController();
            deleteReservationController.initialize();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showDeleteCustomerPage() {
        try {
            FXMLLoader loader = new FXMLLoader(ScreenManager.class.getResource("deleteCustomer-screen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Delete Customer");
            stage.setScene(new Scene(root));
            DeleteCustomerController deleteCustomerController = loader.getController();
            deleteCustomerController.initialize();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showEditCustomerPage() {
        try {
            FXMLLoader loader = new FXMLLoader(ScreenManager.class.getResource("editCustomer-screen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Edit Customer");
            stage.setScene(new Scene(root));
            EditCustomerController editCustomerController = loader.getController();
            editCustomerController.initialize();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showNewCustomerPage() {
        try {
            FXMLLoader loader = new FXMLLoader(ScreenManager.class.getResource("newCustomer-screen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("New Customer");
            stage.setScene(new Scene(root));
            NewCustomerController newCustomerController = loader.getController();
            newCustomerController.initialize();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showNewReservationsServicesPage() {
        try {
            FXMLLoader loader = new FXMLLoader(ScreenManager.class.getResource("newReservationsServices-screen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("New Reservation Service");
            stage.setScene(new Scene(root));
            NewReservationsServicesController newReservationsServicesController = loader.getController();
            newReservationsServicesController.initialize();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showDeleteReservationsServicesPage() {
        try {
            FXMLLoader loader = new FXMLLoader(ScreenManager.class.getResource("deleteReservationsServices-screen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Delete Reservation Service");
            stage.setScene(new Scene(root));
            DeleteReservationsServicesController deleteReservationsServicesController = loader.getController();
            deleteReservationsServicesController.initialize();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showEditReservationsServicesPage() {
        try {
            FXMLLoader loader = new FXMLLoader(ScreenManager.class.getResource("editReservationsServices-screen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Edit Reservation Service");
            stage.setScene(new Scene(root));
            EditReservationsServicesController editReservationsServicesController = loader.getController();
            editReservationsServicesController.initialize();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showCustomerPaymentsPage(){
        try {
            FXMLLoader loader = new FXMLLoader(ScreenManager.class.getResource("customerPayments-screen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Payments");
            stage.setScene(new Scene(root));
            CustomerPaymentsController customerPaymentsController = loader.getController();
            customerPaymentsController.initialize();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
