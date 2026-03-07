package pwr.edu.pl.ui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pwr.edu.pl.lib.Encoder;
import pwr.edu.pl.lib.Zipper;

import java.io.File;
import java.util.List;

public class ViewController {
    @FXML private TextField archiveNameTextField;
    @FXML private TableView<?> checkTableView;
    @FXML private CheckBox generateHashCheckbox;
    @FXML private TextField hashCodeTextField;
    @FXML private ListView<File> selectedArchiveListView;
    @FXML private ListView<File> selectedFilesListView;

    private Stage stage;
    private final ObservableList<File> selectedFilesToBeArchived = FXCollections.observableArrayList();
    private final Zipper zipper = new Zipper();

    public void initialize() {
        markTextAreasAsReadOnly();
        populateFilesToBeArchived();
    }

    @FXML
    void archivize(ActionEvent event) {
        String archiveName = archiveNameTextField.getText().trim();

        if (selectedFilesToBeArchived.isEmpty() || archiveName.isEmpty()) {
            return;
        }

        if (generateHashCheckbox.isSelected()) {
            zipper.createZipArchiveSHA256(selectedFilesToBeArchived, archiveName);
        } else {
            zipper.createZipArchive(selectedFilesToBeArchived, archiveName);
        }

    }

    @FXML
    void chooseDirectory(ActionEvent event) {
        DirectoryChooser fileChooser = new DirectoryChooser();
        File selectedFilesTemp = fileChooser.showDialog(stage);

        if (selectedFilesTemp != null) {
            selectedFilesToBeArchived.add(selectedFilesTemp);
        }
    }

    @FXML
    void chooseFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File chosenFile = fileChooser.showOpenDialog(stage);

        if (chosenFile != null) {
            selectedArchiveListView.setItems(FXCollections.observableArrayList(chosenFile));
        }
    }

    @FXML
    void chooseFiles(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        List<File> chosenFiles = fileChooser.showOpenMultipleDialog(stage);

        if (chosenFiles != null) {
            selectedFilesToBeArchived.addAll(chosenFiles);
        }

    }

    @FXML
    void chooseFilesAndArchives(ActionEvent event) {

    }

    @FXML
    void generateHash(ActionEvent event) {
        if (selectedArchiveListView.getItems().isEmpty()) {
            return;
        }

        File selectedFile = selectedArchiveListView.getItems().getFirst();
        String hash = Encoder.sha256(selectedFile);
        hashCodeTextField.setText(hash);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void markTextAreasAsReadOnly() {
        hashCodeTextField.setEditable(false);
    }

    private void populateFilesToBeArchived() {
        selectedFilesListView.setItems(selectedFilesToBeArchived);
    }
}
