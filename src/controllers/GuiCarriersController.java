package controllers;

import dtos.CarrierDto;
import exceptions.NoAccessException;
import interfaces.CarrierInterface;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Screen;

import java.io.File;
import java.util.Objects;

public class GuiCarriersController {

    private final ObservableList<CarrierInterface> carriersData = FXCollections.observableArrayList();
    StringProperty numberOfCarriers = new SimpleStringProperty("");
    private GuiController guiController;
    @FXML
    private GuiNavbarController navbarController;
    @FXML
    private Label instructionLabel;
    @FXML
    private GridPane carrierDetailsGrid;
    @FXML
    private TableView<CarrierInterface> carrierTable;
    @FXML
    private TableColumn<CarrierInterface, String> carrierNameColumn;
    @FXML
    private TableColumn<CarrierInterface, String> carrierActiveColumn;
    @FXML
    private TextField carrierNameField;
    @FXML
    private TextField carrierEmailField;
    @FXML
    private TextField carrierPhoneField;
    @FXML
    private CheckBox carrierActiveCheck;
    @FXML
    private TextField carrierCharactersField;
    @FXML
    private CheckBox carrierNumOnlyCheck;
    @FXML
    private TextField carrierPrefixField;
    @FXML
    private ImageView carrierImageView;
    @FXML
    private Button deactivateButton;
    @FXML
    private Button addButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button updateButton;
    @FXML
    private Label carriersLabel;
    @FXML
    private ChoiceBox activeFilter;
    @FXML
    private VBox leftPane;
    @FXML
    private StackPane rightPane;
    @FXML
    private HBox mainPane;
    private CarrierInterface selectedCarrier;

    public void setGuiController(GuiController guiController) {
        this.guiController = guiController;
        navbarController.setGuiController(guiController);
    }

    public void initializeController() {
        // this is set to false in order to show the instruction label
        carrierDetailsGrid.setVisible(false);
        // initialize carriers table view
        carrierNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        carrierActiveColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().isActive() ? "Active" : "Inactive"));

