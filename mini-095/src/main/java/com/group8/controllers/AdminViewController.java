package com.group8.controllers;

import java.io.IOException;

import com.group8.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

public class AdminViewController {
    @FXML
    private VBox adminView;
    @FXML
    private Pane topPane;
    @FXML
    private ListView employeeListView;
    @FXML
    private VBox userInfoVBox;
    @FXML
    private HBox userInfoRoleHBox;
    @FXML
    private RadioButton managerRadioButton;
    @FXML
    private RadioButton developerRadioButton;
    @FXML
    private HBox nameHBox;
    @FXML
    private Label firstNameLabel;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private Label lastNameLabel;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private HBox contactHBox;
    @FXML
    private Label emailLabel;
    @FXML
    private TextField emailTextField;
    @FXML
    private Label phoneLabel;
    @FXML
    private TextField phoneTextField;
    @FXML
    private Button deleteButton;
    @FXML
    private Button clearButton;
    @FXML
    private Button addSaveButton;

    @FXML
    private Button logOutButton;

    @FXML
    private void handleManagerRadioButtonAction(ActionEvent event) throws IOException {
        developerRadioButton.setSelected(false);
        managerRadioButton.setSelected(true);
    }

    @FXML
    private void handleDeveloperRadioButtonAction(ActionEvent event) throws IOException {
        developerRadioButton.setSelected(true);
        managerRadioButton.setSelected(false);
    }

    @FXML
    private void handleLogOutButtonAction(ActionEvent event) throws IOException {
        App.setRoot("LoginView");
    }

    @FXML
    private void handleDeleteButtonAction(ActionEvent event) throws IOException {
        // Delete selected User
    }

    @FXML
    private void handleClearButtonAction(ActionEvent event) throws IOException {
        // clear all text field
        firstNameTextField.clear();
        lastNameTextField.clear();
        phoneTextField.clear();
        emailTextField.clear();
    }

    @FXML
    private void handleAddSaveButtonAction(ActionEvent event) throws IOException {
        // Save or add current user to list
    }

}
