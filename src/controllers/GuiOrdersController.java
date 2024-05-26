package controllers;


import enums.OrderStatus;
import interfaces.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.util.StringConverter;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class GuiOrdersController {

    private final ObservableList<OrderInterface> ordersData = FXCollections.observableArrayList();
    private final ObservableList<String> statusFilters = FXCollections.observableArrayList();
    private final ObservableList<String> companyFilters = FXCollections.observableArrayList();
    private final BooleanProperty isStatusChanged = new SimpleBooleanProperty(false);
    private final BooleanProperty isCarrierChanged = new SimpleBooleanProperty(false);
    private final ObservableList<OrderStatus> orderStatuses = FXCollections.observableArrayList(OrderStatus.values());
    private final DecimalFormat df = new DecimalFormat("#.##");
    private GuiController guiController;
    @FXML
    private GuiNavbarController navbarController;
    @FXML
    private TableView<OrderInterface> ordersTable;
    @FXML
    private TableColumn<OrderInterface, String> orderIdColumn;
    @FXML
    private TableColumn<OrderInterface, String> orderCompanyNameColumn;
    @FXML
    private TableColumn<OrderInterface, String> orderDateColumn;
    @FXML
    private TableColumn<OrderInterface, String> orderTrackTraceStatusColumn;
    @FXML
    private ChoiceBox<String> ordersChoiceBox;
    @FXML
    private Label lblOpenOrders;
    @FXML
    private VBox orderDetailsBox;
    // order details part
    private String initialCarrier;
    private String initialStatus;
    @FXML
    private Label instructionLabel;
    @FXML
    private Label orderIdLabel;
    @FXML
    private Label companyNameLabel;
    @FXML
    private Label companyEmailLabel;
    @FXML
    private Label dateLabel;

    @FXML
    private Label packingTypeLabel;
    @FXML
    private ChoiceBox<CarrierInterface> carrierChoiceBox;
    @FXML
    private ChoiceBox<String> companyChoiceBox;
    @FXML
    private Label streetLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label numberLabel;
    @FXML
    private Label zipcodeLabel;
    @FXML
    private Label countryLabel;
    @FXML
    private Label totalPriceLabel;
    @FXML
    private TableView<OrderItemInterface> orderItemsTableView;
    @FXML
    private TableColumn<OrderItemInterface, String> itemNameColumn;
    @FXML
    private TableColumn<OrderItemInterface, Integer> itemQuantityColumn;
    @FXML
    private TableColumn<OrderItemInterface, String> itemPriceColumn;
    @FXML
    private TableColumn<OrderItemInterface, String> itemSubtotalColumn;
    @FXML
    private Button saveButton;
    @FXML
    private Label tntNumber;
    @FXML
    private Label tntVerificationCode;
    @FXML
    private ChoiceBox<OrderStatus> orderStatusChoiceBox;

    @FXML
    private StackPane rightPane;
    @FXML
    private VBox ordersBox;

    public void setGuiController(GuiController guiController) {
        this.guiController = guiController;
        navbarController.setGuiController(guiController);
    }

    public void initializeController() {// Populate the ordersData with your actual data

        var activeCarriers = guiController.getEmployeeController().getCarriers().stream().filter(CarrierInterface::isActive).toList();
        carrierChoiceBox.setItems(FXCollections.observableArrayList(activeCarriers));
        carrierChoiceBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(CarrierInterface carrier) {
                if (carrier == null) {
                    return "No Carrier";
                }
                return carrier.getName();
            }

            @Override
            public CarrierInterface fromString(String string) {
                return null;
            }
        });
        orderStatusChoiceBox.setItems(orderStatuses);
        companyFilters.add("All");
        companyFilters.addAll(guiController.getEmployeeController().getCustomers(guiController.getEmployeeController().getEmployee().getCompany().getId()).stream().map(CustomerInterface::getName).toList());
        companyChoiceBox.setItems(companyFilters);
        companyChoiceBox.getSelectionModel().select("All");

        companyChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                updateOrders();
            }
        });

        // Populate filter items and setting standard on Received
        statusFilters.add("All");
        statusFilters.addAll(Stream.of(OrderStatus.values()).map(OrderStatus::getDisplayName).toList());
        ordersChoiceBox.setItems(statusFilters);
        ordersChoiceBox.getSelectionModel().select(OrderStatus.RECEIVED.getDisplayName());
