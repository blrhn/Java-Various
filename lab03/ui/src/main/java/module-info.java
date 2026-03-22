module ui {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires lib;

    exports ui;
    exports ui.controller;

    opens ui to javafx.fxml;
    opens ui.controller to javafx.fxml;

}