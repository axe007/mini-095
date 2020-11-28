package com.group8.controllers;

import com.group8.constants.Constants;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginViewController {
    
    public Label titleLabel;
    public TextField userNameTextField;
    public PasswordField passwordField;
    public Button loginButton;

    public void handleLoginButtonAction(ActionEvent event) {
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