package pwr.edu.pl.ui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pwr.edu.pl.lib.Zipper;

import java.io.File;
import java.util.List;

public class ViewController {
    @FXML private Button archiveButton;
    @FXML private TextField archiveNameTextField;
    @FXML private GridPane baseGrid;
    @FXML private TableView<?> checkTableView;
    @FXML private Button chooseArchiveButton;
    @FXML private Button chooseDirectoryButton;
    @FXML private Button chooseFilesAndArchivesButton;
    @FXML private Button chooseFilesButton;
    @FXML private Button generateHashButton;
    @FXML private CheckBox generateHashCheckbox;
    @FXML private TextArea hashTextArea;
    @FXML private ListView<?> selectedArchiveListView;
    @FXML private ListView<File> selectedFilesListView;

    private Stage stage;
    private final ObservableList<File> selectedFilesToBeArchived = FXCollections.observableArrayList();
    private final Zipper zipper = new Zipper();

    public void initialize() {
        markTextAreasAsReadOnly();
    }

    @FXML
    void archivize(ActionEvent event) {
    }

    @FXML
    void chooseDirectory(ActionEvent event) {
        DirectoryChooser fileChooser = new DirectoryChooser();
        File selectedFilesTemp = fileChooser.showDialog(stage);

        if (selectedFilesTemp != null) {
            selectedFilesToBeArchived.add(selectedFilesTemp);
        }

        populateFilesToBeArchived();

    }

    @FXML
    void chooseFile(ActionEvent event) {

    }

    @FXML
    void chooseFiles(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        List<File> chosenFiles = fileChooser.showOpenMultipleDialog(stage);

        if (chosenFiles != null) {
            selectedFilesToBeArchived.addAll(chosenFiles);
        }

        populateFilesToBeArchived();
    }

    @FXML
    void chooseFilesAndArchives(ActionEvent event) {

    }

    @FXML
    void generateHash(ActionEvent event) {

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void markTextAreasAsReadOnly() {
        hashTextArea.setEditable(false);
    }

    private void populateFilesToBeArchived() {
        selectedFilesListView.setItems(selectedFilesToBeArchived);
    }
}