//        add listener to isModified property to enable/disable save button
//        updateButton.disableProperty().bind(isModified.not());

        // add listener to table to update carrier details when a carrier is selected
        carrierTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                resetFields();
                selectedCarrier = newSelection;
                updateCarrierDetails(newSelection);
                buttonStatesViewing();
            }
        });

        activeFilter.setItems(FXCollections.observableArrayList("All", "Active", "Inactive"));
        activeFilter.getSelectionModel().selectFirst();
        activeFilter.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                updateCarriersView();
            }
        });
        updateCarriersView();

        deactivateButton.textProperty().bind(Bindings.when(carrierActiveCheck.selectedProperty()).then("Deactivate").otherwise("Activate"));
        addButton.setTooltip(new Tooltip("Click to start adding a new carrier"));
        buttonStatesInitial();
        navbarController.initializeController();

        var screenBounds = Screen.getPrimary().getBounds();
        var styleClass = screenBounds.getWidth() > 1600 ? "carrier-details-large" : "carrier-details-small";
        mainPane.setMaxHeight(screenBounds.getHeight() * 0.8);
        leftPane.setMaxWidth(screenBounds.getWidth() * 0.4);
        leftPane.setPrefWidth(screenBounds.getWidth() * 0.4);
        rightPane.setMaxWidth(screenBounds.getWidth() * 0.4);
        rightPane.getStyleClass().add(styleClass);

    }

    private void updateCarrierDetails(CarrierInterface carrier) {
        if (instructionLabel.isVisible()) {
            instructionLabel.setVisible(false);
            carrierDetailsGrid.setVisible(true);
        }
        carrierNameField.setText(carrier.getName());
        carrierEmailField.setText(carrier.getEmail());
        carrierPhoneField.setText(carrier.getPhone());
        carrierActiveCheck.setSelected(carrier.isActive());
        carrierCharactersField.setText(String.valueOf(carrier.getAmountOfCharacters()));
        carrierNumOnlyCheck.setSelected(carrier.isNumOnly());
        carrierPrefixField.setText(carrier.getPrefix());
        if (carrier.getImage() != null && !carrier.getImage().isEmpty()) {
            carrierImageView.setImage(new Image(carrier.getImage()));
        }
    }

    private void updateCarriersView() {
        String filter = activeFilter.getSelectionModel().getSelectedItem().toString();
        switch (filter) {
            case "All" -> {
                carriersData.clear();
                carriersData.addAll(guiController.getEmployeeController().getCarriers());
                carrierTable.setItems(carriersData);
                carriersLabel.setText("Carriers: " + carriersData.size());
            }
            case "Active" -> {
                carriersData.clear();
                carriersData.addAll(guiController.getEmployeeController().getCarriers().stream().filter(CarrierInterface::isActive).toList());
                carrierTable.setItems(carriersData);
                carriersLabel.setText("Carriers: " + carriersData.size());
            }
            case "Inactive" -> {
                carriersData.clear();
                carriersData.addAll(guiController.getEmployeeController().getCarriers().stream().filter(carrier -> !carrier.isActive()).toList());
                carrierTable.setItems(carriersData);
                carriersLabel.setText("Carriers: " + carriersData.size());
            }
        }
    }

    public void handleUpdate() {
        if (selectedCarrier == null) {
            guiController.showAlert("No carrier selected", "Please select a carrier to update", Alert.AlertType.WARNING);
            return;
        }

        if (!hasCarrierChanged(selectedCarrier)) {
            guiController.showAlert("Carrier hasn't been changed", "You haven't changed anything", Alert.AlertType.ERROR);
            return;
        }

        try {
            var editedCarrier = new CarrierDto(carrierNameField.getText(), carrierEmailField.getText(), carrierPhoneField.getText(), carrierImageView.getImage() != null ? carrierImageView.getImage().getUrl() : "", carrierActiveCheck.isSelected(), Integer.parseInt(carrierCharactersField.getText()), carrierNumOnlyCheck.isSelected(), carrierPrefixField.getText());
            var oldCarrier = new CarrierDto(selectedCarrier.getName(), selectedCarrier.getEmail(), selectedCarrier.getPhone(), selectedCarrier.getImage(), selectedCarrier.isActive(), selectedCarrier.getAmountOfCharacters(), selectedCarrier.isNumOnly(), selectedCarrier.getPrefix());
            validateCarrierDto(oldCarrier, editedCarrier);
            guiController.getEmployeeController().updateCarrier(selectedCarrier.getId(), editedCarrier);
            updateCarriersView();
            guiController.showAlert("Carrier: " + carrierNameField.getText() + " updated", "Carrier details have been updated", Alert.AlertType.INFORMATION);

        } catch (NoAccessException e) {
            guiController.showAlert("No access", "You do not have access to update this carrier", Alert.AlertType.WARNING);
        } catch (NumberFormatException e) {
            guiController.showAlert("Invalid field value", "Please ensure all fields are properly filled", Alert.AlertType.ERROR);
        } catch (IllegalArgumentException e) {
            guiController.showAlert("An error in fields", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public boolean hasCarrierChanged(CarrierInterface selectedCarrier) {
        return !Objects.equals(selectedCarrier.getName(), carrierNameField.getText()) || !Objects.equals(selectedCarrier.getEmail(), carrierEmailField.getText()) || !Objects.equals(selectedCarrier.getPhone(), carrierPhoneField.getText()) || selectedCarrier.isActive() != carrierActiveCheck.isSelected() || selectedCarrier.getAmountOfCharacters() != Integer.parseInt(carrierCharactersField.getText()) || selectedCarrier.isNumOnly() != carrierNumOnlyCheck.isSelected() || !Objects.equals(selectedCarrier.getPrefix(), carrierPrefixField.getText()) || !Objects.equals(selectedCarrier.getImage(), carrierImageView.getImage() != null ? carrierImageView.getImage().getUrl() : "");
    }


    public void handleDeactivate() {
        if (selectedCarrier != null) {
            try {
                guiController.getEmployeeController().toggleActivation(selectedCarrier.getId());
                updateCarriersView();
                CarrierInterface updatedCarrier = guiController.getEmployeeController().getCarriers().stream().filter(carrier -> carrier.getId().equals(selectedCarrier.getId())).findFirst().orElse(null);
                updateCarrierDetails(updatedCarrier);
                guiController.showAlert("Carrier: " + carrierNameField.getText() + " updated", "Carrier: " + carrierNameField.getText() + " has been " + (carrierActiveCheck.isSelected() ? "activated" : "deactivated"), Alert.AlertType.INFORMATION);
            } catch (NoAccessException e) {
                guiController.showAlert("No access", "You do not have access to update this carrier", Alert.AlertType.WARNING);
            }
        } else {
            guiController.showAlert("No carrier selected", "Please select a carrier to update", Alert.AlertType.WARNING);
        }
    }

    public void handleAdd() {
        resetFields();
        carrierDetailsGrid.setVisible(true);
        instructionLabel.setVisible(false);
        buttonStatesAdding();
    }

    public void handleSave() {
        try {
            CarrierDto newCarrier = new CarrierDto(carrierNameField.getText(), carrierEmailField.getText(), carrierPhoneField.getText(), carrierImageView.getImage() != null ? carrierImageView.getImage().getUrl() : "", carrierActiveCheck.isSelected(), Integer.parseInt(carrierCharactersField.getText()), carrierNumOnlyCheck.isSelected(), carrierPrefixField.getText());
            validateCarrierDto(null, newCarrier);
            try {
                guiController.getEmployeeController().addCarrier(newCarrier);
                updateCarriersView();
                guiController.showAlert("Carrier: " + carrierNameField.getText() + " added", "Carrier: " + carrierNameField.getText() + " has been added", Alert.AlertType.INFORMATION);
                saveButton.setDisable(true);
            } catch (NoAccessException e) {
                guiController.showAlert("No access", "You do not have access to add a carrier", Alert.AlertType.WARNING);
            } catch (IllegalArgumentException e) {
                guiController.showAlert("An error in fields", "There's a mistake in a field you've tried to add", Alert.AlertType.ERROR);
            }
        } catch (NumberFormatException e) {
            guiController.showAlert("An error in fields", "Amount of characters can't be empty and must be a number", Alert.AlertType.ERROR);
        } catch (IllegalArgumentException e) {
            guiController.showAlert("An error in fields", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void resetFields() {
        // set the selected carrier to null
        selectedCarrier = null;
        // delete all the fields
        carrierNameField.setText("");
        carrierEmailField.setText("");
        carrierPhoneField.setText("");
        carrierActiveCheck.setSelected(false);
        carrierCharactersField.setText("");
        carrierNumOnlyCheck.setSelected(false);
        carrierPrefixField.setText("");
        carrierImageView.setImage(null);
    }

    public void validateCarrierDto(CarrierDto oldCarrierDto, CarrierDto newCarrierDto) {
        // Validate name
        if (oldCarrierDto == null || !Objects.equals(oldCarrierDto.name(), newCarrierDto.name())) {
            if (newCarrierDto.name() == null || newCarrierDto.name().isEmpty()) {
                throw new IllegalArgumentException("Name cannot be null or empty");
            }
            // Check if new name already exists
            guiController.getEmployeeController().getCarriers().forEach(carrierInterface -> {
                if (carrierInterface.getName().toLowerCase().equals(newCarrierDto.name().toLowerCase())) {
                    throw new IllegalArgumentException("Carrier with this name already exists");
                }
            });
        }

        // Validate email
        if (oldCarrierDto == null || !Objects.equals(oldCarrierDto.email(), newCarrierDto.email())) {
            if (newCarrierDto.email() == null || newCarrierDto.email().isEmpty() || !newCarrierDto.email().contains("@")) {
                throw new IllegalArgumentException("Email cannot be null or empty and must contain @");
            }
        }

        // Validate phone
        if (oldCarrierDto == null || !Objects.equals(oldCarrierDto.phone(), newCarrierDto.phone())) {
            if (newCarrierDto.phone() == null || newCarrierDto.phone().isEmpty()) {
                throw new IllegalArgumentException("Phone cannot be null or empty");
            }
        }
        // validate amount of characters
        if (oldCarrierDto == null || oldCarrierDto.amountOfCharacters() != newCarrierDto.amountOfCharacters()) {
            if (newCarrierDto.amountOfCharacters() <= 0) {
                throw new IllegalArgumentException("Amount of characters must be greater than 0");
            }
        }
        // validate prefix
        if (oldCarrierDto == null || !Objects.equals(oldCarrierDto.prefix(), newCarrierDto.prefix())) {
            if (newCarrierDto.prefix() == null || newCarrierDto.prefix().isEmpty()) {
                throw new IllegalArgumentException("Prefix cannot be null or empty");
            }
        }

        // For the numOnly and prefix fields, validate prefix if either numOnly or prefix has changed
        if (oldCarrierDto == null || oldCarrierDto.numOnly() != newCarrierDto.numOnly() || !Objects.equals(oldCarrierDto.prefix(), newCarrierDto.prefix())) {
            if (newCarrierDto.prefix() == null || newCarrierDto.prefix().isEmpty()) {
                throw new IllegalArgumentException("Prefix cannot be null or empty");
            }
            // if numonly is true, prefix must be numbers only
            if (newCarrierDto.numOnly() && !newCarrierDto.prefix().matches("\\d+")) {
                throw new IllegalArgumentException("Prefix must be numbers only");
            }
        }
        // validate image
        if (oldCarrierDto == null || !Objects.equals(oldCarrierDto.image(), newCarrierDto.image())) {
            if (newCarrierDto.image() == null || newCarrierDto.image().isEmpty()) {
                throw new IllegalArgumentException("Image cannot be null or empty");
            }
        }
    }

//    public void validateCarrierDto(CarrierDto carrierDto) {
//        if (carrierDto.name() == null || carrierDto.name().isEmpty()) {
//            throw new IllegalArgumentException("Name cannot be null or empty");
//        }
//        if (carrierDto.email() == null || carrierDto.email().isEmpty() || !carrierDto.email().contains("@")) {
//            throw new IllegalArgumentException("Email cannot be null or empty and must contain @");
//        }
//        if (carrierDto.phone() == null || carrierDto.phone().isEmpty() || !carrierDto.phone().matches("[\\d\\-\\+\\s]+")) {
//            throw new IllegalArgumentException("Phone cannot be null or empty and must contain only digits");
//        }
//        if (carrierDto.amountOfCharacters() < 0) {
//            throw new IllegalArgumentException("Amount of characters must be a number and cannot be negative");
//        }
//        if (carrierDto.prefix() == null || carrierDto.prefix().isEmpty()) {
//            throw new IllegalArgumentException("Prefix cannot be null or empty");
//        }
//        // if numonly is true, prefix must be numbers only
//        if (carrierDto.numOnly() && !carrierDto.prefix().matches("\\d+")) {
//            throw new IllegalArgumentException("Prefix must be numbers only");
//        }
//        guiController.getEmployeeController().getCarriers().forEach(carrierInterface -> {
//            if (carrierInterface.getName().equals(carrierDto.name())) {
//                throw new IllegalArgumentException("Carrier with this name already exists");
//            }
//        });
//    }

    public void handleChooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp", "*.tiff"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            carrierImageView.setImage(image);
        }
    }

    public void buttonStatesInitial() {
        updateButton.setDisable(true);
        deactivateButton.setDisable(true);
        addButton.setDisable(false);
        saveButton.setDisable(true);
    }

    public void buttonStatesViewing() {
        saveButton.setDisable(true);
        addButton.setDisable(false);
        updateButton.setDisable(false);
        deactivateButton.setDisable(false);
    }

    public void buttonStatesAdding() {
        saveButton.setDisable(false);
        addButton.setDisable(true);
        updateButton.setDisable(true);
        deactivateButton.setDisable(true);
    }
}
