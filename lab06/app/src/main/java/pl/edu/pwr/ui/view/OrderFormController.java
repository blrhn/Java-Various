package pl.edu.pwr.ui.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import org.springframework.stereotype.Component;
import pl.edu.pwr.client.dto.ClientDto;
import pl.edu.pwr.clientorder.ClientOrderService;
import pl.edu.pwr.clientorder.dto.*;
import pl.edu.pwr.offer.dto.OfferDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

@Component
public class OrderFormController {
    @FXML
    private TextField addressTextField;

    @FXML
    private ComboBox<ClientDto> clientComboBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private CheckBox isPaidCheckBox;

    @FXML
    private ComboBox<OfferDto> offerComboBox;

    @FXML
    private ListView<OrderItemDto> offersListView;

    @FXML
    private Spinner<Integer> quantitySpinner;

    private UUID currentOrderId = null;
    private ClientOrderDto orderToEdit = null;
    private List<ClientDto> clients = new ArrayList<>();
    private List<OfferDto> offers = new ArrayList<>();
    private final ClientOrderService orderService;
    private Consumer<ClientOrderDto> onCreationCallback;
    private Consumer<ClientOrderDto> onEditCallback;

    public OrderFormController(ClientOrderService orderService) {
        this.orderService = orderService;
    }

    public void prepareState(List<ClientDto> clients, List<OfferDto> offers) {
        this.clients = clients;
        this.offers = offers;
    }

    public void prepareForAdd(Consumer<ClientOrderDto> onCreationCallback) {
        this.currentOrderId = null;
        this.orderToEdit = null;
        this.onCreationCallback = onCreationCallback;
    }

    public void prepareForEdit(ClientOrderDto orderToEdit, Consumer<ClientOrderDto> onEditCallback) {
        this.currentOrderId = orderToEdit.id();
        this.orderToEdit = orderToEdit;
        this.onEditCallback = onEditCallback;
    }

    public void initialize() {
        clientComboBox.setConverter(new StringConverter<>() {
            @Override public String toString(ClientDto client) {
                return client != null ? client.name() + " " + client.surname() : "";
            }
            @Override public ClientDto fromString(String s) { return null; }
        });

        offerComboBox.setConverter(new StringConverter<>() {
            @Override public String toString(OfferDto offer) {
                return offer != null ? offer.name() + " - " + offer.price() + " PLN" : "";
            }
            @Override public OfferDto fromString(String s) { return null; }
        });

        offersListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(OrderItemDto item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.offerName() + " x" + item.quantity());
                }
            }
        });

        offersListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                offerComboBox.getItems().stream()
                        .filter(o -> o.id().equals(newVal.offerId()))
                        .findFirst()
                        .ifPresent(o -> offerComboBox.getSelectionModel().select(o));

                quantitySpinner.getValueFactory().setValue(newVal.quantity());
            }
        });

        clientComboBox.getItems().setAll(clients);
        offerComboBox.getItems().setAll(offers);

        if (currentOrderId == null) {
            clientComboBox.getSelectionModel().clearSelection();
            offerComboBox.getSelectionModel().clearSelection();
            addressTextField.clear();
            datePicker.setValue(null);
            offersListView.getItems().clear();
            isPaidCheckBox.setSelected(false);
            clientComboBox.setDisable(false);
        } else {
            addressTextField.setText(orderToEdit.address());

            if (orderToEdit.deliveryDate() != null) {
                datePicker.setValue(orderToEdit.deliveryDate().toLocalDate());
            }

            offersListView.getItems().setAll(orderToEdit.items());
            isPaidCheckBox.setSelected(orderToEdit.isPaid());
            clientComboBox.setDisable(true);

            clients.stream()
                    .filter(c -> c.id().equals(orderToEdit.client().id()))
                    .findFirst()
                    .ifPresent(c -> clientComboBox.getSelectionModel().select(c));
        }

        if (quantitySpinner.getEditor() != null) {
            quantitySpinner.getEditor().clear();
        }

        quantitySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
    }

    @FXML
    void saveOffer(ActionEvent event) {
        OfferDto selectedOffer = offerComboBox.getValue();
        Integer quantity = quantitySpinner.getValue();

        if (selectedOffer != null && quantity != null) {
            OrderItemDto newItem = new OrderItemDto(
                    selectedOffer.id(), selectedOffer.name(), quantity);

            int selectedIndex = offersListView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                offersListView.getItems().set(selectedIndex, newItem);
                offersListView.getSelectionModel().clearSelection();
            } else {
                offersListView.getItems().add(newItem);
            }
        }
    }

    @FXML
    void saveOrder(ActionEvent event) {
        List<OrderItemRequest> itemRequests = offersListView.getItems().stream()
                .map(oi -> new OrderItemRequest(oi.offerId(), oi.quantity()))
                .toList();

        LocalDateTime deliveryDateTime = datePicker.getValue().atStartOfDay();

        if (currentOrderId == null) {
            CreateClientOrderRequest request = new CreateClientOrderRequest(
                    clientComboBox.getValue().id(),
                    itemRequests,
                    addressTextField.getText(),
                    deliveryDateTime,
                    isPaidCheckBox.isSelected()
            );

            ClientOrderDto order = orderService.createOrder(request);

            onCreationCallback.accept(order);
        } else {
            UpdateClientOrderRequest request = new UpdateClientOrderRequest(
                    currentOrderId,
                    itemRequests,
                    addressTextField.getText(),
                    deliveryDateTime,
                    isPaidCheckBox.isSelected()
            );

            ClientOrderDto order = orderService.updateOrder(request);

            onEditCallback.accept(order);
        }
    }
}
