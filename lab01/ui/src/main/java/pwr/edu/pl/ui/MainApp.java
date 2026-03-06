package pwr.edu.pl.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pwr.edu.pl.ui.controller.ViewController;

import java.io.IOException;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException  {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("zipper-view.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setTitle("File zipper");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
        stage.setResizable(false);

        ViewController controller = loader.getController();
        controller.setStage(stage);
    }

    void main(String[] args) {
        launch(args);
    }
}
