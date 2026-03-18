package ui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class ViewController {
    Scene scene;

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    @FXML
    void changeToEnglish(ActionEvent event) throws IOException {
        switchLanguage(Locale.ENGLISH);
    }

    @FXML
    void changeToPolish(ActionEvent event) throws IOException {
        switchLanguage(new Locale("pl", "PL"));
    }

    private void switchLanguage(Locale locale) throws IOException {
        ResourceBundle resources = ResourceBundle.getBundle("bundles.Language", locale);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/quiz-view.fxml"), resources);
        Parent root = loader.load();

        scene.setRoot(root);

        ViewController controller = loader.getController();
        controller.setScene(scene);
    }
}
