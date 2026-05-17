module ui {
    requires xmlreader;
    requires jakarta.xml.bind;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires static lombok;
    requires javafx.web;

    exports ui to javafx.graphics;
    exports ui.controller to javafx.fxml;

    opens ui.controller to javafx.fxml;
}