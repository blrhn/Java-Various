package pl.edu.pwr;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.edu.pwr.ui.Lab06FxApp;

// https://bell-sw.com/blog/creating-modern-desktop-apps-with-javafx-and-spring-boot/

@SpringBootApplication
public class Lab06Application {
    void main(String[] args) {
        Application.launch(Lab06FxApp.class, args);
    }
}
