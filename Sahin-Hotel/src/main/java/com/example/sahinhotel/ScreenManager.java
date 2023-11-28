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
import java.util.ArrayList;
import java.util.List;

/**
 * @since 11/3/2023
 */

public class ScreenManager {
    static List<Stage> openStages = new ArrayList<>();


    public static void showCustomersPage() {
        try {
            closeOpenStages();
            FXMLLoader loader = new FXMLLoader(ScreenManager.class.getResource("customer-screen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Customers");
            stage.setScene(new Scene(root));
            CustomerController customerController = loader.getController();
            customerController.initialize();
            openStages.add(stage);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showAllReservations() {
        try {
            closeOpenStages();
            FXMLLoader loader = new FXMLLoader(ScreenManager.class.getResource("main-screen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Reservations");
            stage.setScene(new Scene(root));
            MainController mainController = loader.getController();
            mainController.initialize();
            openStages.add(stage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showFeaturesPage() {
        try {
            closeOpenStages();
            FXMLLoader loader = new FXMLLoader(ScreenManager.class.getResource("feature-screen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Features");
            stage.setScene(new Scene(root));
            FeatureController featureController = loader.getController();
            featureController.initialize();
            openStages.add(stage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showReservationsServicesPage() {
        try {
            closeOpenStages();
            FXMLLoader loader = new FXMLLoader(ScreenManager.class.getResource("reservationsServices-screen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Reservations Services");
            stage.setScene(new Scene(root));
            ReservationsServicesController reservationsServicesController = loader.getController();
            reservationsServicesController.initialize();
            openStages.add(stage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showServicesPage() {
        try {
            closeOpenStages();
            FXMLLoader loader = new FXMLLoader(ScreenManager.class.getResource("service-screen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Services");
            stage.setScene(new Scene(root));
            ServiceController serviceController = loader.getController();
            serviceController.initialize();
            openStages.add(stage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showRoomsPage() {
        try {
            closeOpenStages();
            FXMLLoader loader = new FXMLLoader(ScreenManager.class.getResource("room-screen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Rooms");
            stage.setScene(new Scene(root));
            RoomController roomController = loader.getController();
            roomController.initialize();
            openStages.add(stage);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showNewRoomPage() {
        try {
            closeOpenStages();
            FXMLLoader loader = new FXMLLoader(ScreenManager.class.getResource("newRoom-screen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Adding New Room");
            stage.setScene(new Scene(root));
            NewRoomController newRoomController = loader.getController();
            newRoomController.initialize();
            openStages.add(stage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showEditPage() {
        try {
            closeOpenStages();
            FXMLLoader loader = new FXMLLoader(ScreenManager.class.getResource("editReservation-screen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Edit Reservation");
            stage.setScene(new Scene(root));
            EditReservationController editReservationController = loader.getController();
            editReservationController.initialize();
            openStages.add(stage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showDeleteRoomPage() {
        try {
            closeOpenStages();
            FXMLLoader loader = new FXMLLoader(ScreenManager.class.getResource("deleteRoom-screen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Delete Room");
            stage.setScene(new Scene(root));
            DeleteRoomController deleteRoomController = loader.getController();
            deleteRoomController.initialize();
            openStages.add(stage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void showEditRoomPage() {
        try {
            closeOpenStages();
            FXMLLoader loader = new FXMLLoader(ScreenManager.class.getResource("editRoom-screen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Edit Room");
            stage.setScene(new Scene(root));
            EditRoomController editRoomController = loader.getController();
            editRoomController.initialize();
            openStages.add(stage);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void showNewReservationPage() {
        try {
            closeOpenStages();
            FXMLLoader loader = new FXMLLoader(ScreenManager.class.getResource("newReservation-screen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("New Reservation");
            stage.setScene(new Scene(root));
            NewReservationController newReservationController = loader.getController();
            newReservationController.initialize();
            openStages.add(stage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showNewFeaturePage() {
        try {
            closeOpenStages();
            FXMLLoader loader = new FXMLLoader(ScreenManager.class.getResource("newFeature-screen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("New Feature");
            stage.setScene(new Scene(root));
            NewFeatureController newFeatureController = loader.getController();
            newFeatureController.initialize();
            openStages.add(stage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showEditFeaturePage() {
        try {
            closeOpenStages();
            FXMLLoader loader = new FXMLLoader(ScreenManager.class.getResource("editFeature-screen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Edit Feature");
            stage.setScene(new Scene(root));
            EditFeatureController editFeatureController = loader.getController();
            editFeatureController.initialize();
            openStages.add(stage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showNewServicePage() {
        try {
            closeOpenStages();
            FXMLLoader loader = new FXMLLoader(ScreenManager.class.getResource("newServices-screen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("New Service");
            stage.setScene(new Scene(root));
            NewServicesController newServicesController = loader.getController();
            newServicesController.initialize();
            openStages.add(stage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showEditServicesPage() {
        try {
            closeOpenStages();
            FXMLLoader loader = new FXMLLoader(ScreenManager.class.getResource("editServices-screen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Edit Service");
            stage.setScene(new Scene(root));
            EditServicesController editServicesController = loader.getController();
            editServicesController.initialize();
            openStages.add(stage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showDeleteServicesPage() {
        try {
            closeOpenStages();
            FXMLLoader loader = new FXMLLoader(ScreenManager.class.getResource("deleteServices-screen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Delete Service");
            stage.setScene(new Scene(root));
            DeleteServicesController deleteServicesController = loader.getController();
            deleteServicesController.initialize();
            openStages.add(stage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showDeleteFeaturePage() {
        try {
            closeOpenStages();
            FXMLLoader loader = new FXMLLoader(ScreenManager.class.getResource("deleteFeature-screen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Delete Feature");
            stage.setScene(new Scene(root));
            DeleteFeatureController deleteFeatureController = loader.getController();
            deleteFeatureController.initialize();
            openStages.add(stage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showDeleteReservationPage() {
        try {
            closeOpenStages();
            FXMLLoader loader = new FXMLLoader(ScreenManager.class.getResource("deleteReservation-screen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Delete Reservation");
            stage.setScene(new Scene(root));
            DeleteReservationController deleteReservationController = loader.getController();
            deleteReservationController.initialize();
            openStages.add(stage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showDeleteCustomerPage() {
        try {
            closeOpenStages();
            FXMLLoader loader = new FXMLLoader(ScreenManager.class.getResource("deleteCustomer-screen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Delete Customer");
            stage.setScene(new Scene(root));
            DeleteCustomerController deleteCustomerController = loader.getController();
            deleteCustomerController.initialize();
            openStages.add(stage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showEditCustomerPage() {
        try {
            closeOpenStages();
            FXMLLoader loader = new FXMLLoader(ScreenManager.class.getResource("editCustomer-screen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Edit Customer");
            stage.setScene(new Scene(root));
            EditCustomerController editCustomerController = loader.getController();
            editCustomerController.initialize();
            openStages.add(stage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showNewCustomerPage() {
        try {
            closeOpenStages();
            FXMLLoader loader = new FXMLLoader(ScreenManager.class.getResource("newCustomer-screen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("New Customer");
            stage.setScene(new Scene(root));
            NewCustomerController newCustomerController = loader.getController();
            newCustomerController.initialize();
            openStages.add(stage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showNewReservationsServicesPage() {
        try {
            closeOpenStages();
            FXMLLoader loader = new FXMLLoader(ScreenManager.class.getResource("newReservationsServices-screen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("New Reservation Service");
            stage.setScene(new Scene(root));
            NewReservationsServicesController newReservationsServicesController = loader.getController();
            newReservationsServicesController.initialize();
            openStages.add(stage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showDeleteReservationsServicesPage() {
        try {
            closeOpenStages();
            FXMLLoader loader = new FXMLLoader(ScreenManager.class.getResource("deleteReservationsServices-screen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Delete Reservation Service");
            stage.setScene(new Scene(root));
            DeleteReservationsServicesController deleteReservationsServicesController = loader.getController();
            deleteReservationsServicesController.initialize();
            openStages.add(stage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showEditReservationsServicesPage() {
        try {
            closeOpenStages();
            FXMLLoader loader = new FXMLLoader(ScreenManager.class.getResource("editReservationsServices-screen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Edit Reservation Service");
            stage.setScene(new Scene(root));
            EditReservationsServicesController editReservationsServicesController = loader.getController();
            editReservationsServicesController.initialize();
            openStages.add(stage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showCustomerPaymentsPage() {
        try {
            closeOpenStages();
            FXMLLoader loader = new FXMLLoader(ScreenManager.class.getResource("customerPayments-screen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Payments");
            stage.setScene(new Scene(root));
            CustomerPaymentsController customerPaymentsController = loader.getController();
            customerPaymentsController.initialize();
            openStages.add(stage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void closeOpenStages() {
        for (Stage stage : openStages) {
            stage.close();
        }
        openStages.clear();
    }
}
