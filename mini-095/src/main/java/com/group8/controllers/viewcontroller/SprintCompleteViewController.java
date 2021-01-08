package com.group8.controllers.viewcontroller;

import com.group8.controllers.ActivityController;
import com.group8.controllers.ProjectController;
import com.group8.controllers.SprintController;
import com.group8.helper.UIHelper;
import com.group8.model.Activity;
import com.group8.model.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class SprintCompleteViewController implements Initializable {

    private static ProjectController projectController = new ProjectController();
    private static ActivityController activityController = new ActivityController();
    private static SprintController sprintController = new SprintController();

    private ObservableList<String> ongoingActivities = FXCollections.observableArrayList();
    private ObservableList<String> completeActivities = FXCollections.observableArrayList();

    @FXML
    private StackPane dialogPane;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label windowModeTitle;
    @FXML
    private Text currentSprintName;
    @FXML
    private Text sprintStartDate;
    @FXML
    private Text sprintEndDate;
    @FXML
    private TextArea sprintNoteContent;

    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    @FXML
    private ListView<String> ongoingListView;
    @FXML
    private ListView<String> completeListView;

    @FXML
    private void handleSaveBtn(ActionEvent event) throws IOException {
        String alertHeading = "Complete a sprint";
        String alertContent = "The sprint has been marked as complete and \nongoing activities are assigned to project backlog.";
        // String sprintNotes = this.sprintNoteContent.getText();
        ObjectId currentSprintId = Session.getCurrentSprintId();
        sprintController.completeSprint(currentSprintId); // Mark this sprint as Complete

        List<String> ongoingActivities = ongoingListView.getItems();
        activityController.updateActivitySprint(ongoingActivities, null);
        Session.setCurrentSprintId(null);
        projectController.completeSprint();

        UIHelper uiHelper = new UIHelper();
        Optional<ButtonType> result = uiHelper.alertDialogGenerator(dialogPane, "success", alertHeading, alertContent);
        if (result.get() == ButtonType.OK) {
            ScrumboardViewController.isUpdated.setValue(true);
            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();
        }
        ScrumboardViewController.isUpdated.setValue(true);
    }

    @FXML
    private void handleCancel(ActionEvent event) throws IOException {
        Stage stage;
        stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleAssignButton(ActionEvent event) {
        String activity = (String) ongoingListView.getSelectionModel().getSelectedItem();
        if (activity != null) {
            ongoingListView.getSelectionModel().clearSelection();
            ongoingActivities.remove(activity);
            completeActivities.add(activity);
        }
    }

    @FXML
    private void handleUnassignButton(ActionEvent event) {
        String activity = (String) completeListView.getSelectionModel().getSelectedItem();
        if (activity != null) {
            completeListView.getSelectionModel().clearSelection();
            completeActivities.remove(activity);
            ongoingActivities.add(activity);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObjectId sprintId = Session.getCurrentSprintId();
        setListViews();

        String sprintName = sprintController.getSprintName(sprintId);
        this.currentSprintName.setText(sprintName);
        generateSprintDates();

        ongoingListView.setItems(ongoingActivities);
        completeListView.setItems(completeActivities);
        ongoingListView.setMouseTransparent(true);
        ongoingListView.setFocusTraversable(false);
        completeListView.setMouseTransparent(true);
        completeListView.setFocusTraversable(false);

        if (completeListView.getItems() == null) {
            completeListView.setPlaceholder(new Text("No activities completed yet"));
        }
    }

    private void setListViews() {
        // Get current sprint activities
        ObjectId sprintId = Session.getCurrentSprintId();
        ArrayList<Activity> sprintList = activityController.getSprintActivities(sprintId);
        ArrayList<String> completeActivityNames = new ArrayList<>();
        ArrayList<String> ongoingActivityNames = new ArrayList<>();

        if (!sprintList.isEmpty()) {
            for (Activity activity : sprintList) {
                if (activity.getActivityStatus().equals("DONE")) {
                    String activityName = activity.getName();
                    completeActivityNames.add(activityName);
                } else {
                    String backlogName = activity.getName();
                    ongoingActivityNames.add(backlogName);
                }
            }
        }

        this.ongoingActivities.addAll(ongoingActivityNames);
        this.completeActivities.addAll(completeActivityNames);
    }

    private void generateSprintDates() {
        ObjectId sprintId = Session.getCurrentSprintId();
        LocalDate sprintStartDate = sprintController.getSprintDate(sprintId, "startDate");
        LocalDate sprintEndDate = sprintController.getSprintDate(sprintId, "endDate");
        String startDateText = sprintStartDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
        String endDateText = sprintEndDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
        this.sprintStartDate.setText(startDateText);
        this.sprintEndDate.setText(endDateText);
    }
}