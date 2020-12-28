package com.group8.controllers.viewcontroller;

import com.group8.model.Session;
import com.group8.model.User;
import com.group8.model.Activity;
import com.group8.model.Project;

import com.group8.helper.UIHelper;

import com.group8.controllers.ActivityController;
import com.group8.controllers.ApplicationController;
import com.group8.controllers.ProjectController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import org.bson.types.ObjectId;

public class ActivitiesViewController implements Initializable {

    private static ActivityController activityController = new ActivityController();
    private static UIHelper uiHelper = new UIHelper();

    @FXML
    private TextField activitiesSearch;

    @FXML
    private Button activitiesRefresh;

    @FXML
    private TableView<Activity> tblActivities = new TableView<Activity>();

    @FXML
    private TableColumn<Activity, ObjectId> tblClmProjectName;

    @FXML
    private TableColumn<Activity, ObjectId> tblClmActivityId;

    @FXML
    private TableColumn<Activity, String> tblClmActivityName;

    @FXML
    private TableColumn<Activity, String> tblClmActivityDescription;

    @FXML
    private TableColumn<Activity, String> tblClmActivityStartDate;

    @FXML
    private TableColumn<Activity, String> tblClmActivityEndDate;

    @FXML
    private TableColumn<Activity, String> tblClmActivityPriority;

    @FXML
    private TableColumn<Activity, String> tblClmActivityType;

    @FXML
    private TableColumn<Activity, String> tblClmActivityStatus;

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
            ObjectId projectId = Session.getOpenProjectId();
            if (projectId == null || projectId.equals(null)) {
                uiHelper.alertDialogGenerator(activitiesView,"error", "Create an activity", "No project has been opened.\nPlease open a project in Projects window.");
            } else {
                Session.setWindowMode("new");
                uiHelper.loadWindow("ActivitiesAddView", activityNewButton, "Create an activity");
            }
            // Create new project window
            // Session.setWindowMode("new");
            // System.out.print("activate 1 "); 
            // uiHelper.loadWindow("ActivitiesAddView", activityNewButton, (Activity) null);
            // // uiHelper.loadWindow("UserAddView", activityNewButton, (User) null);
            // // uiHelper.loadWindow("ProjectAddView", activityNewButton, (Project) null);

            // System.out.print("activate 2 "); 

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
            // if (Session.getWindowMode().equals("new")) {
            // //     // windowModeTitle.setText("Enter new project details:");
            //  }
            loadActivityData(); 

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void loadActivityData() {

        //getting the full list of books from file
        List<Activity> activityList = activityController.getActivityList();
        ObservableList<Activity> viewActivities = (ObservableList<Activity>) FXCollections.observableArrayList(activityList);

        tblClmProjectName.setCellValueFactory(new PropertyValueFactory<>("projectName"));
        tblClmActivityId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblClmActivityName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tblClmActivityDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tblClmActivityStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        tblClmActivityEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        tblClmActivityType.setCellValueFactory(new PropertyValueFactory<>("type"));
        tblClmActivityPriority.setCellValueFactory(new PropertyValueFactory<>("priority"));
        
        // tblClmProjectStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        tblActivities.setItems(viewActivities);
        for (Activity viewactivity : activityList) {
            System.out.println(viewactivity); 
        }
    }
    @FXML
    private void activitiesSearchOnKeyReleased(KeyEvent event) {
        /*tblActivities.getItems().clear();
        activityController.activityName = activitiesSearch.getText();*/
    }

    @FXML
    private void btnRefreshOnAction(ActionEvent event) {
        loadActivityData(); 
        /*tblActivities.getItems().clear();
        activitiesSearch.clear();*/
    }
}