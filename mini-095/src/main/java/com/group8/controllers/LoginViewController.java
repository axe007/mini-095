package com.group8.controllers;

import java.io.IOException;

import com.group8.App;
import com.group8.constants.Constants;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class LoginViewController {
    @FXML
    private Label titleLabel;
    @FXML
    private TextField userNameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private HBox loginError;

    @FXML
    private void handleLoginButtonAction(ActionEvent event) throws IOException {
        // Button was clicked, do something...
        String userName = userNameTextField.getText();
        String password = passwordField.getText();

        if (userName.equalsIgnoreCase(Constants.ADMIN_USERNAME) && password.equals(Constants.ADMIN_PASSWORD)) {
            // turn into next screen
            App.setRoot("AdminView");
        } else {
            // display error info
            loginError.setVisible(true);
        }
    }

    @FXML
    private void handleResetButtonAction(ActionEvent event) throws IOException {
        userNameTextField.clear();
        passwordField.clear();
        loginError.setVisible(false);
    }
}