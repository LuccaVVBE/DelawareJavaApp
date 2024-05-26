package controllers;

import dtos.BoxDto;
import enums.BoxType;
import exceptions.InvalidDimensionsException;
import exceptions.InvalidPriceException;
import exceptions.NoAccessException;
import interfaces.BoxInterface;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;

import java.util.Arrays;
import java.util.Objects;

public class GuiBoxController {
    private final ObservableList<BoxInterface> boxesData = FXCollections.observableArrayList();
    private final BooleanProperty isModified = new SimpleBooleanProperty(false);
    private GuiController guiController;
    @FXML
    private GuiNavbarController navbarController;
    @FXML
    private Label instructionLabel;
    @FXML
    private GridPane boxDetailsGrid;

    @FXML
    private TableView<BoxInterface> boxTable;
    @FXML
    private TableColumn<BoxInterface, String> boxNameColumn;
    @FXML
    private TableColumn<BoxInterface, String> boxTypeColumn;
    @FXML
    private TableColumn<BoxInterface, String> boxActiveColumn;
    @FXML
    private TableColumn<BoxInterface, String> boxPriceColumn;

    @FXML
    private TextField boxNameField;
    @FXML
    private TextField boxPriceField;
    @FXML
    private TextField boxWidthField;
    @FXML
    private TextField boxHeightField;
    @FXML
    private TextField boxDepthField;
    @FXML
    private ChoiceBox<BoxType> boxTypeChoiceBox;
    @FXML
    private CheckBox boxActiveCheck;


    @FXML
    private Button addButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private StackPane rightPane;

    private BoxInterface selectedBox;


