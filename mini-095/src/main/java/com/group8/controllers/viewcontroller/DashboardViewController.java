package com.group8.controllers.viewcontroller;

import com.group8.controllers.ActivityController;
import com.group8.controllers.ProjectController;
import com.group8.controllers.SprintController;
import com.group8.controllers.UserController;
import com.group8.helper.UIHelper;
import com.group8.model.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.*;
import org.bson.types.ObjectId;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static java.time.temporal.ChronoUnit.DAYS;
import static javafx.geometry.Pos.CENTER;

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

    // user stats text
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

    // project stats
    @FXML
    private Label sprintsCount;
    @FXML
    private Label activitiesCount;
    @FXML
    private Label usersCount;
    @FXML
    private Label projectDuration;
    @FXML
    private Label totalHoursCurrentProject;
    @FXML
    private GridPane projectInfoGrid;

    // sprint stats
    @FXML
    private Label todoItems;
    @FXML
    private Label inprogressItems;
    @FXML
    private Label reviewItems;
    @FXML
    private Label doneItems;
    @FXML
    private Label totalHoursCurrentSprint;
    @FXML
    private GridPane sprintInfoGrid;

    // activities stats
    @FXML
    private Label numberOfActivities;
    @FXML
    private Label numberOfBacklogItems;
    @FXML
    private Label numberOfUserStories;
    @FXML
    private Label numberOfTasks;
    @FXML
    private Label numberOfBugs;
    /*@FXML
    private Label totalHoursCurrentSprint;*/
    @FXML
    private GridPane activitiesInfoGrid;


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
        double totalHoursCurrentProject = 0.0;

        {
            if (currentProjectId == null) {
                projectInfo.setPrefHeight(120.0);
                projectInfoContent.getChildren().remove(projectInfoGrid);
                VBox vbox = noProjectOpenLabel();
                projectInfoContent.getChildren().add(vbox);
            } else {
                // Show project info
                LocalDate startDate = Session.getProjectStartDate();
                LocalDate endDate = Session.getProjectEndDate();
                long daysBetween = DAYS.between(startDate, endDate);

                ArrayList<String> projectDeveloperList = projectController.getProjectUsernameList(currentProjectId);
                ArrayList<ObjectId> projectSprintList = new ArrayList<>();
                ArrayList<ObjectId> projectActivitiesList = new ArrayList<>();

                for (Project project : projectsList) {
                    if (project.getId().equals(currentProjectId)) {
                        projectSprintList = project.getSprints();
                        projectActivitiesList = project.getActivities();
                    }
                }

                for (TimeLog timelog : timeLogList) {
                    if (timelog.getProjectId().equals(currentProjectId)) {
                        totalHoursCurrentProject = totalHoursCurrentProject + timelog.getHours();
                    }
                }
                this.sprintsCount.setText(String.valueOf(projectSprintList.size()));
                this.activitiesCount.setText(String.valueOf(projectActivitiesList.size()));
                this.usersCount.setText(String.valueOf(projectDeveloperList.size()) + " users");
                this.projectDuration.setText(String.valueOf(daysBetween) + " days");
                this.totalHoursCurrentProject.setText(String.valueOf(totalHoursCurrentProject) + " hours");
            }
        }
    }

    @FXML
    private void loadSprintInfo() {
        ObjectId currentProjectId = Session.getOpenProjectId();
        {
            if (currentProjectId == null) {
                sprintInfo.setPrefHeight(120.0);
                sprintInfoContent.getChildren().remove(sprintInfoGrid);
                VBox vbox = noProjectOpenLabel();
                sprintInfoContent.getChildren().add(vbox);
            } else {
                // Show project info
                ObjectId sprintId = Session.getCurrentSprintId();
                ArrayList<Activity> sprintActivitiesList = activityController.getSprintActivities(sprintId);
                int todoCount = 0;
                int inprogressCount = 0;
                int reviewCount = 0;
                int doneCount = 0;
                double totalHoursCurrentSprint = 0.0;


                for (Activity activity : sprintActivitiesList) {
                    if (activity.getActivityStatus().equals("TODO")) {
                        todoCount = todoCount + 1;
                    } else if (activity.getActivityStatus().equals("INPROGRESS")) {
                        inprogressCount = inprogressCount + 1;
                    } else if (activity.getActivityStatus().equals("REVIEW")) {
                        reviewCount = reviewCount + 1;
                    } else if (activity.getActivityStatus().equals("DONE")) {
                        doneCount = doneCount + 1;
                    }
                }

                for (TimeLog timelog : timeLogList) {
                    if (timelog.getSprintId().equals(sprintId)) {
                        totalHoursCurrentSprint = totalHoursCurrentSprint + timelog.getHours();
                    }
                }

                this.todoItems.setText(String.valueOf(todoCount));
                this.inprogressItems.setText(String.valueOf(inprogressCount));
                this.reviewItems.setText(String.valueOf(reviewCount));
                this.doneItems.setText(String.valueOf(doneCount));
                this.totalHoursCurrentSprint.setText(String.valueOf(totalHoursCurrentSprint) + " hours");
            }
        }
    }

    @FXML
    private void loadActivitiesInfo() {
        ObjectId currentProjectId = Session.getOpenProjectId();
        {
            if (currentProjectId == null) {
                activitiesInfo.setPrefHeight(120.0);
                activitiesInfoContent.getChildren().remove(activitiesInfoGrid);
                VBox vbox = noProjectOpenLabel();
                activitiesInfoContent.getChildren().add(vbox);
            } else {
                // Show project info
                int activityCount = 0;
                int backlogCount = 0;
                int userStoryCount = 0;
                int taskCount = 0;
                int bugCount = 0;

                for (Activity activity : activitiesList) {
                    activityCount = activityCount + 1;
                    if (activity instanceof UserStory) {
                        userStoryCount = userStoryCount + 1;
                    } else if (activity instanceof Task) {
                        taskCount = taskCount + 1;
                    } else if (activity instanceof Bug) {
                        bugCount = bugCount + 1;
                    }
                    if (activity.getSprintId() == null) {
                        backlogCount = backlogCount + 1;
                    }
                }

                this.activitiesCount.setText(String.valueOf(activityCount));
                this.numberOfBacklogItems.setText(String.valueOf(backlogCount));
                this.numberOfUserStories.setText(String.valueOf(userStoryCount));
                this.numberOfTasks.setText(String.valueOf(taskCount));
                this.numberOfBugs.setText(String.valueOf(bugCount));

            }
        }
    }

    @FXML
    private VBox noProjectOpenLabel() {
        Label message = new Label("No project is open yet");
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