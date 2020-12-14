package com.group8.controllers;

import java.io.IOException;

import com.group8.App;
import com.group8.constants.Constants;
import com.group8.controllers.ApplicationController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class LoginViewController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private HBox loginErrorMsg;

    @FXML
    private void handleLoginButton(ActionEvent event) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.equalsIgnoreCase(Constants.ADMIN_USERNAME) && password.equals(Constants.ADMIN_PASSWORD)) {
            ApplicationController appController = new ApplicationController();
            App.setRoot("Application");

        } else {
            // display error info
            loginErrorMsg.setVisible(true);
        }
    }

    @FXML
    private void handleResetButton(ActionEvent event) throws IOException {
        usernameField.clear();
        passwordField.clear();
        loginErrorMsg.setVisible(false);
    }
}