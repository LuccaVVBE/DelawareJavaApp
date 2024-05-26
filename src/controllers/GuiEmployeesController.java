package controllers;

import dtos.AddressDto;
import dtos.EmployeeDto;
import enums.EmployeeRole;
import exceptions.NoAccessException;
import interfaces.AddressInterface;
import interfaces.EmployeeInterface;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class GuiEmployeesController {

    private final String ALL = "All";
    private final String ADMIN = "Admin";
    private final String EMPLOYEE = "Employee";
    private GuiController guiController;
    private EmployeeInterface currentEmployee;
    private ObservableList<EmployeeInterface> employees = FXCollections.observableArrayList();
    private AddressInterface companyAddress;
    @FXML
    private GuiNavbarController navbarController;
    @FXML
    private TableView<EmployeeInterface> employeeTable;
    @FXML
    private TableColumn<EmployeeInterface, String> nameColumn;
    @FXML
    private TableColumn<EmployeeInterface, String> roleColumn;
    @FXML
    private Label instructionLabel;
    @FXML
    private VBox detailForm;
    @FXML
    private TextField firstnameField;
    @FXML
    private TextField lastnameField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField emailField;
    @FXML
    private ComboBox<EmployeeRole> roleComboBox;
    @FXML
    private TitledPane addressPane;
    @FXML
    private TextField streetField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField countryField;
    @FXML
    private TextField zipCodeField;
    @FXML
    private TextField numberField;
    @FXML
    private TextField addressNameField;
    @FXML
    private Button updateButton;
    @FXML
    private Button makeAdminButton;
    @FXML
    private Button addButton;
    @FXML
    private Button saveButton;
    @FXML
    private Label employeesLabel;
    @FXML
    private ChoiceBox<String> roleChoiceBox;

    public void initializeController() {
        navbarController.initializeController();

        roleComboBox.setItems(FXCollections.observableArrayList(EmployeeRole.values()));
        roleChoiceBox.setItems(FXCollections.observableArrayList(ALL, ADMIN, EMPLOYEE));
        roleChoiceBox.getSelectionModel().selectFirst();
        roleChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateEmployeeTable();
            }
        });

        populateEmployeeTable();

        companyAddress = guiController.getEmployeeController().getEmployeeCompany().getAddresses().stream().findFirst().orElse(null);
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName() + " " + cellData.getValue().getLastName()));
        roleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRole().getDisplayName()));

        employeeTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                currentEmployee = newValue;
                detailForm.setVisible(true);
                instructionLabel.setVisible(false);
                buttonStatesViewing();
                populateEmployeeDetails(newValue);
            }
        });

    }

    private void populateEmployeeTable() {
        try {
            employees.clear();
            String filter = roleChoiceBox.getSelectionModel().getSelectedItem();
            if (filter.equals(ALL)) {
                employees.clear();
                employees.addAll(guiController.getEmployeeController().getCompanyEmployees());
                employeeTable.setItems(employees);
                employeeTable.sort();
                employeesLabel.setText("Employees: " + employees.size());
            } else if (filter.equals(ADMIN)) {
                employees.clear();
                employees.addAll(guiController.getEmployeeController().getCompanyEmployees().stream().filter(e -> e.getRole() == EmployeeRole.ADMIN).toList());
                employeeTable.setItems(employees);
                employeeTable.sort();
                employeesLabel.setText("Employees: " + employees.size());
            } else if (filter.equals(EMPLOYEE)) {
                employees.clear();
                employees.addAll(guiController.getEmployeeController().getCompanyEmployees().stream().filter(e -> e.getRole() == EmployeeRole.WAREHOUSE_EMPLOYEE).toList());
                employeeTable.setItems(employees);
                employeeTable.sort();
                employeesLabel.setText("Employees: " + employees.size());
            }
        } catch (NoAccessException e) {
            guiController.showAlert("No Access", "You do not have access to this page", Alert.AlertType.ERROR);
        }
    }

    private void populateEmployeeDetails(EmployeeInterface employee) {
        // Set the make admin button to disabled if the employee is already an admin
        if (employee.getRole() == EmployeeRole.ADMIN) {
            makeAdminButton.setDisable(true);
        } else {
            makeAdminButton.setDisable(false);
        }

        firstnameField.setText(employee.getFirstName());
        lastnameField.setText(employee.getLastName());
        phoneField.setText(guiController.getEmployeeController().getEmployeeCompany().getPhone());
        emailField.setText(employee.getEmail());
        roleComboBox.setValue(employee.getRole());
        // Populate Address Fields
        streetField.setText(companyAddress.getStreet());
        cityField.setText(companyAddress.getCity());
        countryField.setText(companyAddress.getCountry());
        zipCodeField.setText(companyAddress.getZipCode());
        numberField.setText(companyAddress.getNumber());
        addressNameField.setText(companyAddress.getName());
    }

    public void setGuiController(GuiController guiController) {
        this.guiController = guiController;
        this.navbarController.setGuiController(guiController);
    }

    public void handleUpdate() {
        if (currentEmployee == null) {
            guiController.showAlert("No Employee Selected", "Please select an employee", Alert.AlertType.ERROR);
            return;
        }

        AddressDto addressDto = new AddressDto(streetField.getText(), cityField.getText(), countryField.getText(), zipCodeField.getText(), numberField.getText(), addressNameField.getText());
        EmployeeDto employeeDto = new EmployeeDto(firstnameField.getText(), lastnameField.getText(), phoneField.getText(), emailField.getText(), roleComboBox.getValue());
        if (!hasEmployeeChanged(currentEmployee)) {
            guiController.showAlert("No Changes", "No changes were made to the employee", Alert.AlertType.INFORMATION);
            return;
        }
        try {
            guiController.getEmployeeController().updateEmployee(currentEmployee.getId(), employeeDto);
            populateEmployeeTable();
            guiController.showAlert("Success", "Employee updated", Alert.AlertType.INFORMATION);

        } catch (NoAccessException e) {
            guiController.showAlert("No Access", "You do not have access to this page", Alert.AlertType.ERROR);
        } catch (IllegalArgumentException e) {
            guiController.showAlert("Error", "Employee not found", Alert.AlertType.ERROR);
        }

    }


    public void handleMakeAdmin() {
        if (currentEmployee != null) {
            try {
                guiController.getEmployeeController().makeAdmin(currentEmployee.getId());
                populateEmployeeTable();
                guiController.showAlert("Success", "Employee is now an admin", Alert.AlertType.INFORMATION);
                populateEmployeeDetails(currentEmployee);
            } catch (NoAccessException e) {
                guiController.showAlert("No Access", "You do not have access to this page", Alert.AlertType.ERROR);
            } catch (IllegalArgumentException e) {
                guiController.showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            guiController.showAlert("No Employee Selected", "Please select an employee", Alert.AlertType.ERROR);
        }
    }

    public boolean hasEmployeeChanged(EmployeeInterface selectedEmployee) {
        return !Objects.equals(selectedEmployee.getFirstName(), firstnameField.getText()) || !Objects.equals(selectedEmployee.getLastName(), lastnameField.getText()) || !Objects.equals(selectedEmployee.getEmail(), emailField.getText()) || !Objects.equals(selectedEmployee.getRole(), roleComboBox.getValue());
    }

    public boolean hasAddressChanged(AddressInterface companyAddress) {
        return !Objects.equals(companyAddress.getStreet(), streetField.getText()) || !Objects.equals(companyAddress.getCity(), cityField.getText()) || !Objects.equals(companyAddress.getCountry(), countryField.getText()) || !Objects.equals(companyAddress.getZipCode(), zipCodeField.getText()) || !Objects.equals(companyAddress.getNumber(), numberField.getText()) || !Objects.equals(companyAddress.getName(), addressNameField.getText());
    }

    public void handleAdd() {
        resetFields();
        detailForm.setVisible(true);
        instructionLabel.setVisible(false);
        buttonStatesAdding();
    }

    private void resetFields() {
        firstnameField.setText("");
        lastnameField.setText("");
        phoneField.setText("");
        emailField.setText("");
        roleComboBox.setValue(null);
        streetField.setText("");
        cityField.setText("");
        countryField.setText("");
        zipCodeField.setText("");
        numberField.setText("");
        addressNameField.setText("");
        currentEmployee = null;
    }

    public void handleSave() {
        // check if the employee fields are empty
        try {
            AddressDto addressDto = new AddressDto(streetField.getText(), cityField.getText(), countryField.getText(), zipCodeField.getText(), numberField.getText(), addressNameField.getText());
            EmployeeDto employeeDto = new EmployeeDto(firstnameField.getText(), lastnameField.getText(), phoneField.getText(), emailField.getText(), roleComboBox.getValue());
            validateEmployeeDto(employeeDto);
            guiController.getEmployeeController().createEmployee(employeeDto);
            guiController.showAlert("Success", "Employee created", Alert.AlertType.INFORMATION);
            populateEmployeeTable();
            resetFields();
            buttonStatesInitial();
        } catch (IllegalArgumentException e) {
            guiController.showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        } catch (NoAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void validateEmployeeDto(EmployeeDto employeeDto) {
        if (employeeDto.firstname().isEmpty() || employeeDto.lastname().isEmpty() || employeeDto.email().isEmpty() || employeeDto.role() == null || employeeDto.phone().isEmpty()) {
            throw new IllegalArgumentException("Please fill in all the fields");
        }
    }

    public void buttonStatesInitial() {
        saveButton.setDisable(true);
        addButton.setDisable(false);
        updateButton.setDisable(true);
        makeAdminButton.setDisable(true);
    }

    public void buttonStatesViewing() {
        saveButton.setDisable(true);
        addButton.setDisable(false);
        updateButton.setDisable(false);
        makeAdminButton.setDisable(false);
    }

    public void buttonStatesAdding() {
        saveButton.setDisable(false);
        addButton.setDisable(true);
        updateButton.setDisable(true);
        makeAdminButton.setDisable(true);
    }
}
