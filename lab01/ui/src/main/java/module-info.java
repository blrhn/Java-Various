module pwr.edu.pl.ui {
    requires pwr.edu.pl.lib;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    exports ui;
    exports ui.controller;

    opens ui to javafx.fxml;
    opens ui.controller to javafx.fxml;
}