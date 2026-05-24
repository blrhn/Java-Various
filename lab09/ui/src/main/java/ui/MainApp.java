package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.controller.ViewController;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MainApp extends Application {
    public static File server;
    public static String serverPass;
    public static File client;
    public static String clientPass;

    @Override
    public void start(Stage stage) throws IOException  {
        List<String> args = getParameters().getRaw();

        server = new File(args.getFirst());
        serverPass = args.get(1);
        client = new File(args.get(2));
        clientPass = args.get(3);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("chat-view.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setTitle("Chat");
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
