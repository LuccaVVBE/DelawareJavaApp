package controllers;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import enums.EmployeeRole;
import interfaces.EmployeeInterface;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class GuiNavbarController {

    private GuiController guiController;
    private ToggleGroup toggleGroup;
    @FXML
    private ImageView logoImageView;
    @FXML
    private ToggleButton ordersButton;
    @FXML
    private ToggleButton clientsButton;
    @FXML
    private ToggleButton carriersButton;
    @FXML
    private ToggleButton employeesButton;
    @FXML
    private ToggleButton boxesButton;

    @FXML
    private Label usernameLabel;
    @FXML
    private Button logoutButton;
    @FXML
    private Label employeeRoleLabel;

    @FXML
    private Label welcomeLabel;
    @FXML
    private HBox welcomeBox;

    @FXML
    private void initialize() {
        ToggleGroup toggleGroup = new ToggleGroup();
        ordersButton.setToggleGroup(toggleGroup);
        clientsButton.setToggleGroup(toggleGroup);
        carriersButton.setToggleGroup(toggleGroup);
        employeesButton.setToggleGroup(toggleGroup);
        boxesButton.setToggleGroup(toggleGroup);

        var logoutIcon = GlyphsDude.createIcon(FontAwesomeIcons.SIGN_OUT, "1.5em");
        var orderIcon = GlyphsDude.createIcon(FontAwesomeIcons.LIST, "1.5em");
        var clientIcon = GlyphsDude.createIcon(FontAwesomeIcons.USERS, "1.5em");
        var carrierIcon = GlyphsDude.createIcon(FontAwesomeIcons.TRUCK, "1.5em");
        var employeeIcon = GlyphsDude.createIcon(FontAwesomeIcons.USER, "1.5em");
        var boxIcon = GlyphsDude.createIcon(FontAwesomeIcons.DROPBOX, "1.5em");

        logoutButton.setGraphic(logoutIcon);
        logoutButton.setAlignment(Pos.BASELINE_LEFT);
        ordersButton.setGraphic(orderIcon);
        ordersButton.setAlignment(Pos.BASELINE_LEFT);
        clientsButton.setGraphic(clientIcon);
        clientsButton.setAlignment(Pos.BASELINE_LEFT);
        carriersButton.setGraphic(carrierIcon);
        carriersButton.setAlignment(Pos.BASELINE_LEFT);
        employeesButton.setGraphic(employeeIcon);
        employeesButton.setAlignment(Pos.BASELINE_LEFT);
        boxesButton.setGraphic(boxIcon);
        boxesButton.setAlignment(Pos.BASELINE_LEFT);


        // make label text bold
        usernameLabel.setStyle("-fx-font-weight: bold");
        usernameLabel.setFont(Font.font(16));
    }

    public void initializeController() {
        // we need this because the navbar controller is initialized before the gui controller
        EmployeeInterface employee = guiController.getEmployeeController().getEmployee();
        setSelected(getSelected());

        var adminIcon = GlyphsDude.createIcon(FontAwesomeIcons.USER_PLUS, "1.5em");
        var employeeIcon = GlyphsDude.createIcon(FontAwesomeIcons.USER, "1.5em");
        employeeRoleLabel.setText((employee.getRole() == EmployeeRole.ADMIN ? "Admin" : "Employee") + " View");
        employeeRoleLabel.setGraphic(employee.getRole() == EmployeeRole.ADMIN ? adminIcon : employeeIcon);

        // Set the username label and make the text bold
        var fullname = employee.getFirstName() + " " + employee.getLastName() + "!";
        if (fullname.length() > 14) {
            VBox vbox = new VBox();
            welcomeBox.getChildren().clear();
            vbox.getChildren().addAll(welcomeLabel, usernameLabel);
            welcomeBox.getChildren().addAll(vbox);
        }
        usernameLabel.setText(fullname);
        usernameLabel.setStyle("-fx-font-weight: bold");
        usernameLabel.setFont(Font.font(16));

        carriersButton.setVisible(employee.getRole() == EmployeeRole.ADMIN);
        carriersButton.setManaged(employee.getRole() == EmployeeRole.ADMIN);
        employeesButton.setVisible(employee.getRole() == EmployeeRole.ADMIN);
        employeesButton.setManaged(employee.getRole() == EmployeeRole.ADMIN);

    }

    public void setGuiController(GuiController guiController) {
        this.guiController = guiController;
    }

    @FXML
    private void handleOrdersButton() {
        if (!guiController.getCurrentScene().equals("orders")) {
            guiController.showOrderOverview();
        }
    }

    @FXML
    private void handleClientsButton() {
        if (!guiController.getCurrentScene().equals("clients")) {
            guiController.showClientOverview();
        }
    }

    @FXML
    private void handleCarriersButton() {
        if (!guiController.getCurrentScene().equals("carriers")) {
            guiController.showCarriersOverview();
        }
    }

    @FXML
    private void handleEmployeesButton() {
        if (!guiController.getCurrentScene().equals("employees")) {
            guiController.showEmployeesOverview();
        }
    }

    @FXML
    private void handleLogoutButton() {
        // Navigate to Login Page
        guiController.logout();
    }

    public void hideNavbar() {
        logoImageView.setVisible(false);
        ordersButton.setVisible(false);
        clientsButton.setVisible(false);
        carriersButton.setVisible(false);
        usernameLabel.setVisible(false);
        logoutButton.setVisible(false);
        welcomeLabel.setVisible(false);
    }

    public void handleBoxesButton() {
        //navigate to Boxes page
        guiController.showBoxOverview();
    }

    private ToggleButton getSelected() {
        switch (guiController.getCurrentScene()) {
            case "orders":
                return ordersButton;
            case "clients":
                return clientsButton;
            case "carriers":
                return carriersButton;
            case "employees":
                return employeesButton;
            case "boxes":
                return boxesButton;
            default:
                return null;
        }
    }

    private void setSelected(ToggleButton button) {
        ordersButton.getStyleClass().remove("selected-button");
        clientsButton.getStyleClass().remove("selected-button");
        carriersButton.getStyleClass().remove("selected-button");
        employeesButton.getStyleClass().remove("selected-button");
        boxesButton.getStyleClass().remove("selected-button");

        button.getStyleClass().add("selected-button");
    }
}