//        ordersChoiceBox.setValue(OrderStatus.RECEIVED.getDisplayName());


        // Initialize the table columns and bind their properties
        orderIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        orderCompanyNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomerCompanyInterface().getName()));
        orderDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOrderDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))));
        orderTrackTraceStatusColumn.setCellValueFactory(cellData -> {
            var tnt = cellData.getValue().getTrackAndTrace();
            if (tnt == null) {
                return new SimpleStringProperty("No track and trace");
            }
            return new SimpleStringProperty(tnt.getStatus().getDisplayName());
        });

        // Set the table's items property to the ordersData
        ordersTable.setItems(ordersData);

        ordersTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showOrderDetails(newValue);
            }
        });

        ordersChoiceBox.getSelectionModel().selectedItemProperty().addListener(observable -> updateOrders());

        //Initial update
        updateOrders();
        navbarController.initializeController();

        var screenBounds = Screen.getPrimary().getBounds();
//        ordersBox.setPrefWidth(screenBounds.getWidth() * 0.4);
        rightPane.setMaxWidth(screenBounds.getWidth() * 0.4);
        rightPane.setPrefWidth(screenBounds.getWidth() * 0.4);
        var styleClass = screenBounds.getWidth() > 1600 ? "order-details-large" : "order-details-small";
        rightPane.getStyleClass().add(styleClass);

    }

    private void showOrderDetails(OrderInterface order) {
        if (instructionLabel.isVisible()) {
            instructionLabel.setVisible(false);
            orderDetailsBox.setVisible(true);
        }
        guiController.getEmployeeController().setCurrentWorkingOrder(order);
        updateOrderDetail();
        isCarrierChanged.set(false);
        isStatusChanged.set(false);
    }

    protected void updateOrderDetail() {
        OrderInterface currentWorkingOrder = guiController.getEmployeeController().getCurrentWorkingOrder();
        if (currentWorkingOrder != null) {
            orderIdLabel.setText(currentWorkingOrder.getId());
            carrierChoiceBox.setValue(currentWorkingOrder.getTrackAndTrace().getCarrier() != null ? currentWorkingOrder.getTrackAndTrace().getCarrier() : null);

            companyNameLabel.setText(currentWorkingOrder.getCustomerCompanyInterface().getName());
            companyEmailLabel.setText(currentWorkingOrder.getCustomerCompanyInterface().getName() + "@gmail.com");
            dateLabel.setText(currentWorkingOrder.getOrderDate().format(DateTimeFormatter.ofPattern("dd MM yyyy, HH:mm")));
            packingTypeLabel.setText(currentWorkingOrder.getPackingType().getName());

            AddressInterface address = currentWorkingOrder.getAddressInterface();
            streetLabel.setText(address.getStreet());
            cityLabel.setText(address.getCity());
            numberLabel.setText(address.getNumber());
            zipcodeLabel.setText(address.getZipCode());
            countryLabel.setText(address.getCountry());

            totalPriceLabel.setText(guiController.getEmployeeController().calculateTotalOrderPrice(currentWorkingOrder));

            initialCarrier = carrierChoiceBox.getValue() != null ? carrierChoiceBox.getValue().getName() : "No Carrier";

            // set the tnt number and verification code
            tntNumber.setText(currentWorkingOrder.getTrackAndTrace().getNumber());
            tntVerificationCode.setText(currentWorkingOrder.getTrackAndTrace().getVerificationCode());

            orderStatusChoiceBox.setValue(currentWorkingOrder.getTrackAndTrace().getStatus());
            initialStatus = orderStatusChoiceBox.getValue().toString();


            // set the save button's text and action based on the order's status (if received, process order, else save changes)
            if (currentWorkingOrder.getTrackAndTrace().getStatus().equals(OrderStatus.RECEIVED)) {
                saveButton.setText("Process Order");
                saveButton.setOnAction(e -> processOrder());
                orderStatusChoiceBox.setDisable(true);
            } else {
                saveButton.setText("Save Changes");
                saveButton.disableProperty().bind(isStatusChanged.or(isCarrierChanged).not());
                saveButton.setOnAction(e -> saveOrder());
                orderStatusChoiceBox.setDisable(false);
            }

            // update the isChanged properties to be bound to the save button's disable property
            carrierChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                String carrierName = newVal != null ? newVal.getName() : "No Carrier";
                isCarrierChanged.set(!carrierName.equals(initialCarrier));
            });
            orderStatusChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> isStatusChanged.set(!newVal.getDisplayName().equals(initialStatus)));
            //

            // Populate the order items table
            ObservableList<OrderItemInterface> orderItems = FXCollections.observableArrayList(currentWorkingOrder.getOrderItems());

            itemNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProduct().getName("en")));
            itemQuantityColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getQuantity()).asObject());
            itemPriceColumn.setCellValueFactory(data -> new SimpleStringProperty(df.format(data.getValue().getProduct().getPrice("EUR"))));
            itemSubtotalColumn.setCellValueFactory(data -> new SimpleStringProperty(df.format(data.getValue().getProduct().getPrice("EUR") * data.getValue().getQuantity())));

            orderItemsTableView.setItems(orderItems);
        }
    }

    protected void updateOrders() {
        String statusFilter = ordersChoiceBox.getSelectionModel().getSelectedItem();
        String companyFilter = companyChoiceBox.getSelectionModel().getSelectedItem();

        Set<OrderInterface> orders = guiController.getEmployeeController().getOrders();
        List<OrderInterface> ordersFiltered;
        if (companyFilter.equals("All")) {
            ordersFiltered = orders.stream().toList();
        } else {
            ordersFiltered = orders.stream().filter(o -> o.getCustomerCompanyInterface().getName().equals(companyFilter)).toList();
        }
        if (!statusFilter.equals("All")) {
            ordersFiltered = ordersFiltered.stream().filter(order -> order.getTrackAndTrace().getStatus().getDisplayName().contentEquals(statusFilter)).toList();
        }
        ordersData.setAll(ordersFiltered);
        lblOpenOrders.setText("Open orders: " + ordersFiltered.size());
    }

    private void saveOrder() {
        if (!carrierChoiceBox.getValue().getName().equals(initialCarrier) || !orderStatusChoiceBox.getValue().toString().equals(initialStatus)) {
            OrderInterface currentWorkingOrder = guiController.employeeController.getCurrentWorkingOrder();

            if (!currentWorkingOrder.getTrackAndTrace().getStatus().equals(OrderStatus.PROCESSED)) {
                guiController.showAlert("Error", "Order can only be updated if the status is Received", Alert.AlertType.ERROR);
                return;
            }

            guiController.getEmployeeController().saveOrder(currentWorkingOrder, carrierChoiceBox.getValue(), orderStatusChoiceBox.getValue());

            updateOrderDetail();
            updateOrders();
            guiController.showAlert("Success", "Order has been updated", Alert.AlertType.INFORMATION);
        } else {
            guiController.showAlert("Error", "No changes have been made", Alert.AlertType.ERROR);
        }
    }

    private void processOrder() {
        OrderInterface currentWorkingOrder = guiController.getEmployeeController().getCurrentWorkingOrder();
        if (guiController.getEmployeeController().orderIsReceived(currentWorkingOrder)) {
            if (carrierChoiceBox.getValue() == null) {
                guiController.showAlert("Error", "Please select a carrier", Alert.AlertType.ERROR);
                return;
            }

            guiController.getEmployeeController().processOrder(currentWorkingOrder, carrierChoiceBox.getValue());

            updateOrderDetail();
            updateOrders();
            guiController.showAlert("Success", "Order has been processed", Alert.AlertType.INFORMATION);
        } else {
            guiController.showAlert("Error", "Order has already been processed", Alert.AlertType.ERROR);
        }
    }
}
