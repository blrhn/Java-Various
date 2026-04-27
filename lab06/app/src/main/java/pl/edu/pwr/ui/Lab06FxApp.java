package pl.edu.pwr.ui;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import pl.edu.pwr.Lab06Application;
import pl.edu.pwr.ui.config.FxmlView;
import pl.edu.pwr.ui.config.StageManager;

public class Lab06FxApp extends Application {
    private static Stage stage;

    private ConfigurableApplicationContext applicationContext;
    private StageManager stageManager;

    @Override
    public void init() {
        applicationContext = new SpringApplicationBuilder(Lab06Application.class).run();
    }

    @Override
    public void stop() {
        applicationContext.close();
        stage.close();
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        stageManager = applicationContext.getBean(StageManager.class, primaryStage);
        stageManager.switchScene(FxmlView.MAIN);
    }
}
