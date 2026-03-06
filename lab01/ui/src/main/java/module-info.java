module pwr.edu.pl.ui {
    requires pwr.edu.pl.lib;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    exports pwr.edu.pl.ui;
    exports pwr.edu.pl.ui.controller;

    opens pwr.edu.pl.ui to javafx.fxml;
    opens pwr.edu.pl.ui.controller to javafx.fxml;
}