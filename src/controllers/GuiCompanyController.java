package controllers;

import enums.OrderStatus;
import interfaces.AddressInterface;
import interfaces.CustomerInterface;
import interfaces.EmployeeInterface;
import interfaces.OrderInterface;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.util.StringConverter;

import java.util.Set;


public class GuiCompanyController {

    private final ObservableList<CustomerInterface> companiesData = FXCollections.observableArrayList();
    private final ObservableList<EmployeeInterface> companyEmployeeData = FXCollections.observableArrayList();
    private final ObservableList<OrderInterface> companyOrders = FXCollections.observableArrayList();
    private GuiController guiController;
    @FXML
    private GuiNavbarController navbarController;
    private Set<CustomerInterface> customers;

    //FXML Table with all companies
    @FXML
    private TableView<CustomerInterface> companyTable;
    @FXML
    private TableColumn<CustomerInterface, String> CompanyIdColumn;
    @FXML
    private TableColumn<CustomerInterface, String> CompanyNameColumn;
    @FXML
    private TableColumn<CustomerInterface, String> CompanyOpenOrdersColumn;
    @FXML
    private TextField txtCompanyName;

    //FXML company info
    @FXML
    private Label lblCompanyName;
    @FXML
    private Label lblCompanyPhone;
    @FXML
    private ChoiceBox<AddressInterface> addressNameChoiceBox;
    @FXML
    private Label lblStreet;
    @FXML
    private Label lblCity;
    @FXML
    private Label lblCountry;
    @FXML
    private TableView<EmployeeInterface> EmployeeTable;
    @FXML
    private TableColumn<EmployeeInterface, String> EmployeeFirstname;
    @FXML
    private TableColumn<EmployeeInterface, String> EmployeeLastname;
    @FXML
    private TableColumn<EmployeeInterface, String> EmployeeEmail;
    @FXML
    private ImageView imgLogo;
    @FXML
    private TableView<OrderInterface> companyOrdersTable;
    @FXML
    private TableColumn<OrderInterface, String> CompanyOrderId;
    @FXML
    private TableColumn<OrderInterface, String> CompanyOrderDate;
    @FXML
    private TableColumn<OrderInterface, String> CompanyOrderStatus;
    @FXML
    private VBox companyInfoBox;
    @FXML
    private StackPane clientDetailsPane;
    @FXML
    private VBox leftPane;
    @FXML
    private Label instructionLabel;

    private Set<AddressInterface> addresses;

    public void setGuiController(GuiController guiController) {
        this.guiController = guiController;
        navbarController.setGuiController(guiController);
    }

    public void initializeController() {
        customers = guiController.getEmployeeController().getCustomers(guiController.employeeController.getEmployeeCompany().getId());

        // Initialize the table columns and bind their properties
        CompanyIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        CompanyNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        CompanyOpenOrdersColumn.setCellValueFactory(cellData -> {
            var ordersToUs = cellData.getValue().getPlacedOrders().stream().filter(order -> order.getCompanyInterface().getId().equals(guiController.getEmployeeController().getEmployeeCompany().getId()));
            return new SimpleStringProperty(ordersToUs.filter(order -> order.getTrackAndTrace().getStatus() != OrderStatus.DELIVERED).count() + " orders");
        });

        // Set the table's items property to the companyData
        companyTable.setItems(companiesData);

        companiesData.addAll(customers);

        companyTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectCompany(newValue);
            }
        });

        //--------------------------------------//

        companyOrdersTable.setItems(companyOrders);
        EmployeeTable.setItems(companyEmployeeData);

        companyOrdersTable.getSortOrder().add(CompanyOrderId);

        CompanyOrderId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        CompanyOrderDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOrderDate().toString()));
        CompanyOrderStatus.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTrackAndTrace().getStatus().getDisplayName()));

        EmployeeFirstname.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()));
        EmployeeLastname.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));
        EmployeeEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        addressNameChoiceBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(AddressInterface addressInterface) {
                return addressInterface != null ? addressInterface.getName() : "No Address";
            }

            @Override
            public AddressInterface fromString(String string) {
                return null;
            }
        });
        addressNameChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectAddress(newValue);
            }
        });
        navbarController.initializeController();

        var screenBounds = Screen.getPrimary().getBounds();
        var sb = Screen.getPrimary().getVisualBounds();
        var styleClass = screenBounds.getWidth() > 1600 ? "client-details-large" : "client-details-small";
        clientDetailsPane.getStyleClass().add(styleClass);
        clientDetailsPane.setMaxWidth(screenBounds.getWidth() * 0.4);
        leftPane.setMaxWidth(screenBounds.getWidth() * 0.4);
        leftPane.setPrefWidth(screenBounds.getWidth() * 0.4);
    }

    @FXML
    private void searchCompany() {
        String searchValue = txtCompanyName.getText().toLowerCase();
        companiesData.setAll(customers.stream().filter(company -> company.getName().toLowerCase().contains(searchValue)).toList());
    }

    private void selectCompany(CustomerInterface selectedCompany) {
        companyInfoBox.setVisible(true);
        companyOrdersTable.setVisible(true);
        instructionLabel.setVisible(false);

        companyEmployeeData.setAll(selectedCompany.getEmployeeInterfaces());
        var allOrders = selectedCompany.getPlacedOrders();
        var ordersPlacedToUs = allOrders.stream().filter(order -> order.getCompanyInterface().getId().equals(guiController.employeeController.getEmployeeCompany().getId())).toList();
        companyOrders.setAll(ordersPlacedToUs);
        companyOrdersTable.sort();
        lblCompanyName.setText(selectedCompany.getName());

        imgLogo.setImage(new Image(selectedCompany.getImage()));
        lblCompanyPhone.setText(selectedCompany.getPhone());

        addresses = selectedCompany.getAddressInterfaces();
        addressNameChoiceBox.setItems(FXCollections.observableArrayList(addresses));
        AddressInterface firstAddress = addresses.stream().findFirst().orElse(null);
        selectAddress(firstAddress);
    }

    public void selectAddress(AddressInterface addressInterface) {
        try {
            assert addressInterface != null;
            addressNameChoiceBox.setValue(addressInterface);
            lblStreet.setText(addressInterface.getStreet() + " " + addressInterface.getNumber());
            lblCity.setText(addressInterface.getZipCode() + " " + addressInterface.getCity());
            lblCountry.setText(addressInterface.getCountry());
        } catch (NullPointerException npe) {
            //INFO: heeft geen handling nodig, wordt opgevangen in fxml
        }
    }


}
