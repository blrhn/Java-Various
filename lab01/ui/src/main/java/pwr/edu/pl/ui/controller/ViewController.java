package pwr.edu.pl.ui.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pwr.edu.pl.lib.validator.HashValidator;
import pwr.edu.pl.lib.validator.model.CheckedFile;
import pwr.edu.pl.lib.zipper.Encoder;
import pwr.edu.pl.lib.zipper.Zipper;

import java.io.File;
import java.util.List;

public class ViewController {
    @FXML private TextField archiveNameTextField;
    @FXML private TableView<CheckedFile> checkTableView;
    @FXML private TableColumn<CheckedFile, File> fileColumn;
    @FXML private TableColumn<CheckedFile, String> expectedHashColumn;
    @FXML private TableColumn<CheckedFile, String> isHashCorrectColumn;
    @FXML private CheckBox generateHashCheckbox;
    @FXML private TextField hashCodeTextField;
    @FXML private ListView<File> selectedArchiveListView;
    @FXML private ListView<File> selectedFilesListView;
    @FXML private TextField saveDirectoryTextField;
    @FXML private TextField saveHashDirectoryTextField;

    private Stage stage;
    private final ObservableList<File> selectedFilesToBeArchived = FXCollections.observableArrayList();
    private final Zipper zipper = new Zipper();

    public void initialize() {
        markTextAreasAsReadOnly();
        populateFilesToBeArchived();
        setTableViewColumns();
    }

    @FXML
    void archivize(ActionEvent event) {
        String archiveName = archiveNameTextField.getText().trim();
        String saveDirectory = saveDirectoryTextField.getText().trim();

        if (selectedFilesToBeArchived.isEmpty() || archiveName.isEmpty() || saveDirectory.isEmpty()) {
            return;
        }

        if (generateHashCheckbox.isSelected()) {
            zipper.createZipArchiveSHA256(selectedFilesToBeArchived, new File(saveDirectory), archiveName);
        } else {
            zipper.createZipArchive(selectedFilesToBeArchived, new File(saveDirectory), archiveName);
        }

    }

    @FXML
    void chooseDirectory(ActionEvent event) {
        File chosenDirectory = getChosenDirectory();

        if (chosenDirectory != null) {
            selectedFilesToBeArchived.add(chosenDirectory);
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
        List<File> chosenFiles = getChosenFiles();

        if (chosenFiles != null) {
            selectedFilesToBeArchived.addAll(chosenFiles);
        }
    }

    private List<File> getChosenFiles() {
        FileChooser fileChooser = new FileChooser();

        return fileChooser.showOpenMultipleDialog(stage);
    }

    private File getChosenDirectory() {
        DirectoryChooser fileChooser = new DirectoryChooser();

        return fileChooser.showDialog(stage);
    }

    @FXML
    void chooseFilesAndArchives(ActionEvent event) {
        List<File> chosenFiles = getChosenFiles();

        if (chosenFiles != null) {
            List<CheckedFile> checkedFiles = HashValidator.validateFiles(chosenFiles);
            checkTableView.setItems(FXCollections.observableList(checkedFiles));
        }
    }

    @FXML
    void chooseSaveDirectory(ActionEvent event) {
        File chosenDirectory = getChosenDirectory();

        if (chosenDirectory != null) {
            saveDirectoryTextField.setText(chosenDirectory.getAbsolutePath());
        }

    }

    @FXML
    void chooseHashSaveDirectory(ActionEvent event) {
        File chosenDirectory = getChosenDirectory();

        if (chosenDirectory != null) {
            saveHashDirectoryTextField.setText(chosenDirectory.getAbsolutePath());
        }
    }

    @FXML
    void generateHash(ActionEvent event) {
        if (selectedArchiveListView.getItems().isEmpty()) {
            return;
        }

        File selectedFile = selectedArchiveListView.getItems().getFirst();
        String hash = Encoder.sha256(selectedFile);
        hashCodeTextField.setText(hash);

        String hashDirectory = saveHashDirectoryTextField.getText().trim();
        if (!hashDirectory.isEmpty()) {
            File saveHashDirectory = new File(hashDirectory, selectedFile.getName());
            zipper.saveHashCode(hash, saveHashDirectory.getAbsolutePath());
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void markTextAreasAsReadOnly() {
        hashCodeTextField.setEditable(false);
        saveDirectoryTextField.setEditable(false);
        saveHashDirectoryTextField.setEditable(false);
    }

    private void populateFilesToBeArchived() {
        selectedFilesListView.setItems(selectedFilesToBeArchived);
    }

    private void setTableViewColumns() {
        fileColumn.setCellValueFactory(
                data -> new SimpleObjectProperty<>(data.getValue().file()));
        expectedHashColumn.setCellValueFactory(
                data -> new SimpleStringProperty(data.getValue().expectedHash()));
        isHashCorrectColumn.setCellValueFactory(
                data -> new SimpleStringProperty(data.getValue().hashStatus().getStatus()));
    }
}
