package pl.edu.pwr.ui.view;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import pl.edu.pwr.activity.ActivityService;
import pl.edu.pwr.activity.dto.ActivityDto;
import pl.edu.pwr.client.ClientService;
import pl.edu.pwr.client.dto.ClientDto;
import pl.edu.pwr.clientorder.ClientOrderService;
import pl.edu.pwr.clientorder.dto.ClientOrderDto;
import pl.edu.pwr.offer.OfferService;
import pl.edu.pwr.offer.dto.OfferDto;
import pl.edu.pwr.stats.StatsService;
import pl.edu.pwr.time.SimulationService;
import pl.edu.pwr.ui.config.FxmlView;
import pl.edu.pwr.ui.config.StageManager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class ViewController {
    private final OfferFormController offerFormController;
    private final OrderFormController orderFormController;
    private final ActivityService activityService;
    private final ActivityFormController activityFormController;
    @FXML
    private TableView<ActivityDto> activitiesTable;

    @FXML
    private TableView<ClientDto> clientsTable;

    @FXML
    private TableView<OfferDto> offersTable;

    @FXML
    private TableView<ClientOrderDto> ordersTable;

    @FXML
    private TextField incomeTextField;

    @FXML
    private TextArea monitTextArea;

    private final StageManager stageManager;
    private final ClientFormController clientFormController;
    private final ClientService clientService;
    private final OfferService offerService;
    private final ClientOrderService orderService;
    private final SimulationService simulationService;
    private final StatsService statsService;

    @Lazy
    public ViewController(
            StageManager stageManager,
            ClientFormController clientFormController,
            ClientService clientService,
            OfferService offerService, OfferFormController offerFormController,
            ClientOrderService orderService,
            OrderFormController orderFormController,
            ActivityService activityService,
            ActivityFormController activityFormController,
            SimulationService simulationService,
            StatsService statsService) {
        this.stageManager = stageManager;
        this.clientFormController = clientFormController;
        this.clientService = clientService;
        this.offerService = offerService;
        this.offerFormController = offerFormController;
        this.orderService = orderService;
        this.orderFormController = orderFormController;
        this.activityService = activityService;
        this.activityFormController = activityFormController;
        this.simulationService = simulationService;
        this.statsService = statsService;
    }

    public void initialize() {
        setupClientsTable();
        loadClientsData();
        setupOffersTable();
        loadOffersData();
        setupOrdersTable();
        loadOrdersData();
        setupActivitiesTable();
        loadActivitiesData();

        simulationService.setUiLogCallback(message ->
                Platform.runLater(() -> monitTextArea.appendText(message))
        );

        refreshStatistics();
    }

    private void setupClientsTable() {
        TableColumn<ClientDto, String> nameCol = new TableColumn<>("Imię");
        nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().name()));

        TableColumn<ClientDto, String> surnameCol = new TableColumn<>("Nazwisko");
        surnameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().surname()));

        TableColumn<ClientDto, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().email()));

        clientsTable.getColumns().setAll(List.of(nameCol, surnameCol, emailCol));
    }

    private void loadClientsData() {
        List<ClientDto> clients = clientService.getAllClients();

        clientsTable.getItems().setAll(clients);
    }

    private void setupOffersTable() {
        TableColumn<OfferDto, String> nameCol = new TableColumn<>("Nazwa");
        nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().name()));

        TableColumn<OfferDto, String> priceCol = new TableColumn<>("Cena");
        priceCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().price().toString()));

        TableColumn<OfferDto, String> mealTypeCol = new TableColumn<>("Typ posiłku");
        mealTypeCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().mealType().getMealType()));

        offersTable.getColumns().setAll(List.of(nameCol, priceCol, mealTypeCol));
    }

    private void loadOffersData() {
        List<OfferDto> offers = offerService.getAllActiveOffers();

        offersTable.getItems().setAll(offers);
    }

    private void  setupOrdersTable() {
        TableColumn<ClientOrderDto, String> clientCol = new TableColumn<>("Klient");
        clientCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().client().name() + " " + cellData.getValue().client().surname()));

        TableColumn<ClientOrderDto, String> addressCol = new TableColumn<>("Adres");
        addressCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().address()));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        TableColumn<ClientOrderDto, String> dateCol = new TableColumn<>("Dostawa");
        dateCol.setCellValueFactory(cellData -> {
            LocalDateTime date = cellData.getValue().deliveryDate();
            return new SimpleStringProperty(date.format(formatter));
        });

        TableColumn<ClientOrderDto, String> totalCol = new TableColumn<>("Cena całkowita");
        totalCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().totalPrice().toString()));

        TableColumn<ClientOrderDto, String> paidCol = new TableColumn<>("Opłacone");
        paidCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().isPaid() ? "Tak" : "Nie"));

        ordersTable.getColumns().setAll(List.of(clientCol, addressCol, dateCol, totalCol, paidCol));
    }

    private void loadOrdersData() {
        List<ClientOrderDto> orders = orderService.getAllOrders();

        ordersTable.getItems().setAll(orders);
    }

    private void setupActivitiesTable() {
        TableColumn<ActivityDto, String> clientIdCol = new TableColumn<>("Id klienta");
        clientIdCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().clientId().toString()));

        TableColumn<ActivityDto, String> orderIdCol = new TableColumn<>("Id zamowienia");
        orderIdCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().orderId().toString()));

        TableColumn<ActivityDto, String> activityTypeCol = new TableColumn<>("Typ aktywności");
        activityTypeCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().type().getActivityType()));

        TableColumn<ActivityDto, String> amountCol = new TableColumn<>("Wartość");
        amountCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().amount().toString()));

        activitiesTable.getColumns().setAll(List.of(clientIdCol, orderIdCol, activityTypeCol, amountCol));
    }

    private void loadActivitiesData() {
        List<ActivityDto> activities = activityService.getAllActivities();

        activitiesTable.getItems().setAll(activities);
    }

    @FXML
    void addActivity(ActionEvent event) {
        List<ClientDto> activeClients = clientService.getClientsWithOrders();
        List<ClientOrderDto> allOrders = orderService.getAllOrders();

        activityFormController.prepareState(activeClients, allOrders);
        activityFormController.prepareForAdd(na -> activitiesTable.getItems().add(na));

        stageManager.showModal(FxmlView.ACTIVITY_FORM);
    }

    @FXML
    void addClient(ActionEvent event) {
        clientFormController.prepareForAdd(nc -> clientsTable.getItems().add(nc));

        stageManager.showModal(FxmlView.CLIENT_FORM);
    }

    @FXML
    void addOffer(ActionEvent event) {
        offerFormController.prepareForAdd(no -> offersTable.getItems().add(no));

        stageManager.showModal(FxmlView.OFFER_FORM);
    }

    @FXML
    void addOrder(ActionEvent event) {
        List<ClientDto> currentClients = clientService.getAllClients();
        List<OfferDto> currentOffers = offerService.getAllActiveOffers();

        orderFormController.prepareState(currentClients, currentOffers);
        orderFormController.prepareForAdd(no -> {
            ordersTable.getItems().add(no);
            loadActivitiesData();
        });

        stageManager.showModal(FxmlView.ORDER_FORM);
    }

    @FXML
    void deleteClient(ActionEvent event) {
        ClientDto selectedClient = clientsTable.getSelectionModel().getSelectedItem();

        if (selectedClient != null) {
            clientService.deleteClient(selectedClient.id());
            clientsTable.getItems().remove(selectedClient);
        }
    }

    @FXML
    void deleteOffer(ActionEvent event) {
        OfferDto selectedOffer = offersTable.getSelectionModel().getSelectedItem();

        if (selectedOffer != null) {
            offerService.deleteOffer(selectedOffer.id());
            offersTable.getItems().remove(selectedOffer);
        }
    }

    @FXML
    void deleteOrder(ActionEvent event) {
        ClientOrderDto selectedOrder = ordersTable.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            orderService.deleteOrder(selectedOrder.id());
            ordersTable.getItems().remove(selectedOrder);

            loadActivitiesData();
        }
    }

    @FXML
    void editClient(ActionEvent event) {
        ClientDto selectedClient = clientsTable.getSelectionModel().getSelectedItem();
        int selectedIndex = clientsTable.getSelectionModel().getSelectedIndex();

        if  (selectedClient != null) {
            clientFormController.prepareForEdit(selectedClient, uc -> clientsTable.getItems().set(selectedIndex, uc));
        }

        stageManager.showModal(FxmlView.CLIENT_FORM);
    }

    @FXML
    void editOffer(ActionEvent event) {
        OfferDto selectedOffer = offersTable.getSelectionModel().getSelectedItem();
        int selectedIndex = offersTable.getSelectionModel().getSelectedIndex();

        if  (selectedOffer != null) {
            offerFormController.prepareForEdit(selectedOffer, uo -> offersTable.getItems().set(selectedIndex, uo));
        }

        stageManager.showModal(FxmlView.OFFER_FORM);
    }

    @FXML
    void editOrder(ActionEvent event) {
        ClientOrderDto selectedOrder = ordersTable.getSelectionModel().getSelectedItem();
        int selectedIndex = ordersTable.getSelectionModel().getSelectedIndex();

        if (selectedOrder != null) {
            orderFormController.prepareState(clientService.getAllClients(), offerService.getAllActiveOffers());

            orderFormController.prepareForEdit(selectedOrder, uo -> {
                ordersTable.getItems().set(selectedIndex, uo);
                loadActivitiesData();
            });
        }

        stageManager.showModal(FxmlView.ORDER_FORM);
    }

    @FXML
    void addOneDay(ActionEvent event) {
        simulationService.advanceTime(Duration.ofDays(1));
        refreshStatistics();

    }

    @FXML
    void addOneHour(ActionEvent event) {
        simulationService.advanceTime(Duration.ofHours(1));
        refreshStatistics();
    }


    @FXML
    void addOneMinute(ActionEvent event) {
        simulationService.advanceTime(Duration.ofMinutes(1));
        refreshStatistics();
    }

    private void refreshStatistics() {
        incomeTextField.setText(statsService.calculateTotalIncome().toString() + " PLN");

        loadOrdersData();
        loadActivitiesData();
    }
}
