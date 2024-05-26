package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class GuiErrorController {
    private GuiController guiController;

    @FXML
    private GuiNavbarController navbarController;


    @FXML
    private Label errorLabel;

    @FXML
    private Button backButton;


    public void setGuiController(GuiController guiController) {
        this.guiController = guiController;
        navbarController.setGuiController(guiController);

    }

    public void initializeController(Exception e) {
        String errorMessage = e.getMessage();

        if (errorLabel != null) {
            if (errorMessage != null && !errorMessage.isEmpty()) {
                errorLabel.setText(errorMessage + "\n" + "Use the navigation bar to go back to the previous page");
            }
        }

//      if during logging in, an error occurs
        if (this.guiController.getEmployeeController() != null) {
            navbarController.initializeController();
            backButton.setVisible(false);
        } else {
            navbarController.hideNavbar();
            errorLabel.setText("An error during logging in occurred");
            backButton.setVisible(true);
        }
    }

    @FXML
    private void showLogin() {
        guiController.showLogin();
    }

}


