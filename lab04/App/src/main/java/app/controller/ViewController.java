package app.controller;

import classloader.ProcessClassLoader;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import processing.Processor;

import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewController {
    @FXML private TextArea resultView;
    @FXML private ListView<Path> loadableClasses;
    @FXML private ListView<Processor> getInfos;
    @FXML private TextField taskTextField;
    @FXML private TextField rootArea;
    @FXML private ProgressBar progressBar;

    private Stage stage;
    private final ObservableList<Path> loadableClassesObservable = FXCollections.observableArrayList();
    private final Map<Path, Processor> pathProcessorMap = new HashMap<>();

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initialize() {
        loadableClasses.setItems(loadableClassesObservable);
        loadableClasses.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        getInfos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                taskTextField.setText(newValue.getInfo());
            } else {
                taskTextField.setText("");
            }
        });
    }

    @FXML
    private void chooseCatalog(ActionEvent event) {
        Path chosenDirectory = getChosenDirectory();
        populateLoadableClasses(chosenDirectory);
    }

    @FXML
    void load(ActionEvent event) {
        List<Processor> infos = new ArrayList<>();
        loadableClasses.getSelectionModel().getSelectedItems().forEach(path -> {
            try {
                ProcessClassLoader processClassLoader = new ProcessClassLoader(Path.of(rootArea.getText()));
                Processor processor = (Processor) processClassLoader.loadClass(path).getConstructor().newInstance();

                infos.add(processor);
                pathProcessorMap.put(path, processor);

            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                     InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });

        getInfos.setItems(FXCollections.observableList(infos));
    }

    @FXML
    void chooseRoot(ActionEvent event) {
        Path root = getChosenDirectory();
        rootArea.setText(root.toString());
    }


    @FXML
    void process(ActionEvent event) {
        Processor p =  getInfos.getSelectionModel().getSelectedItem();
        progressBar.setProgress(0.0);

        p.submitTask(taskTextField.getText(), s -> Platform.runLater(() -> {
            double currentProgress = s.getProgress();
            progressBar.setProgress(currentProgress / 100.0);

            if (currentProgress >= 100) {
                resultView.setText(p.getResult());
            }
        }));
    }

    @FXML
    void unload(ActionEvent event) {
        loadableClasses.getSelectionModel().getSelectedItems().forEach(path -> {
            Processor toUnload = pathProcessorMap.get(path);

            getInfos.getItems().remove(toUnload);
            pathProcessorMap.remove(path);
        });

        loadableClasses.getSelectionModel().clearSelection();
        progressBar.setProgress(0.0);
        System.gc();
    }

    private Path getChosenDirectory() {
        DirectoryChooser fileChooser = new DirectoryChooser();

        return fileChooser.showDialog(stage).toPath();
    }

    private void populateLoadableClasses(Path directory) {
        ProcessClassLoader processClassLoader = new ProcessClassLoader(directory);

        loadableClassesObservable.clear();
        loadableClassesObservable.addAll(processClassLoader.getLoadableClasses());
    }
}