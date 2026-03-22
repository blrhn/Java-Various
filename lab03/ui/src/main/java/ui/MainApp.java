package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lib.quiz.QuizState;
import ui.controller.ViewController;

import java.io.IOException;
import java.util.ResourceBundle;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        QuizState quizState = DataInitializer.initData();

        ResourceBundle resources = ResourceBundle.getBundle("i18n.Language");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("quiz-view.fxml"), resources);

        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
        stage.setResizable(false);

        ViewController controller = loader.getController();
        controller.setQuizState(quizState);
        controller.setResourceBundle(resources);
        controller.startQuiz();
    }

    void main(String[] args) {
        launch(args);
    }
}
