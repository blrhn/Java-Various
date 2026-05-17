package ui.controller;

import jakarta.xml.bind.JAXBException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Setter;
import xmlreader.abstractions.XMLRunner;
import xmlreader.jaxb.JAXBRunner;
import xmlreader.jaxb.models.ChannelJAXB;
import xmlreader.jaxb.models.ItemJAXB;
import xmlreader.jaxp.dom.DOMRunner;
import xmlreader.jaxp.models.ChannelJAXP;
import xmlreader.jaxp.models.ItemJAXP;
import xmlreader.jaxp.sax.SAXBRunner;
import xmlreader.xslt.XSLTTransformer;

import java.io.File;
import java.time.ZonedDateTime;

public class ViewController {
    @FXML private TextField xmlDir;
    @FXML private TextField xmlDirR;
    @FXML private TextField xsltDir;

    @FXML private TextField xmlDirRJAXB;

    @FXML private TableView<ItemJAXP> itemTable;
    @FXML private TableColumn<ItemJAXP, String> colTitle;
    @FXML private TableColumn<ItemJAXP, String> colDescription;
    @FXML private TableColumn<ItemJAXP, Double> colRate;
    @FXML private TableColumn<ItemJAXP, Double> colInverseRate;
    @FXML private TableColumn<ItemJAXP, ZonedDateTime> colPubDate;

    @FXML private TableView<ItemJAXB> itemTableJAXB;
    @FXML private TableColumn<ItemJAXB, String> colTitle1;
    @FXML private TableColumn<ItemJAXB, String> colDescription1;
    @FXML private TableColumn<ItemJAXB, Double> colRate1;
    @FXML private TableColumn<ItemJAXB, Double> colInverseRate1;
    @FXML private TableColumn<ItemJAXB, ZonedDateTime> colPubDate1;

    @FXML private WebView htmlView;

    @Setter
    private Stage stage;

    private File selectedXmlRFile;
    private File selectedXmlFile;
    private File selectedXsltFile;

    @FXML
    public void initialize() {
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colRate.setCellValueFactory(new PropertyValueFactory<>("exchangeRate"));
        colInverseRate.setCellValueFactory(new PropertyValueFactory<>("inverseRate"));
        colPubDate.setCellValueFactory(new PropertyValueFactory<>("pubDate"));

        colTitle1.setCellValueFactory(new PropertyValueFactory<>("title"));
        colDescription1.setCellValueFactory(new PropertyValueFactory<>("description"));
        colRate1.setCellValueFactory(new PropertyValueFactory<>("exchangeRate"));
        colInverseRate1.setCellValueFactory(new PropertyValueFactory<>("inverseRate"));
        colPubDate1.setCellValueFactory(new PropertyValueFactory<>("pubDate"));
    }

    @FXML
    void chooseXmlR(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        selectedXmlRFile = fileChooser.showOpenDialog(stage);
        if (selectedXmlRFile != null) {
            xmlDirR.setText(selectedXmlRFile.getAbsolutePath());
            itemTable.getItems().clear();
        }
    }

    @FXML
    void chooseXml(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        selectedXmlFile = fileChooser.showOpenDialog(stage);
        if (selectedXmlFile != null) {
            xmlDir.setText(selectedXmlFile.getAbsolutePath());
        }
    }

    @FXML
    void chooseXlt(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        selectedXsltFile = fileChooser.showOpenDialog(stage);
        if (selectedXsltFile != null) {
            xsltDir.setText(selectedXsltFile.getAbsolutePath());
        }
    }

    @FXML
    void chooseXmlRJAXB(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        selectedXmlRFile = fileChooser.showOpenDialog(stage);
        if (selectedXmlRFile != null) {
            xmlDirRJAXB.setText(selectedXmlRFile.getAbsolutePath());
            itemTableJAXB.getItems().clear();
        }
    }

    @FXML
    void render(ActionEvent event) {
        try {
            String htmlContent = XSLTTransformer.run(selectedXmlFile, selectedXsltFile);

            htmlView.getEngine().loadContent(htmlContent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleParsing(XMLRunner runner) throws Exception {
        ChannelJAXP channel = runner.run(selectedXmlRFile);

        if (channel != null) {
            itemTable.setItems(FXCollections.observableArrayList(channel.getItems()));
        }
    }

    @FXML
    void runDOM(ActionEvent event) throws Exception {
        handleParsing(new DOMRunner());

    }

    @FXML
    void runJAXB(ActionEvent event) throws JAXBException {
        ChannelJAXB channelJAXB = JAXBRunner.run(selectedXmlRFile);
        itemTableJAXB.setItems(FXCollections.observableArrayList(channelJAXB.getItems()));
    }

    @FXML
    void runSAX(ActionEvent event) throws Exception {
        handleParsing(new SAXBRunner());
    }
}