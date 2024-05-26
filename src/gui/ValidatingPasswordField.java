package gui;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.PseudoClass;
import javafx.scene.control.PasswordField;

import java.util.function.Predicate;

public class ValidatingPasswordField extends PasswordField {

    private static final PseudoClass INVALID = PseudoClass.getPseudoClass("invalid");
    private Predicate<String> validation;
    private BooleanProperty isValidProperty = new SimpleBooleanProperty();
    private BooleanProperty loginFailedProperty = new SimpleBooleanProperty();

    public ValidatingPasswordField() {
        super();
        this.getStyleClass().add("validating-text-field");
        textProperty().addListener((observable, oldValue, newValue) -> isValidProperty.set(validation.test(newValue)));
        isValidProperty.addListener((observable, oldValue, newValue) -> pseudoClassStateChanged(INVALID, !newValue));
        loginFailedProperty.addListener((observable, oldValue, newValue) -> pseudoClassStateChanged(INVALID, newValue));
    }

    public BooleanProperty getIsValidProperty() {
        return isValidProperty;
    }

    public void setValidation(Predicate<String> validation) {
        this.validation = validation;
    }

    public void bindLoginFailedProperty(BooleanProperty loginFailedProperty) {
        this.loginFailedProperty.bind(loginFailedProperty);
    }
}