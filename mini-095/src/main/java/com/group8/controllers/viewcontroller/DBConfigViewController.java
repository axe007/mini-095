package com.group8.controllers.viewcontroller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.group8.controllers.DatabaseController;
import com.group8.helper.UIHelper;
import com.group8.model.Session;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class DBConfigViewController implements Initializable {
    private final String WITHOUTAUTH = "Without Authentication";
    private final String WITHAUTH = "With Authentication";
    UIHelper uiHelper = new UIHelper();
    @FXML
    private TextField dbUsernameField;
    @FXML
    private TextField dbPasswordField;
    @FXML
    private StackPane dialogPane;
    @FXML
    private TextField dbPortTextField;
    @FXML
    private TextField authCollactionTextField;
    @FXML
    private TextField dbAddressTextField;
    @FXML
    private ComboBox<String> authSelectComboBox;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label authCollectionLabel;
    @FXML
    private Label dbUsernameLabel;
    @FXML
    private Label dbPasswordLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        authSelectComboBox.getItems().add(WITHAUTH);
        authSelectComboBox.getItems().add(WITHOUTAUTH);
        authSelectComboBox.setValue(WITHAUTH);
        // TODO: presist db info
    }

    @FXML
    private void handleSaveButton(ActionEvent event) {
        if (validateInput()) {
            if (authSelectComboBox.getValue().equals(WITHOUTAUTH)) {
                Session.setLocalDb(true);
                DatabaseController.dbLocalServer = dbAddressTextField.getText();
                DatabaseController.dbPort = Integer.parseInt(dbPortTextField.getText());

            } else if (authSelectComboBox.getValue().equals(WITHOUTAUTH)) {
                Session.setLocalDb(false);
                DatabaseController.dbUser = dbUsernameField.getText();
                DatabaseController.dbPassword = dbPasswordField.getText().toCharArray();
                DatabaseController.authdbName = authCollactionTextField.getText();
                DatabaseController.dbServer = dbAddressTextField.getText();

            }
            Optional<ButtonType> result = uiHelper.alertDialogGenerator(dialogPane, "success", "Database Setting",
                    "Database setting updated successfully.");
            if (result.get() == ButtonType.OK) {
                Stage stage = (Stage) saveButton.getScene().getWindow();
                stage.close();
            }

        }

    }

    public boolean validateInput() {
        if (authSelectComboBox.getValue().equals(WITHOUTAUTH)) {

            if (dbAddressTextField.getText().isEmpty()) {
                uiHelper.alertDialogGenerator(dialogPane, "error", "Empty Database Adress",
                        "Database address is missing.\nPlease check the field and try again.");
                dbAddressTextField.getStyleClass().add("textfield-error-highlight");
                return false;
            } else if (dbPortTextField.getText().isEmpty()) {
                uiHelper.alertDialogGenerator(dialogPane, "error", "Empty Database Port",
                        "Database Port number is missing.\nPlease check the field and try again.");
                dbPortTextField.getStyleClass().add("textfield-error-highlight");
                return false;

            } else if (Integer.parseInt(dbPortTextField.getText()) > 65535
                    || Integer.parseInt(dbPortTextField.getText()) < 0) {
                uiHelper.alertDialogGenerator(dialogPane, "error", "Wrong Port Number",
                        "Port number is out of Range.\nPlease check the field and try again.");
                dbPortTextField.getStyleClass().add("textfield-error-highlight");
                return false;

            } else {

                return true;
            }

        } else if (authSelectComboBox.getValue().equals(WITHAUTH)) {

            if (dbAddressTextField.getText().isEmpty()) {
                uiHelper.alertDialogGenerator(dialogPane, "error", "Empty Database Adress",
                        "Database address is missing.\nPlease check the field and try again.");
                dbAddressTextField.getStyleClass().add("textfield-error-highlight");
                return false;
            } else if (dbPortTextField.getText().isEmpty()) {
                uiHelper.alertDialogGenerator(dialogPane, "error", "Empty Database Port",
                        "Database Port number is missing.\nPlease check the field and try again.");
                dbPortTextField.getStyleClass().add("textfield-error-highlight");
                return false;

            } else if (Integer.parseInt(dbPortTextField.getText()) > 65535
                    || Integer.parseInt(dbPortTextField.getText()) < 0) {
                uiHelper.alertDialogGenerator(dialogPane, "error", "Wrong Port Number",
                        "Port number is out of Range.\nPlease check the field and try again.");
                dbPortTextField.getStyleClass().add("textfield-error-highlight");
                return false;

            } else if (authCollactionTextField.getText().isEmpty()) {
                uiHelper.alertDialogGenerator(dialogPane, "error", "Empty Database Auth Colletcion",
                        "Database Auth Colletcion is missing.\nPlease check the field and try again.");
                authCollactionTextField.getStyleClass().add("textfield-error-highlight");
                return false;
            } else if (dbUsernameField.getText().isEmpty()) {
                uiHelper.alertDialogGenerator(dialogPane, "error", "Database Username is missing",
                        "Database Username is missing.\nPlease check the field and try again.");
                dbUsernameField.getStyleClass().add("textfield-error-highlight");
                return false;
            } else if (dbPasswordField.getText().isEmpty()) {
                uiHelper.alertDialogGenerator(dialogPane, "error", "Empty Database Password",
                        "Database Password is missing.\nPlease check the field and try again.");
                dbPasswordField.getStyleClass().add("textfield-error-highlight");
                return false;
            } else {
                return true;
            }

        } else {
            return false;
        }
    }

    @FXML
    private void handleCancelButton(ActionEvent event) {
        Stage stage;
        stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onAuthComboBoxChange(ActionEvent event) {
        if (authSelectComboBox.getValue().equals(WITHOUTAUTH)) {
            dbPasswordField.setVisible(false);
            dbPasswordLabel.setVisible(false);
            dbUsernameField.setVisible(false);
            dbUsernameLabel.setVisible(false);
            authCollactionTextField.setVisible(false);
            authCollectionLabel.setVisible(false);

        } else {
            dbPasswordField.setVisible(true);
            dbPasswordLabel.setVisible(true);
            dbUsernameField.setVisible(true);
            dbUsernameLabel.setVisible(true);
            authCollactionTextField.setVisible(true);
            authCollectionLabel.setVisible(true);
        }

    }

}
