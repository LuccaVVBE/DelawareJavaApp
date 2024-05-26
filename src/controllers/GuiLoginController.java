package controllers;


import gui.ValidatingPasswordField;
import gui.ValidatingTextField;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import lombok.Setter;

public class GuiLoginController {

    private final BooleanProperty loginFailedProperty = new SimpleBooleanProperty(false);
    private final StringProperty errorText = new SimpleStringProperty("Password must be at least 4 characters long");
    @FXML
    private ValidatingTextField emailTextField;
    @FXML
    private ValidatingPasswordField passwordField;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button loginButton;
    @FXML
    private Label errorLabel;
    @FXML
    private ImageView backgroundImage;
    @FXML
    private ProgressIndicator loadingProgressIndicator;
    @FXML
    private StackPane loginCard;
    @FXML
    private VBox loginBox;
    @Setter
    private GuiController guiController;

    @FXML
    public void initialize() {
        backgroundImage.fitWidthProperty().bind(anchorPane.widthProperty());
        backgroundImage.fitHeightProperty().bind(anchorPane.heightProperty());
        emailTextField.setValidation(s -> s.matches("(?:[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?\\.)+[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"));
        emailTextField.bindLoginFailedProperty(loginFailedProperty);
        passwordField.setValidation(s -> s.length() >= 4);
        passwordField.bindLoginFailedProperty(loginFailedProperty);

        emailTextField.getIsValidProperty().addListener((observable, oldValue, newValue) -> {
            loginFailedProperty.set(false);
            if (!newValue) {
                errorText.setValue("Email is not valid");
            } else {
                errorText.setValue("Password must be at least 4 characters long");
            }
        });

        passwordField.getIsValidProperty().addListener((observable, oldValue, newValue) -> {
            loginFailedProperty.set(false);
            if (!newValue) {
                errorText.setValue("Password must be at least 4 characters long");
            } else {
                errorText.setValue("Email is not valid");
            }
        });


        loginButton.disableProperty().bind(emailTextField.getIsValidProperty().not().or(passwordField.getIsValidProperty().not()));
        errorLabel.visibleProperty().bind(((emailTextField.getIsValidProperty().and(passwordField.getIsValidProperty())).not()).or(loginFailedProperty));


//        errorLabel.visibleProperty().bind(emailTextField.getIsValidProperty().not().or(passwordField.getIsValidProperty().not()).or(loginFailedProperty));
        errorLabel.textProperty().bind(errorText);

        loadingProgressIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
    }

    public void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            loginButton.fire();
        }
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        // Get user type, username, and password from the controls
        String email = emailTextField.getText().toLowerCase();
        String password = passwordField.getText();

        // Create a background service for the login process
        Service<EmployeeController> loginService = new Service<>() {
            @Override
            protected Task<EmployeeController> createTask() {
                return new Task<>() {
                    @Override
                    protected EmployeeController call() throws Exception {
                        return guiController.getLoginController().handleLogin(email, password);
                    }
                };
            }
        };

        // Set up event handlers for the service
        loginService.setOnSucceeded((WorkerStateEvent workerStateEvent) -> {
            guiController.setEmployeeController(loginService.getValue());
            guiController.showOrderOverview();
        });

        loginService.setOnFailed((WorkerStateEvent workerStateEvent) -> {
            errorText.setValue("Wrong username or password");
            loginFailedProperty.setValue(true);
        });

//        // Show the progress indicator and start the background service
        loadingProgressIndicator.visibleProperty().bind(loginService.runningProperty());
        loginService.start();
    }
}
