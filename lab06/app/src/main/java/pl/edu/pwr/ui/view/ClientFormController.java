package pl.edu.pwr.ui.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;
import pl.edu.pwr.client.ClientService;
import pl.edu.pwr.client.dto.ClientDto;
import pl.edu.pwr.client.dto.CreateClientRequest;
import pl.edu.pwr.client.dto.UpdateClientRequest;

import java.util.UUID;
import java.util.function.Consumer;

@Component
public class ClientFormController {
    @FXML
    private TextField mailTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField surnameTextField;

    private UUID currentClientId = null;
    private ClientDto clientToEdit = null;
    private final ClientService clientService;
    private Consumer<ClientDto> onCreationCallback;
    private Consumer<ClientDto> onEditCallback;

    public ClientFormController(ClientService clientService) {
        this.clientService = clientService;
    }

    public void prepareForAdd(Consumer<ClientDto> onCreationCallback) {
        this.currentClientId = null;
        this.clientToEdit = null;
        this.onCreationCallback = onCreationCallback;
    }

    public void prepareForEdit(ClientDto clientToEdit, Consumer<ClientDto> onEditCallback) {
        this.currentClientId = clientToEdit.id();
        this.clientToEdit = clientToEdit;
        this.onEditCallback = onEditCallback;
    }

    public void initialize() {
        if (currentClientId == null) {
            nameTextField.clear();
            surnameTextField.clear();
            mailTextField.clear();
        } else {
            nameTextField.setText(clientToEdit.name());
            surnameTextField.setText(clientToEdit.surname());
            mailTextField.setText(clientToEdit.email());
        }
    }

    @FXML
    void addClient(ActionEvent event) {
        if (currentClientId == null) {
            CreateClientRequest request = new CreateClientRequest(
                    nameTextField.getText(),
                    surnameTextField.getText(),
                    mailTextField.getText()
            );

            ClientDto client = clientService.createClient(request);

            onCreationCallback.accept(client);
        } else {
            UpdateClientRequest request = new UpdateClientRequest(
                    currentClientId,
                    nameTextField.getText(),
                    surnameTextField.getText(),
                    mailTextField.getText()
            );

            ClientDto client = clientService.updateClient(request);

            onEditCallback.accept(client);
        }
    }
}
