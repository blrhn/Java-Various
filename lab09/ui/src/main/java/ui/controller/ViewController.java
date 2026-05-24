package ui.controller;

import client.ChatClient;
import common.message.Message;
import common.message.MessageSerializer;
import common.message.MessageType;
import common.refresh.Updatable;
import common.ssl.SslConfig;
import io.netty.handler.ssl.SslContext;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import server.ChatServer;
import ui.MainApp;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Base64;

public class ViewController implements Updatable {
    @FXML
    private TextArea chatArea;

    @FXML
    private TextField filename;

    @FXML
    private TextField message;

    @FXML
    private TextField port;

    @FXML
    private TextField server;

    @FXML
    private TextField username;

    private Stage stage;
    private ChatClient chatClient;
    private File chosenFile;


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    void attachFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        chosenFile = fileChooser.showOpenDialog(stage);

        if (chosenFile != null) {
            filename.setText(chosenFile.getName());
        }
    }

    @FXML
    void connectServer(ActionEvent event) {
        initServer();
    }

    @FXML
    void connectClient(ActionEvent event) {
        initClient();
    }

    @FXML
    void send(ActionEvent event) {
        String sender = username.getText();

        Thread.ofVirtual().start(() -> {
            try {
                if (chosenFile != null) {
                    byte[] fileBytes = Files.readAllBytes(chosenFile.toPath());
                    String base64Content = Base64.getEncoder().encodeToString(fileBytes);

                    Message fileMsg = new Message(
                            MessageType.FILE,
                            sender, base64Content,
                            chosenFile.getName(),
                            LocalDateTime.now().toString());

                    chatClient.send(MessageSerializer.encode(fileMsg));

                    Platform.runLater(() -> {
                        chatArea.appendText("[" + fileMsg.timestamp().substring(11, 16) + "] Ty: wysłano plik " + chosenFile.getName() + "\n");
                        filename.clear();
                        chosenFile = null;
                    });
                } else if (!message.getText().isEmpty()) {
                    Message txtMsg = new Message(
                            MessageType.TEXT,
                            sender,
                            message.getText(),
                            null,
                            LocalDateTime.now().toString()
                    );

                    chatClient.send(MessageSerializer.encode(txtMsg));

                    Platform.runLater(() -> {
                        chatArea.appendText("[" + txtMsg.timestamp().substring(11, 16) + "] Ty: " + txtMsg.content() + "\n");
                        message.clear();
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void initServer() {
        String serverPortText = server.getText();

        Thread.ofVirtual().start(() -> {
            try {
                SslContext serverCtx = SslConfig.createSslServerContext(MainApp.server, MainApp.serverPass);
                ChatServer chatServer = new ChatServer(Integer.parseInt(serverPortText), serverCtx, this);
                chatServer.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void initClient() {
        String clientPortText = port.getText();

        Thread.ofVirtual().start(() -> {
            try {
                SslContext clientCtx = SslConfig.createSslClientContext(MainApp.client, MainApp.clientPass);
                chatClient = new ChatClient(Integer.parseInt(clientPortText), clientCtx/*, this*/);
                chatClient.connect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void update(String msg) {
        Platform.runLater(() -> chatArea.appendText(msg + "\n"));
    }
}
