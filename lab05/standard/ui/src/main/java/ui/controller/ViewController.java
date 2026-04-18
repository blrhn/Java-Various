package ui.controller;

import ex.api.AnalysisException;
import ex.api.AnalysisService;
import ex.api.DataSet;
import impl.utils.DataReader;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ui.utils.AnalysisServiceLoader;

import java.io.File;

public class ViewController {
    @FXML private TextField chosenFileText;
    @FXML private TableView<String[]> dataTableView;
    @FXML private Spinner<Integer> iterationsValue;
    @FXML private Spinner<Integer> kValue;
    @FXML private RadioButton kMean;
    @FXML private RadioButton kMedian;
    @FXML private TabPane tabPane;
    @FXML private TableView<String[]> resultsTableView;


    private Stage stage;
    private AnalysisService kMeanAnalysis;
    private AnalysisService kMedianAnalysis;
    private DataSet dataSet;

    public void initialize() {
        dataTableView.setEditable(true);
        kValue.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 7));
        iterationsValue.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 100));
        initAnalysisService();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void initAnalysisService() {
        kMeanAnalysis = AnalysisServiceLoader.loadAnalysisService("k-mean")
                .orElseThrow(() -> new IllegalStateException("k-mean service not found"));
        kMedianAnalysis = AnalysisServiceLoader.loadAnalysisService("k-median")
                .orElseThrow(() -> new IllegalStateException("k-median service not found"));
    }

    @FXML
    void chooseFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File chosenFile = fileChooser.showOpenDialog(stage);

        if (chosenFile != null) {
            chosenFileText.setText(chosenFile.getAbsolutePath());

            dataSet = DataReader.readDataSet(chosenFile.getAbsolutePath());
            populateTableView(dataSet, dataTableView);
        }
    }

    void populateTableView(DataSet dataSet, TableView<String[]> tableView) {
        tableView.getColumns().clear();
        tableView.getItems().clear();

        String[] header = dataSet.getHeader();
        String[][] data =  dataSet.getData();

        for (int i = 0; i < header.length; i++) {
            TableColumn<String[], String> column = new TableColumn<>(header[i]);
            final int columnIdx = i;

            column.setCellFactory(TextFieldTableCell.forTableColumn());

            column.setCellValueFactory(
                    d -> new SimpleStringProperty(d.getValue()[columnIdx]));

            column.setOnEditCommit(e -> {
                String[] row = e.getRowValue();
                row[columnIdx] = e.getNewValue();

                dataSet.setData(data);
            });

            tableView.getColumns().add(column);
        }

        for (String[] row : data) {
            tableView.getItems().add(row);
        }
    }

    @FXML
    void performAnalysis(ActionEvent event)  {
        Platform.runLater(() -> {
            try {
                if (kMean.isSelected()) {
                    calculateKMean();
                } else if (kMedian.isSelected()) {
                    calculateKMedian();
                }
            } catch (AnalysisException e) {
                e.printStackTrace();
            }
        });
    }

    void calculateKMean() throws AnalysisException {
        kMeanAnalysis.setOptions(new String[]{kValue.getValue().toString(), iterationsValue.getValue().toString()});
        kMeanAnalysis.submit(dataSet);
        DataSet ds = kMeanAnalysis.retrieve(false);

        tabPane.getSelectionModel().select(1);
        populateTableView(ds, resultsTableView);
    }


    void calculateKMedian() throws AnalysisException {
        kMedianAnalysis.setOptions(new String[]{kValue.getValue().toString(), iterationsValue.getValue().toString()});
        kMedianAnalysis.submit(dataSet);
        DataSet ds = kMedianAnalysis.retrieve(false);

        tabPane.getSelectionModel().select(1);
        populateTableView(ds, resultsTableView);
    }
}
