package ui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import lib.cache.CacheResult;
import lib.person.model.ProcessedPersonsData;
import lib.cache.WeakRefCache;
import lib.person.model.Person;
import lib.person.stats.PersonStats;
import spi.FileAnalyzer;
import spi.FileParser;
import spi.FileReader;
import spi.FileRenderer;
import ui.model.FileTreeItem;
import ui.utils.ServiceLoaderHelper;

import java.io.File;
import java.util.List;

public class ViewController {
    @FXML private TextField directoryTextField;
    @FXML private TreeView<File> fileTreeView;
    @FXML private StackPane previewStackPane;
    @FXML private Label loadLabel;
    @FXML private Label totalCountLabel;
    @FXML private Label mostPopularNameLabel;
    @FXML private Label mostPopularSurnameLabel;
    @FXML private Label peopleWithoutSecondNameLabel;

    private Stage stage;
    private FileReader fileReader;
    private FileParser<Person> fileParser;
    private FileRenderer fileRenderer;
    private FileAnalyzer<PersonStats, Person> fileAnalyzer;
    private final WeakRefCache<File, ProcessedPersonsData> cache = new WeakRefCache<>();

    public void initialize() {
        markFieldsAsNonEditable();
        addFileTreeViewListener();
        initInterfaces();
    }

    private void markFieldsAsNonEditable() {
        directoryTextField.setEditable(false);
    }

    private void addFileTreeViewListener() {
        fileTreeView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) ->
                    showResults(newValue)
        );
    }

    @SuppressWarnings("unchecked")
    private void initInterfaces() {
        fileReader = ServiceLoaderHelper
                .getService(FileReader.class, reader -> reader.supports("csv"))
                .orElseThrow(() -> new IllegalStateException("FileReader not found"));
        fileParser = ServiceLoaderHelper
                .getService(FileParser.class, parser -> parser.supports("person"))
                .orElseThrow(() -> new IllegalStateException("FileParser not found"));
        fileRenderer = ServiceLoaderHelper
                .getService(FileRenderer.class, renderer -> renderer.supports("text"))
                .orElseThrow(() -> new IllegalStateException("FileRenderer not found"));
        fileAnalyzer = ServiceLoaderHelper
                .getService(FileAnalyzer.class, analyzer -> analyzer.supports("person"))
                .orElseThrow(() -> new IllegalStateException("FileAnalyzer not found"));
    }

    private void showResults(TreeItem<File> item) {
        if (!item.isLeaf()) {
            return;
        }

        File file = item.getValue();
        CacheResult<ProcessedPersonsData> result = cache.getOrCompute(file, () -> dataSupplier(file));

        populateDataContent(result);
        populateStatsLabels(result);
    }

    private void populateDataContent(CacheResult<ProcessedPersonsData> result) {
        loadLabel.setText(result.loadedFromCache() ? "Załadowano z pamięci" : "Załadowano z dysku");
        previewStackPane.getChildren().setAll(fileRenderer.render(result.processedData().previewLines(), 7));
    }

    private void populateStatsLabels(CacheResult<ProcessedPersonsData> result) {
        ProcessedPersonsData personData =result.processedData();

        totalCountLabel.setText(String.valueOf(personData.personStats().totalCount()));
        mostPopularNameLabel.setText(personData.personStats().mostPopularFirstName());
        mostPopularSurnameLabel.setText(personData.personStats().mostPopularLastName());
        peopleWithoutSecondNameLabel.setText(String.valueOf(personData.personStats().peopleWithoutSecondNameCount()));
    }

    private ProcessedPersonsData dataSupplier(File file) {
        List<String> lines = fileReader.read(file);
        List<Person> persons = fileParser.parse(lines);
        PersonStats stats = fileAnalyzer.analyze(persons);

        return new ProcessedPersonsData(lines, persons, stats);
    }


    @FXML
    void chooseDirectory(ActionEvent event) {
        DirectoryChooser fileChooser = new DirectoryChooser();
        File chosenDirectory = fileChooser.showDialog(stage);

        if (chosenDirectory != null) {
            directoryTextField.setText(chosenDirectory.getAbsolutePath());
            createTree(chosenDirectory);
        }
    }

    private void createTree(File dir) {
        fileTreeView.setRoot(new FileTreeItem(dir));
        fileTreeView.setShowRoot(true);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
