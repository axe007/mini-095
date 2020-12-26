package com.group8.controllers.viewcontroller;

import com.group8.controllers.ActivityController;
import com.group8.controllers.ProjectController;
import com.group8.helper.UIHelper;
import com.group8.model.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import org.bson.types.ObjectId;

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
            // Create new activity window
            if (checkOpenProject("Create new activity")) {
                Session.setWindowMode("new");
                uiHelper.loadWindow("ActivityAddView", activityNewButton, "Create new activity");
            }
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void activitiesSearchOnKeyReleased(KeyEvent event) {
        /*
         * tblActivities.getItems().clear(); activityController.activityName =
         * activitiesSearch.getText();
         */
    }

    @FXML
    private void btnRefreshOnAction(ActionEvent event) {
        /*
         * tblActivities.getItems().clear(); activitiesSearch.clear();
         */
    }

    private boolean checkOpenProject(String requestedViewTitle) {
        boolean isProjectOpen = false;
        ObjectId projectId = Session.getOpenProjectId();
        if (projectId == null || projectId.equals(null)) {
            uiHelper.alertDialogGenerator(activitiesView,"error", requestedViewTitle, "No project has been opened.\nPlease open a project in Projects window.");
        } else {
            isProjectOpen = true;
        }
        return isProjectOpen;
    }
}