    public void initializeController() {
        // Allow elements to be editable/seen or not.
        startEnableElements();

        // this is set to false in order to show the instruction label
        boxDetailsGrid.setVisible(false);
        // initialize box table view
        updateBoxesOverview();
        boxNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        boxTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBoxType().toString()));
        boxActiveColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().isActive() ? "Active" : "Non-active"));
        boxPriceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.format("€%.2f", cellData.getValue().getPrice())));

        boxNameField.textProperty().addListener((observable, oldValue, newValue) -> isModified.set(true));
        boxPriceField.textProperty().addListener((observable, oldValue, newValue) -> isModified.set(true));
        boxWidthField.textProperty().addListener((observable, oldValue, newValue) -> isModified.set(true));
        boxHeightField.textProperty().addListener((observable, oldValue, newValue) -> isModified.set(true));
        boxDepthField.textProperty().addListener((observable, oldValue, newValue) -> isModified.set(true));

        boxTypeChoiceBox.setItems(FXCollections.observableArrayList(BoxType.values()));

        // add listener to table to update boxes details when a box is selected
        boxTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedBox = newSelection;
                updateBoxDetails(newSelection);
                if (guiController.getEmployeeController() instanceof AdminController) {
                    addButton.setDisable(false);
                    updateButton.setDisable(false);
                    deleteButton.setDisable(false);
                }
            }
        });


        for (TextField tf : Arrays.asList(boxPriceField, boxWidthField, boxHeightField, boxDepthField)) {
            tf.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("^(\\d+(\\.?\\d*))?$")) {
                    tf.setText(newValue.replaceAll("[^\\d.]", ""));
                }
            });
        }


        addButton.setTooltip(new Tooltip("Click to start adding a new box"));

        navbarController.initializeController();

        var screenBounds = Screen.getPrimary().getBounds();
        var styleClass = screenBounds.getWidth() > 1600 ? "box-details-large" : "box-details-small";
        rightPane.getStyleClass().add(styleClass);
    }

    public void setGuiController(GuiController guiController) {
        this.guiController = guiController;
        navbarController.setGuiController(guiController);
    }

    private void updateBoxDetails(BoxInterface box) {
        if (instructionLabel.isVisible()) {
            instructionLabel.setVisible(false);
            boxDetailsGrid.setVisible(true);
        }
        isModified.set(false);
        boxNameField.setText(box.getName());
        boxPriceField.setText(String.valueOf(box.getPrice()));
        boxTypeChoiceBox.setValue(BoxType.valueOf(box.getBoxType()));
        boxActiveCheck.setSelected(box.isActive());
        boxWidthField.setText(String.valueOf(box.getWidth()));
        boxHeightField.setText(String.valueOf(box.getHeight()));
        boxDepthField.setText(String.valueOf(box.getDepth()));
    }

    private void updateBoxesOverview() {
        boxesData.clear();
        boxesData.addAll(guiController.getEmployeeController().getBoxes());
        boxTable.setItems(boxesData);
    }

    public void handleUpdate(ActionEvent actionEvent) {
        if (selectedBox == null) {
            guiController.showAlert("No box selected", "Please select a box to update", Alert.AlertType.WARNING);
            return;
        }

        if (!hasBoxChanged(selectedBox)) {
            guiController.showAlert("Box hasn't been changed", "You haven't changed anything", Alert.AlertType.ERROR);
            return;
        }

        try {
            var editedBox = new BoxDto(
                    boxNameField.getText(),
                    Double.parseDouble(boxWidthField.getText()),
                    Double.parseDouble(boxHeightField.getText()),
                    Double.parseDouble(boxDepthField.getText()),
                    boxTypeChoiceBox.getValue(),
                    boxActiveCheck.isSelected(),
                    Double.parseDouble(boxPriceField.getText())
            );

            validateBoxDto(editedBox, false);
            guiController.getEmployeeController().updateBox(selectedBox.getName(), editedBox);
            updateBoxesOverview();
            guiController.showAlert("Box: " + boxNameField.getText() + " updated", "Box details have been updated", Alert.AlertType.INFORMATION);

        } catch (NoAccessException e) {
            guiController.showAlert("No access", "You do not have access to update this box", Alert.AlertType.WARNING);
        } catch (NumberFormatException e) {
            guiController.showAlert("Invalid field value", "Please ensure all fields are properly filled", Alert.AlertType.ERROR);
        } catch (InvalidPriceException | InvalidDimensionsException e) {
            guiController.showAlert("An error in fields", e.getMessage(), Alert.AlertType.ERROR);
        } catch (IllegalArgumentException e) {
            guiController.showAlert("An error in fields", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void validateBoxDto(BoxDto boxDto, boolean isAdding) throws InvalidDimensionsException, InvalidPriceException {
        if (boxDto.name() == null || boxDto.name().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (boxDto.width() <= 0 || boxDto.height() <= 0 || boxDto.depth() <= 0) {
            throw new InvalidDimensionsException();
        }
        if (boxDto.price() < 0) {
            throw new InvalidPriceException();
        }
        guiController.getEmployeeController().getBoxes().forEach(boxInterface -> {
            if (isAdding && boxInterface.getName().equals(boxDto.name())) {
                throw new IllegalArgumentException("Box with this name already exists");
            }
        });
    }


    public boolean hasBoxChanged(BoxInterface selectedBox) {
        return !Objects.equals(selectedBox.getName(), boxNameField.getText())
                || !Objects.equals(selectedBox.getBoxType(), boxTypeChoiceBox.getValue().toString())
                || selectedBox.isActive() != boxActiveCheck.isSelected()
                || selectedBox.getHeight() != Double.parseDouble(boxHeightField.getText())
                || selectedBox.getWidth() != Double.parseDouble(boxWidthField.getText())
                || selectedBox.getDepth() != Double.parseDouble(boxDepthField.getText())
                || selectedBox.getPrice() != Double.parseDouble(boxPriceField.getText());
    }

    public void handleAdd(ActionEvent actionEvent) {
        resetFields();
        boxDetailsGrid.setVisible(true);
        instructionLabel.setVisible(false);
        updateButton.setDisable(true);
        addButton.setDisable(true);
        saveButton.setDisable(false);
        deleteButton.setDisable(true);
    }

    private void resetFields() {
        // set the selected box to null
        selectedBox = null;
        // delete all the fields
        boxNameField.setText("");
        boxPriceField.setText("");
        boxActiveCheck.setSelected(true);
        boxTypeChoiceBox.setValue(BoxType.STANDARD);
        boxWidthField.setText("");
        boxHeightField.setText("");
        boxDepthField.setText("");
    }

    public void handleSave() {
        try {
            var newBox = new BoxDto(
                    boxNameField.getText(),
                    Double.parseDouble(boxWidthField.getText()),
                    Double.parseDouble(boxHeightField.getText()),
                    Double.parseDouble(boxDepthField.getText()),
                    boxTypeChoiceBox.getValue(),
                    boxActiveCheck.isSelected(),
                    Double.parseDouble(boxPriceField.getText())
            );
            validateBoxDto(newBox, true);
            guiController.getEmployeeController().addBox(newBox);
            updateBoxesOverview();
            guiController.showAlert("Box: " + boxNameField.getText() + " added", "Box: " + boxNameField.getText() + " has been added", Alert.AlertType.INFORMATION);
            saveButton.setDisable(true);
        } catch (NoAccessException e) {
            guiController.showAlert("No access", "You do not have access to add a box", Alert.AlertType.WARNING);
        } catch (IllegalArgumentException e) {
            guiController.showAlert("An error in fields", "There's a mistake in a field you've tried to add\n" + e.getLocalizedMessage(), Alert.AlertType.ERROR);
        } catch (InvalidPriceException | InvalidDimensionsException e) {
            guiController.showAlert("An error in fields", e.getMessage(), Alert.AlertType.ERROR);
        }

    }

    public void handleDelete(ActionEvent actionEvent) {
        if (selectedBox == null) {
            guiController.showAlert("No box selected", "Please select a box to delete", Alert.AlertType.WARNING);
            return;
        }


        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete box " + selectedBox.getName() + "? This action is irreversable.");
        a.showAndWait().ifPresent(response -> {
            if (response.getText().equals("OK")) {
                try {
                    guiController.getEmployeeController().removeBox(selectedBox.getName());
                    updateBoxesOverview();
                    resetFields();
                    deleteButton.setDisable(true);
                } catch (NoAccessException nae) {
                    guiController.showAlert("No access", "You do not have access to remove a box", Alert.AlertType.WARNING);
                } catch (IllegalArgumentException e) {
                    guiController.showAlert("An error occured", "Following error occured while trying to delete a box:\n" + e.getLocalizedMessage(), Alert.AlertType.ERROR);
                }
            } else {
                guiController.showAlert("Cancelled delete", "The deletion of box " + selectedBox.getName() + " has been cancelled", Alert.AlertType.INFORMATION);
            }
        });
    }

    private void startEnableElements() {
        if (guiController.getEmployeeController() instanceof AdminController) {
            boxNameField.setDisable(false);
            boxPriceField.setDisable(false);
            boxWidthField.setDisable(false);
            boxHeightField.setDisable(false);
            boxDepthField.setDisable(false);
            boxActiveCheck.setDisable(false);
            boxTypeChoiceBox.setDisable(false);
            addButton.setDisable(false);
        } else {
            addButton.setVisible(false);
            updateButton.setVisible(false);
            deleteButton.setVisible(false);
            saveButton.setVisible(false);
        }
    }


}
