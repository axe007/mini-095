package com.group8.controllers;

import com.group8.constants.Constants;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
    private void handleLoginButtonAction(ActionEvent event) {
        // Button was clicked, do something...
        String userName = userNameTextField.getText();
        String password = passwordField.getText();
        if(userName!= null && !userName.isEmpty()&&password!=null&&!password.isEmpty()){
            if(userName.equalsIgnoreCase("admin")){
                if(password.equals(Constants.ADMIN_PASSWORD)){
                    // turn into next screen
                }else{
                    // display error info

                }
            }else{
                


            }

        }else{

        }

    }

}