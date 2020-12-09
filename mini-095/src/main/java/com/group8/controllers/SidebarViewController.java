package com.group8.controllers;

import com.group8.App;
import com.group8.model.UserWithoutPm;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SidebarViewController implements Initializable {
    @FXML
    private GridPane sidebarGridPane;
    @FXML
    private Button dashboardButton;
    @FXML
    private Button projectButton;
    @FXML
    private Button sprintboardButton;
    @FXML
    private Button tasksButton;
    @FXML
    private Button usersButton;
    @FXML
    private Button logoutButton;

    @FXML
    private void handleSidebarBtn(ActionEvent menuEvent) throws IOException {

        ProjectViewController pvController = new ProjectViewController();
        // clear all text field
        if (menuEvent.getSource() == dashboardButton) {
            pvController.changeText("Dashboard");
        } else if (menuEvent.getSource() == projectButton) {
            pvController.changeText("Projects");
        } else if (menuEvent.getSource() == sprintboardButton) {
            pvController.changeText("Sprint Board");
        } else if (menuEvent.getSource() == logoutButton) {
            App.setRoot("LoginView");
        }



    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getBounds();
        // sidebarGridPane.setMinHeight(primaryScreenBounds.getHeight() * 0.85);
    }

   /* @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            // Node node = FXMLLoader.load(getClass().getResource("fxml/Sidebar.fxml"));
            leftSidebar.getChildren().add(addSaveButton);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

}