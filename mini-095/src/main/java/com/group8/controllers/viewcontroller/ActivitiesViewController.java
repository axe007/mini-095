package com.group8.controllers.viewcontroller;

import com.group8.controllers.ActivityController;
import com.group8.helper.UIHelper;
import com.group8.model.Activity;
import com.group8.model.Project;
import com.group8.model.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.*;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ActivitiesViewController implements Initializable {

    private static ActivityController activityController = new ActivityController();
    private static UIHelper uiHelper = new UIHelper();
    private List<Project> activitiesList = new ArrayList<>();

    @FXML
    private StackPane activitiesView;
    @FXML
    private BorderPane avBorderPane;
    @FXML
    private TableView tblActivities;
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
    private Button activitiesRefresh;
    @FXML
    private TextField activitiesSearch;
    @FXML
    private TableColumn<Activity, ObjectId> tblClmActivityId;
    @FXML
    private TableColumn<Activity, String> tblClmActivityName;
    @FXML
    private TableColumn<Activity, String> tblClmActivityDescription;
    @FXML
    private TableColumn<Activity, LocalDate> tblClmActivityStartDate;
    @FXML
    private TableColumn<Activity, LocalDate> tblClmActivityEndDate;
    @FXML
    private TableColumn<Activity, String> tblClmActivityPriority;
    @FXML
    private TableColumn<Activity, String> tblClmActivityType;
    @FXML
    private TableColumn<Activity, String> tblClmActivityStatus;


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
            loadActivitiesData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadActivitiesData() {

        //getting the full list of books from file
        List<Activity> activityList = activityController.getActivitiesList();
        ObservableList<Activity> viewActivities = (ObservableList<Activity>) FXCollections.observableArrayList(activityList);

        String activityType;

        tblClmActivityId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblClmActivityName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tblClmActivityDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tblClmActivityStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        tblClmActivityEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        tblClmActivityPriority.setCellValueFactory(new PropertyValueFactory<>("priority"));
        tblClmActivityType.setCellValueFactory(new PropertyValueFactory<>("activityType"));
        tblClmActivityStatus.setCellValueFactory(new PropertyValueFactory<>("activityStatus"));

        tblActivities.setItems(viewActivities);
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