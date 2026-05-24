module ui {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires client;
    requires io.netty.handler;
    requires common;
    requires server;

    exports ui to javafx.graphics;
    exports ui.controller to javafx.fxml;

    opens ui.controller to javafx.fxml;
}