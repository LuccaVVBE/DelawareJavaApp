package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class GuiController {

    @Getter(AccessLevel.PROTECTED)
    protected LoginController loginController;

    @Getter(AccessLevel.PROTECTED)
    @Setter(AccessLevel.PROTECTED)
    protected EmployeeController employeeController;
    private Stage rootStage;
    private String currentScene;

    public void start(Stage stage) {
        this.rootStage = stage;
        this.loginController = new LoginController();
        rootStage.setMinWidth(1450);
        rootStage.setMinHeight(720);
        rootStage.setMaximized(true);
        rootStage.setScene(new Scene(new Pane()));
        showLogin();
        rootStage.show();
    }

    public void showLogin() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Pane loginPane = fxmlLoader.load();

            GuiLoginController guiLoginController = fxmlLoader.getController();
            setCurrentScene("login");
            guiLoginController.setGuiController(this);
            rootStage.getScene().setRoot(loginPane);
            rootStage.setTitle("Login to Portal");
        } catch (Exception e) {
            showError(e);
        }
    }

    public void showOrderOverview() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/orders.fxml"));
            Pane orderPane = fxmlLoader.load();

            GuiOrdersController guiOrderController = fxmlLoader.getController();
            setCurrentScene("orders");
            guiOrderController.setGuiController(this);
            guiOrderController.initializeController();

            rootStage.getScene().setRoot(orderPane);
            rootStage.setTitle("Orders");
        } catch (Exception e) {
            showError(e);
        }
    }

    public void showClientOverview() {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/clients.fxml"));
            Pane clientPane = fxmlLoader.load();

            GuiCompanyController guiCompanyController = fxmlLoader.getController();
            setCurrentScene("clients");
            guiCompanyController.setGuiController(this);
            guiCompanyController.initializeController();

            rootStage.getScene().setRoot(clientPane);

            rootStage.setTitle("Clients");
        } catch (Exception e) {
            showError(e);
        }

    }

    public void showCarriersOverview() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/carriers.fxml"));
            Pane carriersPane = fxmlLoader.load();

            GuiCarriersController guiCarriersController = fxmlLoader.getController();
            setCurrentScene("carriers");
            guiCarriersController.setGuiController(this);
            guiCarriersController.initializeController();

            rootStage.getScene().setRoot(carriersPane);
            rootStage.setTitle("Carriers");
        } catch (Exception e) {
            showError(e);
        }
    }

    public void showEmployeesOverview() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/employees.fxml"));
            Pane employeesPane = fxmlLoader.load();

            GuiEmployeesController guiEmployeesController = fxmlLoader.getController();
            setCurrentScene("employees");
            guiEmployeesController.setGuiController(this);
            guiEmployeesController.initializeController();

            rootStage.getScene().setRoot(employeesPane);
            rootStage.setTitle("Employees");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showBoxOverview() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/boxes.fxml"));
            Pane boxesPane = fxmlLoader.load();

            GuiBoxController guiBoxController = fxmlLoader.getController();
            setCurrentScene("boxes");
            guiBoxController.setGuiController(this);
            guiBoxController.initializeController();

            rootStage.getScene().setRoot(boxesPane);
            rootStage.setTitle("Boxes");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void logout() {
        setEmployeeController(null);
        showLogin();
    }

    public void showError(Exception e) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/errors.fxml"));
            Pane errorPane = fxmlLoader.load();

            GuiErrorController guiErrorController = fxmlLoader.getController();
            guiErrorController.setGuiController(this);
            guiErrorController.initializeController(e);
            rootStage.getScene().setRoot(errorPane);
            rootStage.setTitle("Error");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public String getCurrentScene() {
        return currentScene;
    }

    public void setCurrentScene(String currentScene) {
        this.currentScene = currentScene;
    }

    public void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
