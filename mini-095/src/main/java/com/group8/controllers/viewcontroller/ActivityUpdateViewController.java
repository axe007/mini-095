package com.group8.controllers.viewcontroller;

import com.group8.controllers.ActivityController;
import com.group8.helper.UIHelper;
import com.group8.model.*;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class ActivityUpdateViewController implements Initializable {

    private static ActivityController activityController = new ActivityController();
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
    private Label estimatedHours;
    @FXML
    private Label storyPoints;
    @FXML
    private TextArea noteContent;
    @FXML
    private ComboBox<String> newStatusCombo;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    @FXML
    private void handleSaveActivityBtn(ActionEvent event) throws IOException {
        // clear all text field
        String noteContentText = "";
        String newStatusText;
        String newStatusSet = "TODO";
        String alertHeading = "Update activity status";
        String alertContent = "Activity status successfully updated.";
        Activity activity = (Activity) Session.getOpenItem();
        ObjectId activityId = activity.getId();

        if (newStatusCombo.getValue() == null) {
            uiHelper.alertDialogGenerator(dialogPane, "error", alertHeading,
                    "You did not select new status.\nPlease select activity's new status and try again.");
            return;
        } else {
            newStatusText = newStatusCombo.getValue();
        }

        System.out.println("Note text: " + noteContent.getText());

        switch (newStatusText) {
            case "To Do" -> newStatusSet = "TODO";
            case "In Progress" -> newStatusSet = "INPROGRESS";
            case "Review" -> newStatusSet = "REVIEW";
            case "Done" -> newStatusSet = "DONE";
        }

        activityController.updateActivityStatus(activityId, newStatusSet);

        Optional<ButtonType> result = uiHelper.alertDialogGenerator(dialogPane, "success", alertHeading, alertContent);
        if (result.get() == ButtonType.OK) {
            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();
        }
        ActivitiesViewController.isUpdated.setValue(true);

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
        double storyPoints = 0.0;
        double estimatedHours = 0.0;
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

        ArrayList<String> statusStrings = new ArrayList<>(Arrays.asList("TODO", "INPROGRESS", "REVIEW", "DONE"));
        for (String status : statusStrings) {
            if (activity.getActivityStatus().equals(status)) {
                currentStatus.setText(activity.getSimpleStatus(status));
                currentStatus.setStyle("-fx-font-weight: 800");
            } else {
                newStatusCombo.getItems().add(activity.getSimpleStatus(status));
                newStatusCombo.setPromptText("Select here ...");
            }
        }

        this.activityName.setText(activityName);
        this.parentItems.setText(grandName + parentName);

        description.setText(activity.getDescription());
        startDate = activity.getStartDate();
        endDate = activity.getEndDate();
    }
}
