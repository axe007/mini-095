module com.group8 {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.group8 to javafx.fxml;
    opens com.group8.controllers to javafx.fxml;

    exports com.group8;
}