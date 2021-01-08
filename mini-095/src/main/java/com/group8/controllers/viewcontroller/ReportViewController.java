package com.group8.controllers.viewcontroller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import com.group8.controllers.ActivityController;
import com.group8.controllers.ProjectController;
import com.group8.controllers.SprintController;
import com.group8.controllers.UserController;
import com.group8.helper.UIHelper;
import com.group8.model.*;
import com.group8.model.TimeTrackingRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Cell;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.bson.types.ObjectId;

public class ReportViewController implements Initializable {
    @FXML
    private StackPane reportStackPane;
    @FXML
    private BorderPane reportBorderPane;
    @FXML
    private GridPane reportGrid;
    @FXML
    private TabPane reportTabPane;
    @FXML
    private AnchorPane timeTrackingTable;
    @FXML
    private GridPane projectBreadcrumb;

    @FXML
    private TableView<TimeTrackingRecord> timeTrackingTableView;

    private static ProjectController projectController = new ProjectController();
    private static ActivityController activityController = new ActivityController();
    private static SprintController sprintController = new SprintController();
    private static UserController userController = new UserController();
    private static UIHelper uiHelper = new UIHelper();
    private static ArrayList<User> usersList = new ArrayList<>();
    private static ArrayList<Activity> activitiesList = new ArrayList<>();
    private static ArrayList<TimeLog> timeLogList = new ArrayList<>();
    public static ArrayList<TimeTrackingRecord> timeTrackingReport = new ArrayList<>();

    // Report table list
    public final List<ReportViewController.Column<?>> timeTrackingReportColumns = new ArrayList<>(Arrays.asList(
            new ReportViewController.Column<String>(String.class, "activityType", "Type"),
            new ReportViewController.Column<String>(String.class, "activityName", "Activity name"),
            new ReportViewController.Column<String>(String.class, "userFullname", "User name"),
            new ReportViewController.Column<String>(String.class, "userRole", "User role"),
            new ReportViewController.Column<Double>(Double.class, "estimatedEffort", "Estimated hours"),
            new ReportViewController.Column<Double>(Double.class, "totalHours", "Total hours"),
            new ReportViewController.Column<String>(String.class, "activityStatus", "Status")
    ));



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        try {
            uiHelper.loadProjectBreadcrumbs(projectBreadcrumb);
            loadLists();
            loadTableData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadLists() {
        ObjectId projectId = Session.getOpenProjectId();
        if (projectId != null) {
            ArrayList<String> projectUsers = projectController.getProjectUsernameList(projectId);
            usersList.clear();
            timeLogList.clear();
            activitiesList.clear();
            timeTrackingReport.clear();

            usersList = userController.getUserList();
            timeLogList = userController.retrieveTimeLogList();
            activitiesList = activityController.getActivitiesList();
            System.out.println(activitiesList);

            String activityType = "n/a";
            String activityName = "n/a";
            ObjectId activityUserId;
            String userFullname = "n/a";
            String userRole = "n/a";
            String activityStatus = "n/a";
            double totalHours = 0.0;

            for (Activity activity : activitiesList) {
                double estimatedEffort = 0.0;
                ObjectId activityId = activity.getId();
                if (activity instanceof UserStory) {
                    activityType = "User story";
                    estimatedEffort = ((UserStory) activity).getStoryPoints();
                }
                else if (activity instanceof Task) {
                    activityType = "Task";
                    estimatedEffort = ((Task) activity).getEstimatedHours();
                }
                else if (activity instanceof Bug) {
                    activityType = "Bug";
                    estimatedEffort = ((Bug) activity).getEstimatedHours();
                }
                activityName = activity.getName();
                ArrayList<ObjectId> activityUsers = activity.getAssigneeList();
                activityStatus = activity.getSimpleStatus();

                // Skip if no user is assigned
                if (activityUsers != null) {
                    // Loop for each report record
                    for (ObjectId projectUser : activityUsers) {
                        System.out.println(projectUser);
                        // Lookup user details
                        for (User systemUser : usersList) {
                            if (systemUser.getId().equals(projectUser)) {
                                userFullname = systemUser.getFullname();
                                userRole = systemUser.getUserRole();
                            }
                        }
                        // Lookup time log projectUser
                        totalHours = 0.0;
                        for (TimeLog timelog : timeLogList) {
                            System.out.println(activityId);

                            if (timelog.getActivityId().equals(activityId) && timelog.getUserId().equals(projectUser)) {
                                System.out.println(timelog.getHours());
                                double itemHour = timelog.getHours();
                                totalHours = totalHours + itemHour;
                            }
                        }
                    }
                    // Create time tracking record item
                    timeTrackingReport.add(new TimeTrackingRecord(activityType, activityName, userFullname, userRole, estimatedEffort, totalHours, activityStatus));
                }
            }
        }
    }

    @FXML
    private void loadTableData() {

        timeTrackingTableView = createTable(timeTrackingReportColumns, timeTrackingReport);
        timeTrackingTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        timeTrackingTableView.setPrefWidth(1080.0);
        timeTrackingTableView.setMaxWidth(1280.0);
        timeTrackingTable.getChildren().add(timeTrackingTableView);
    }

    public class Column<T> {
        private Class<T> type;
        private String fieldName;
        private String title;

        public Column(Class<T> type, String fieldName, String title) {
            this.type = type;
            this.fieldName = fieldName;
            this.title = title;
        }

        public Class<T> getType() { return type; }
        public String getFieldName() { return fieldName; }
        public String getTitle() { return title; }

        public void setType(Class<T> type) { this.type = type; }
        public void setFieldName(String fieldName) { this.fieldName = fieldName; }
        public void setTitle(String title) { this.title = title; }
    }

    public <E> TableView<E> createTable(List<ReportViewController.Column<?>> columns, List<E> data) {
        TableView<E> table = new TableView<>();
        ObservableList<E> reportRecords = FXCollections.observableArrayList(data);
        for (ReportViewController.Column<?> column : columns) {
            table.getColumns().add(createColumn(column));
        }
        table.setItems(reportRecords);
        return table;
    }

    public <E, C> TableColumn<E, C> createColumn(ReportViewController.Column<?> column, C type) {
        TableColumn<E, C> tableColumn = new TableColumn<E, C>(column.getTitle());
        tableColumn.setCellValueFactory(new PropertyValueFactory<E, C>(column.getFieldName()));
        return tableColumn;
    }

    public <E> TableColumn<E, ?> createColumn(ReportViewController.Column<?> column) {
        switch (column.getType().getCanonicalName()) {
            case "java.lang.Integer":
                return createColumn(column, Integer.class);
            case "java.lang.Double":
                return createColumn(column, Double.class);
            case "java.lang.String":
            default:
                return createColumn(column, String.class);
        }
    }
}