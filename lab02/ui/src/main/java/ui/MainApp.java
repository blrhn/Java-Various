package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("file-explorer-view.fxml"));

        Scene scene = new Scene(loader.load());
        stage.setTitle("File zipper");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
        stage.setResizable(false);
    }

    void main(String[] args) {
        launch(args);
    }
}