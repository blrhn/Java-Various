package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.controller.ViewController;

import java.io.IOException;
import java.util.ResourceBundle;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ResourceBundle resources = ResourceBundle.getBundle("bundles.Language");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("quiz-view.fxml"), resources);
        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setTitle("File Explorer");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
        stage.setResizable(false);

        ViewController controller = loader.getController();
        controller.setScene(scene);
    }

    void main(String[] args) {
        launch(args);
    }
}
