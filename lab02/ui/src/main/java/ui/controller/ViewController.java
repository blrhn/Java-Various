package ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;

public class ViewController {
    @FXML private TextField directoryTextField;
    @FXML private TreeView<?> fileTreeView;
    @FXML private TextArea previewTextArea;

    public void initialize() {
        markFieldsAsNonEditable();
    }

    private void markFieldsAsNonEditable() {
        previewTextArea.setEditable(false);
        directoryTextField.setEditable(false);
    }
}
