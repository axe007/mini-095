package com.group8.controllers.viewcontroller;

import com.group8.controllers.ActivityController;
import com.group8.controllers.NoteController;
import com.group8.controllers.UserController;
import com.group8.helper.UIHelper;
import com.group8.model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ActivityTimeLogViewController implements Initializable {

    private static ActivityController activityController = new ActivityController();
    private static UserController userController = new UserController();
    private static UIHelper uiHelper = new UIHelper();
    private static ArrayList<Activity> activitiesList = new ArrayList<>();

    @FXML
    private StackPane dialogPane;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label windowModeTitle;
    @FXML
    private Text activityName;
    @FXML
    private Text parentItems;
    @FXML
    private Text activityDates;
    @FXML
    private Text activityEstimate;
    @FXML
    private Text activityPriority;
    @FXML
    private Text description;
    @FXML
    private Text currentStatus;
    @FXML
    private VBox currentStatusPane;
    @FXML
    private Label estimatedHours;
    @FXML
    private Label storyPoints;
    @FXML
    private TextField timeLogHours;
    @FXML
    private TextArea noteContent;
    @FXML
    private DatePicker createdDate;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    @FXML
    private void handleSaveActivityBtn(ActionEvent event) throws IOException {
        // clear all text field
        // String noteContentText = "";
        // String newStatusText;
        // String newStatusSet = "TODO";
        String alertHeading = "Activity time log";
        String alertContent = "Time log successfully recorded.";
        Activity activity = (Activity) Session.getOpenItem();
        ObjectId activityId;
        activityId = activity.getId();

        ObjectId projectId;
        ObjectId sprintId;
        ObjectId userId;
        LocalDate createdDate;
        double timeLogHours;

        projectId = Session.getOpenProjectId();
        sprintId = Session.getCurrentSprintId();
        userId = Session.getSessionUserId();

        String noteContent = this.noteContent.getText();

        if (!noteContent.isEmpty() || !noteContent.equals("")) {
            UserController userController = new UserController();
            String userName = userController.getUserDetail(userId, "fullname");
            String activityName = this.activityName.getText();
            LocalDate createDate = LocalDate.now();

            Note newNote = new Note(projectId, activityId, activityName, "Activity Note", userId, userName, createDate, "Time log notes", noteContent);

            NoteController noteController = new NoteController();
            noteController.createNote(newNote);
        }

        try {
            timeLogHours = Double.parseDouble(this.timeLogHours.getText());
        } catch (Exception e) {
            uiHelper.alertDialogGenerator(dialogPane, "error", alertHeading,
                    "You need to enter numbers (2.5) for hours.\nPlease check the hours and try again.");
            this.timeLogHours.getStyleClass().add("textfield-error-highlight");
            this.timeLogHours.requestFocus();
            return;
        }

        if (this.createdDate.getValue().equals(null) || this.createdDate.getValue().toString().equals("")) {
            uiHelper.alertDialogGenerator(dialogPane, "error", alertHeading,
                    "You need to specify the created date.\nPlease check the date and try again.");
            this.createdDate.getStyleClass().add("textfield-error-highlight");
            this.createdDate.requestFocus();
            return;
        } else {
            createdDate = this.createdDate.getValue();
        }

        userController.recordTimeLog(projectId, sprintId, activityId, userId, timeLogHours, createdDate);
        Optional<ButtonType> result = uiHelper.alertDialogGenerator(dialogPane, "success", alertHeading, alertContent);
        if (result.get() == ButtonType.OK) {
            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();
        }
        ScrumboardViewController.isUpdated.setValue(true);
    }

    @FXML
    private void handleCancel(ActionEvent event) throws IOException {
        // TODO
        Stage stage;
        stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        activitiesList.clear();
        activitiesList = activityController.getActivitiesList();

        String activityName;
        LocalDate startDate;
        LocalDate endDate;
        // double storyPoints = 0.0;
        // double estimatedHours = 0.0;
        double priority;
        ObjectId grandId = null;
        ObjectId parentId = null;

        Activity activity = (Activity) Session.getOpenItem();
        activityName = activity.getName();

        startDate = activity.getStartDate();
        endDate = activity.getEndDate();
        String startDateText = startDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
        String endDateText = endDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
        activityDates.setText(startDateText + " - " + endDateText);

        if (activity instanceof UserStory) {
            this.estimatedHours.setVisible(false);
            this.activityEstimate.setText(String.valueOf(((UserStory) activity).getStoryPoints()) + " story points");
        } else if (activity instanceof Task) {
            this.storyPoints.setVisible(false);
            this.activityEstimate.setText(String.valueOf(((Task) activity).getEstimatedHours()) + " hours");
            grandId = ((Task) activity).getGrandId();
            parentId = ((Task) activity).getParentId();
        } else {
            this.storyPoints.setVisible(false);
            this.activityEstimate.setText(String.valueOf(((Bug) activity).getEstimatedHours()) + " hours");
            grandId = ((Bug) activity).getGrandId();
            parentId = ((Bug) activity).getParentId();
        }

        String grandName = "";
        String parentName = "None";
        for (Activity parentActivity : activitiesList) {
            if (grandId != null) {
                if (grandId.equals(parentActivity.getId())) {
                    grandName = parentActivity.getName();
                    if (grandName.length() > 23) {
                        grandName = grandName.substring(0, Math.min(grandName.length(), 22)) + "...";
                    }
                    grandName = grandName + " / ";
                }
            }
            if (parentId != null) {
                if (parentId.equals(parentActivity.getId())) {
                    parentName = parentActivity.getName();
                    if (grandId != null && parentName.length() > 23) {
                        parentName = parentName.substring(0, Math.min(parentName.length(), 22)) + "...";
                    }
                }
            }
        }

        priority = activity.getPriority();
        if ((int) priority == 1) {
            activityPriority.setText("Very low");
            activityPriority.setStyle(
                    "-fx-font-weight: 800; -fx-fill: #DDD; -fx-effect: dropshadow(one-pass-box, #4c4c4c, 1, 0, 1, 1);");
        } else if ((int) priority == 2) {
            activityPriority.setText("Low");
            activityPriority.setStyle(
                    "-fx-font-weight: 800;-fx-fill: #cbea96; -fx-effect: dropshadow(one-pass-box, #6d767e, 1, 0, 1, 1);");
        } else if ((int) priority == 3) {
            activityPriority.setText("Normal");
            activityPriority.setStyle(
                    "-fx-font-weight: 800;-fx-fill: #41b337; -fx-effect: dropshadow(one-pass-box, #6d767e, 1, 0, 1, 1);");
        } else if ((int) priority == 4) {
            activityPriority.setText("High");
            activityPriority.setStyle(
                    "-fx-font-weight: 800;-fx-fill: #ffe56b; -fx-effect: dropshadow(one-pass-box, #4c4c4c, 1, 0, 1, 1);");
        } else {
            activityPriority.setText("Urgent");
            activityPriority.setStyle(
                    "-fx-font-weight: 800;-fx-fill: #E56767; -fx-text-fill: #E56767; -fx-effect: dropshadow(one-pass-box,#ddd, 1, 0, 1, 1);");
        }

        String status = activity.getSimpleStatus(activity.getActivityStatus());

        if (status.equals("To Do")) {
            currentStatusPane.getStyleClass().add("scrum-column-header-todo");
            currentStatusPane.setPrefWidth(58);
        } else if (status.equals("In Progress")) {
            currentStatusPane.getStyleClass().add("scrum-column-header-inprogress");
            currentStatusPane.setPrefWidth(87);
        } else if (status.equals("Review")) {
            currentStatusPane.getStyleClass().add("scrum-column-header-review");
            currentStatusPane.setPrefWidth(62);
        } else if (status.equals("Done")) {
            currentStatusPane.getStyleClass().add("scrum-column-header-done");
            currentStatusPane.setPrefWidth(52);
        }
        currentStatus.setText(status);
        // currentStatus.getStyleClass().add("scrum-activity-status");
        this.activityName.setText(activityName);
        this.parentItems.setText(grandName + parentName);

        description.setText(activity.getDescription());

        LocalDate projectStartDate = Session.getProjectStartDate();
        LocalDate projectEndDate = Session.getProjectEndDate();

        Callback<DatePicker, DateCell> projectDates = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setDisable((empty || item.compareTo(projectStartDate) < 0)
                                || (empty || item.compareTo(projectEndDate) > 0));
                    }

                };
            }
        };

        this.createdDate.setDayCellFactory(projectDates);
        this.createdDate.setValue(startDate);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                timeLogHours.requestFocus();
            }
        });
    }
}
