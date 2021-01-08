package com.group8.controllers.viewcontroller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

public class DBConfigViewController implements Initializable {
    @FXML
    private TextField dbUsernamField;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub

    }

    @FXML
    private void handleSaveButton(ActionEvent event) {

    }

    @FXML
    private void handleCancelButton(ActionEvent event) {

    }

    @FXML
    private void onAuthComboBoxChange(ActionEvent event) {
    }

}
