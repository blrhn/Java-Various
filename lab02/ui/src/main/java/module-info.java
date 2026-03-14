module ui {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires spi;
    requires lib;

    exports ui;
    exports ui.controller;

    opens ui to javafx.fxml;
    opens ui.controller to javafx.fxml;

    uses spi.FileParser;
    uses spi.FileReader;
    uses spi.FileRenderer;
    uses spi.FileAnalyzer;
}