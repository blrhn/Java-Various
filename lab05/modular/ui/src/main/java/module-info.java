module ui {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires impl;
    requires spi;

    exports ui to javafx.graphics;

    opens ui to javafx.fxml;
    opens ui.controller to javafx.fxml;

    uses ex.api.AnalysisService;
}