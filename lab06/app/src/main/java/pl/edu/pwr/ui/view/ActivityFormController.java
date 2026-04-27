package pl.edu.pwr.ui.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import org.springframework.stereotype.Component;
import pl.edu.pwr.activity.ActivityService;
import pl.edu.pwr.activity.dto.ActivityDto;
import pl.edu.pwr.activity.dto.CreateActivityRequest;
import pl.edu.pwr.client.dto.ClientDto;
import pl.edu.pwr.clientorder.dto.ClientOrderDto;
import pl.edu.pwr.persistence.enums.ActivityType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

@Component
public class ActivityFormController {
    private final ActivityService activityService;
    @FXML
    private ComboBox<ActivityType> activityTypeComboBox;

    @FXML
    private TextField amountTextField;

    @FXML
    private ComboBox<ClientDto> clientComboBox;

    @FXML
    private ComboBox<ClientOrderDto> orderComboBox;

    private UUID currentActivityId = null;
    private List<ClientDto> clients = new ArrayList<>();
    private List<ClientOrderDto> orders = new ArrayList<>();
    private Consumer<ActivityDto> onCreationCallback;

    public ActivityFormController(ActivityService activityService) {
        this.activityService = activityService;
    }

    public void prepareState(List<ClientDto> clients, List<ClientOrderDto> orders) {
        //this.clients = clients;
        this.orders = orders;
    }

    public void prepareForAdd(Consumer<ActivityDto> onCreationCallback) {
        this.currentActivityId = null;
        this.onCreationCallback = onCreationCallback;
    }

    public void initialize() {
        activityTypeComboBox.getItems().addAll(ActivityType.values());
        //clientComboBox.getItems().addAll(clients);
        orderComboBox.getItems().addAll(orders);

        amountTextField.clear();

//        clientComboBox.setConverter(new StringConverter<>() {
//            @Override public String toString(ClientDto client) {
//                return client != null ? client.name() + " " + client.surname() : "";
//            }
//            @Override public ClientDto fromString(String s) { return null; }
//        });

        orderComboBox.setConverter(new StringConverter<>() {
            @Override public String toString(ClientOrderDto order) {
                return order != null ? order.id().toString() : "";
            }
            @Override public ClientOrderDto fromString(String s) { return null; }
        });
    }


    @FXML
    void saveActivity(ActionEvent event) {
        ClientOrderDto selectedOrder = orderComboBox.getValue();

        String amountText = amountTextField.getText();
        BigDecimal amount = (amountText == null || amountText.trim().isEmpty())
                ? BigDecimal.ZERO
                : new BigDecimal(amountText.trim());

        CreateActivityRequest request = new CreateActivityRequest(
                selectedOrder.client().id(),
                orderComboBox.getValue().id(),
                activityTypeComboBox.getValue(),
                amount
        );

        ActivityDto activity = activityService.createActivity(request);

        onCreationCallback.accept(activity);
    }
}
