module App {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires refleksja02;

    exports app to javafx.graphics;

    opens app to javafx.fxml;
    opens app.controller to javafx.fxml;
}