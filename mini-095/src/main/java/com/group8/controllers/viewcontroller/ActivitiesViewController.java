package com.group8.controllers.viewcontroller;

import com.group8.controllers.ActivityController;
import com.group8.helper.UIHelper;
import com.group8.model.Activity;
import com.group8.model.Session;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.layout.*;
import javafx.util.Callback;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class ActivitiesViewController implements Initializable {

    private static ActivityController activityController = new ActivityController();
    private static UIHelper uiHelper = new UIHelper();
    public static List<Activity> activitiesList = new ArrayList<>();
    public static BooleanProperty isUpdated = new SimpleBooleanProperty();

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
        isUpdated.setValue(false);
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
        isUpdated.addListener((observable, oldValue, newValue) -> {
            // Only if completed
            if (newValue == true) {
                loadActivitiesData();
                uiHelper.loadProjectBreadcrumbs(projectBreadcrumb);
                isUpdated.setValue(false);
            }

        });
    }

    private void loadActivitiesData() {
        activitiesList = activityController.getActivitiesList();
        Collections.sort(activitiesList, new Comparator<Activity>() {
            public int compare(Activity o1, Activity o2) {
                return o1.getStartDate().compareTo(o2.getStartDate());
            }
        });
        ObservableList<Activity> viewActivities = (ObservableList<Activity>) FXCollections
                .observableArrayList(activitiesList);

        tblClmActivityId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblClmActivityName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tblClmActivityDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tblClmActivityStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        tblClmActivityEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        tblClmActivityPriority.setCellValueFactory(new PropertyValueFactory<>("priority"));
        tblClmActivityPriority.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Activity, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Activity, String> p) {
                        Double priority = p.getValue().getPriority();
                        if (priority == 1.0) {
                            return new SimpleStringProperty("Very low");
                        } else if (priority == 2.0) {
                            return new SimpleStringProperty("Low");
                        } else if (priority == 3.0) {
                            return new SimpleStringProperty("Normal");
                        } else if (priority == 4.0) {
                            return new SimpleStringProperty("High");
                        } else if (priority == 5.0) {
                            return new SimpleStringProperty("Urgent");
                        } else {
                            return new SimpleStringProperty("<no value>");
                        }
                    }
                });

        tblClmActivityType.setCellValueFactory(new PropertyValueFactory<>("activityType"));
        tblClmActivityStatus.setCellValueFactory(new PropertyValueFactory<>("activityStatus"));
        tblClmActivityStatus.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Activity, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Activity, String> p) {
                        String status = p.getValue().getActivityStatus();
                        if (status.equals("TODO")) {
                            return new SimpleStringProperty("To do");
                        } else if (status.equals("INPROGRESS")) {
                            return new SimpleStringProperty("In Progress");
                        } else if (status.equals("REVIEW")) {
                            return new SimpleStringProperty("Review");
                        } else if (status.equals("DONE")) {
                            return new SimpleStringProperty("Done");
                        } else {
                            return new SimpleStringProperty("<no value>");
                        }
                    }
                });

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
            uiHelper.alertDialogGenerator(activitiesView, "error", requestedViewTitle,
                    "No project has been opened.\nPlease open a project in Projects window.");
        } else {
            isProjectOpen = true;
        }
        return isProjectOpen;
    }

    protected List<Activity> getActivitiesList() {
        return activitiesList;
    }
}