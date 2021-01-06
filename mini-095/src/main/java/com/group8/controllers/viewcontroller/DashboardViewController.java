package com.group8.controllers.viewcontroller;

import com.group8.controllers.ActivityController;
import com.group8.controllers.ProjectController;
import com.group8.controllers.SprintController;
import com.group8.controllers.UserController;
import com.group8.helper.UIHelper;
import com.group8.model.*;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.*;
import org.bson.types.ObjectId;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static javafx.geometry.Pos.CENTER;
import static javafx.geometry.Pos.TOP_CENTER;

public class DashboardViewController implements Initializable {

    @FXML
    private GridPane projectBreadcrumb;
    @FXML
    private StackPane dashboardView;
    @FXML
    private TitledPane userInfo;
    @FXML
    private TitledPane projectInfo;
    @FXML
    private AnchorPane projectInfoContent;
    @FXML
    private TitledPane sprintInfo;
    @FXML
    private AnchorPane sprintInfoContent;
    @FXML
    private TitledPane activitiesInfo;
    @FXML
    private AnchorPane activitiesInfoContent;

    @FXML
    private Label userRole;
    @FXML
    private Label userProjects;
    @FXML
    private Label userActivities;
    @FXML
    private Label userHoursProjects;
    @FXML
    private Label userHoursCurrentProject;

    private static ProjectController projectController = new ProjectController();
    private static ActivityController activityController = new ActivityController();
    private static SprintController sprintController = new SprintController();
    private static UserController userController = new UserController();
    private static UIHelper uiHelper = new UIHelper();
    private static ArrayList<User> usersList = new ArrayList<>();
    private static ArrayList<Project> projectsList = new ArrayList<>();
    private static ArrayList<Sprint> sprintsList = new ArrayList<>();
    private static ArrayList<Activity> activitiesList = new ArrayList<>();
    private static ArrayList<TimeLog> timeLogList = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            uiHelper.loadProjectBreadcrumbs(projectBreadcrumb);
            loadLists();
            loadUserInfo();
            loadProjectInfo();
            loadSprintInfo();
            loadActivitiesInfo();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadLists() {
        usersList = userController.getUserList();
        projectsList = projectController.getProjectList();
        timeLogList = userController.retrieveTimeLogList();
        activitiesList = activityController.getFullActivitiesList();
        sprintsList = sprintController.getSprintList();

        ObjectId openProject = Session.getOpenProjectId();
        if (openProject != null) {
            // will do something later
        }
    }

    @FXML
    private void loadUserInfo() {
        ObjectId currentUserId = Session.getSessionUserId();
        ObjectId currentProjectId = Session.getOpenProjectId();
        System.out.println("Current user id:" + currentUserId);
        String userName;
        String fullName;
        String userRole = "";
        String numberOfProjects;
        String numberOfActivities;
        int userProjectCount = 0;
        int userActivityCount = 0;
        double userHoursProjects = 0.0;
        double userHoursCurrentProject = 0.0;

        // Load current user info
        for (User user : usersList) {
            if (user.getId().equals(currentUserId)) {
                userName = user.getUsername();
                userRole = user.getUserRole();

            }
        }

        for (Project project : projectsList) {
            ArrayList<ObjectId> projectUserList = project.getDeveloperTeam();
            for (ObjectId userId : projectUserList) {
                if (userId.equals(currentUserId)) {
                    userProjectCount = userProjectCount + 1;
                }
            }
            projectUserList.clear();
        }

        for (Activity activity : activitiesList) {
            ArrayList<ObjectId> activityUserList = activity.getAssigneeList();
            if (activityUserList != null) {
                for (ObjectId userId : activityUserList) {
                    if (userId.equals(currentUserId)) {
                        userActivityCount = userActivityCount + 1;
                    }
                }
                activityUserList.clear();
            }
        }

        for (TimeLog timelog : timeLogList) {
            if (timelog.getUserId().equals(currentUserId)) {
                userHoursProjects = userHoursProjects + timelog.getHours();
            }
            if (currentProjectId != null && timelog.getUserId().equals(currentUserId)) {
                userHoursCurrentProject = userHoursCurrentProject + timelog.getHours();
            }
        }

        // Finally set user stats to FXML Labels
        this.userRole.setText(userRole);
        this.userProjects.setText(String.valueOf(userProjectCount));
        this.userActivities.setText(String.valueOf(userActivityCount));
        this.userHoursProjects.setText(String.valueOf(userHoursProjects) + " hours");
        this.userHoursCurrentProject.setText(String.valueOf(userHoursCurrentProject)  + " hours");
    }

    @FXML
    private void loadProjectInfo() {
        ObjectId currentProjectId = Session.getOpenProjectId();
        {
            if (currentProjectId == null) {
                projectInfo.setPrefHeight(120.0);
                projectInfoContent.getChildren().removeAll();
                VBox vbox = noProjectOpenLabel();
                projectInfoContent.getChildren().add(vbox);
            } else {
                // Show project info

            }
        }
    }

    @FXML
    private void loadSprintInfo() {
        ObjectId currentProjectId = Session.getOpenProjectId();
        {
            if (currentProjectId == null) {
                sprintInfo.setPrefHeight(120.0);
                sprintInfoContent.getChildren().removeAll();
                VBox vbox = noProjectOpenLabel();
                sprintInfoContent.getChildren().add(vbox);
            } else {
                // Show project info

            }
        }
    }

    @FXML
    private void loadActivitiesInfo() {
        ObjectId currentProjectId = Session.getOpenProjectId();
        {
            if (currentProjectId == null) {
                activitiesInfo.setPrefHeight(120.0);
                activitiesInfoContent.getChildren().removeAll();
                VBox vbox = noProjectOpenLabel();
                activitiesInfoContent.getChildren().add(vbox);
            } else {
                // Show project info

            }
        }
    }

    @FXML
    private VBox noProjectOpenLabel() {
        Label message = new Label("No project open yet");
        VBox vbox = new VBox();
        vbox.setFillWidth(true);
        vbox.setPrefHeight(80.0);
        vbox.setAlignment(CENTER);
        HBox hbox = new HBox();
        hbox.setFillHeight(true);
        hbox.setPrefWidth(450.0);
        hbox.setAlignment(CENTER);
        vbox.getChildren().add(hbox);
        hbox.getChildren().add(message);

        return vbox;
    }
}