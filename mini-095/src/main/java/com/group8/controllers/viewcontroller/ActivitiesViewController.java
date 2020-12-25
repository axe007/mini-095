package com.group8.controllers.viewcontroller;

import com.group8.model.Session;
import com.group8.model.User;
import com.group8.model.Activity;
import com.group8.model.Project;

import com.group8.helper.UIHelper;

import com.group8.controllers.ActivityController;
import com.group8.controllers.ApplicationController;
import com.group8.controllers.ProjectController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ActivitiesViewController implements Initializable {

    private static ActivityController activityController = new ActivityController();
    private static UIHelper uiHelper = new UIHelper();

    @FXML
    private StackPane activitiesView;
    @FXML
    private Button activityNewButton;
    @FXML
    private Button activityOpenButton;
    @FXML
    private Button activityAssignButton;
    @FXML
    private Button activityListButton;
    @FXML
    private Button activityArchiveButton;
    @FXML
    private TextField activitySearch;

    @FXML
    private void handleActivityButtons(ActionEvent event) throws IOException {
        // clear all text field
        if (event.getSource() == activityNewButton) {
            // Create new project window
            Session.setWindowMode("new");
            System.out.print("activate 1 "); 
            uiHelper.loadWindow("ActivitiesAddView", activityNewButton, (Activity) null);
            // uiHelper.loadWindow("UserAddView", activityNewButton, (User) null);
            // uiHelper.loadWindow("ProjectAddView", activityNewButton, (Project) null);

            System.out.print("activate 2 "); 

            // uiHelper.loadWindow("UserAddView", activityNewButton);


        } else if (event.getSource() == activityOpenButton) {
            // Open project window

        } else if (event.getSource() == activityAssignButton) {
            // List all projects window

        } else if (event.getSource() == activityListButton) {
            // List all projects window

        } else if (event.getSource() == activityArchiveButton) {
            // Archive project window
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            // TODO
            if (Session.getWindowMode().equals("new")) {
                // windowModeTitle.setText("Enter new project details:");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void activitiesSearchOnKeyReleased(KeyEvent event) {
        /*tblActivities.getItems().clear();
        activityController.activityName = activitiesSearch.getText();*/
    }

    @FXML
    private void btnRefreshOnAction(ActionEvent event) {
        /*tblActivities.getItems().clear();
        activitiesSearch.clear();*/
    }
}