module com.example.sahinhotel {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.example.sahinhotel to javafx.fxml;
    exports com.example.sahinhotel;
    exports com.example.sahinhotel.RoomTypes;
    opens com.example.sahinhotel.RoomTypes to javafx.fxml;
    exports com.example.sahinhotel.EditItemsPages;
    opens com.example.sahinhotel.EditItemsPages to javafx.fxml;
    exports com.example.sahinhotel.AddingNewItemsPages;
    opens com.example.sahinhotel.AddingNewItemsPages to javafx.fxml;
    exports com.example.sahinhotel.DeletedItemsPages;
    opens com.example.sahinhotel.DeletedItemsPages to javafx.fxml;
    exports com.example.sahinhotel.ControllersPackage;
    opens com.example.sahinhotel.ControllersPackage to javafx.fxml;
}