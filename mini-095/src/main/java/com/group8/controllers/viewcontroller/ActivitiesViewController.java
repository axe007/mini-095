package com.group8.controllers.viewcontroller;

import com.group8.controllers.ActivityController;
import com.group8.helper.UIHelper;
import com.group8.model.Activity;
import com.group8.model.Project;
import com.group8.model.Session;
import com.group8.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ActivitiesViewController implements Initializable {

    private static ActivityController activityController = new ActivityController();
    private static UIHelper uiHelper = new UIHelper();
    protected static List<Activity> activitiesList = new ArrayList<>();

    @FXML
    private StackPane activitiesView;
    @FXML
    private BorderPane avBorderPane;
    @FXML
    private TableView tblActivities;
    @FXML
    private Button activityNewButton;
    @FXML
    private Button activityEditButton;
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
    private GridPane projectBreadcrumb;
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
        } else if (event.getSource() == activityEditButton) {
            // Edit activity details
            Activity activity = (Activity) tblActivities.getSelectionModel().getSelectedItem();
            Session.setSetOpenItem(activity);
            if (activity == null) {
                uiHelper.alertDialogGenerator(activitiesView, "error", "Edit activity",
                        "No activity was selected.\nPlease select an activity and try again.");
            } else {
                Session.setWindowMode("edit");
                uiHelper.loadWindow("ActivityAddView", activityEditButton, "Edit activity details");
            }

        } else if (event.getSource() == activityAssignButton) {
            // Edit activity details
            Activity activity = (Activity) tblActivities.getSelectionModel().getSelectedItem();
            if (activity == null) {
                uiHelper.alertDialogGenerator(activitiesView, "error", "Assign activity",
                        "No activity was selected.\nPlease select an activity and try again.");
            } else {
                Session.setSetOpenItem(activity);
                uiHelper.loadWindow("ActivityAssignView", activityEditButton, "Edit activity details");
            }

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
            uiHelper.loadProjectBreadcrumbs(projectBreadcrumb);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadActivitiesData() {

        Task<List<Activity>> loadDataTask = new Task<List<Activity>>() {
            @Override
            protected List<Activity> call() throws Exception {
                activitiesList = activityController.getActivitiesList();

                Collections.sort(activitiesList, new Comparator<Activity>() {
                    public int compare(Activity o1, Activity o2) {
                        return o1.getStartDate().compareTo(o2.getStartDate());
                    }
                });

                return activitiesList;
            }
        };

        loadDataTask.setOnSucceeded(e -> tblActivities.getItems().setAll(loadDataTask.getValue()));
        loadDataTask.setOnFailed(e -> { /* handle errors... */ });

        ProgressIndicator progressIndicator = new ProgressIndicator();
        tblActivities.setPlaceholder(progressIndicator);

        Thread loadDataThread = new Thread(loadDataTask);
        loadDataThread.start();

        ObservableList<Activity> viewActivities = (ObservableList<Activity>) FXCollections.observableArrayList(activitiesList);

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
        loadActivitiesData();
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

    protected List<Activity> getActivitiesList() {
        return activitiesList;
    }
}