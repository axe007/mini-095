package com.group8.controllers;

import java.io.IOException;

import com.group8.App;
import com.group8.constants.Constants;
import com.group8.model.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import org.bson.types.ObjectId;

public class LoginViewController {

    private UserController userController = new UserController();

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private HBox loginErrorMsg;
    @FXML
    private CheckBox localDb;

    @FXML
    private void handleLoginButton(ActionEvent event) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        boolean localDb = this.localDb.isSelected();

        if (username.equalsIgnoreCase(Constants.ADMIN_USERNAME) && password.equals(Constants.ADMIN_PASSWORD)) {
            initializeApp("admin", localDb);
        } else if (userController.authenticateUser(username, password)) {
            initializeApp(username, localDb);
        } else {
            // display error info
            loginErrorMsg.setVisible(true);
        }
    }

    public void initializeApp(String loggedUsername, boolean localDb) throws IOException {
        UserController userController = new UserController();
        ObjectId loggedUserId = userController.getUserId("username", loggedUsername);
        Session.getInstance(loggedUserId);
        Session.setLocalDb(localDb);
        App app = new App();
        app.setRoot("Application");
    }

    @FXML
    private void handleResetButton(ActionEvent event) throws IOException {

        usernameField.clear();
        passwordField.clear();
        loginErrorMsg.setVisible(false);
    }

}