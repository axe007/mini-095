package com.group8.controllers;

import java.io.IOException;

import com.group8.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AdminViewController {










    
    @FXML
    private Button logOutButton;
    @FXML
    private void handleLogOutButtonAction(ActionEvent event) throws IOException {
        App.setRoot("LoginView");
    }

}
