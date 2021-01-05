package com.group8.controllers.viewcontroller;

import com.group8.controllers.ActivityController;
import com.group8.controllers.ProjectController;
import com.group8.controllers.SprintController;
import com.group8.controllers.UserController;
import com.group8.helper.UIHelper;
import com.group8.model.Activity;
import com.group8.model.Session;
import com.group8.model.Sprint;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

public class SprintAddViewController implements Initializable {

    private static ProjectController projectController = new ProjectController();
    private static ActivityController activityController = new ActivityController();
    private static SprintController sprintController = new SprintController();
    private static UIHelper uiHelper = new UIHelper();

    private ObservableList<String> backlogActivities = FXCollections.observableArrayList();
    private ObservableList<String> sprintActivities = FXCollections.observableArrayList();

    @FXML
    private StackPane dialogPane;
    @FXML
    private AnchorPane anchorPaneSprint;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField sprintName;
    @FXML
    private Label sprintNameEdit;
    @FXML
    private DatePicker sprintStartDate;
    @FXML
    private DatePicker sprintEndDate;
    @FXML
    private Label windowModeTitle;
    @FXML
    private Label sprintStartDateEdit;
    @FXML
    private Label sprintEndDateEdit;
    @FXML
    private Text sprintDuration;
    @FXML
    private ListView<String> unassignedListView;
    @FXML
    private ListView<String> assignedListView;


    @FXML
    private void handleSaveBtn(ActionEvent event) throws IOException {

        String alertHeading = "Create new sprint";
        String alertContent = "New sprint is created and activities are assigned.\nPlease reload the board.";
        UIHelper uiHelper = new UIHelper();

        String sprintName;
        LocalDate sprintStartDate;
        LocalDate sprintEndDate;
        ObjectId sprintId = null;

        if (Session.getWindowMode().equals("new")) {
            sprintName = this.sprintName.getText();
            sprintStartDate = this.sprintStartDate.getValue();
            sprintEndDate = this.sprintEndDate.getValue();
            sprintId = sprintController.createSprint(sprintName, sprintStartDate, sprintEndDate);
            Session.setCurrentSprintId(sprintId);
            projectController.updateSprints(sprintId);

        } else if (Session.getWindowMode().equals("edit")) {
            sprintId = Session.getCurrentSprintId();
            alertHeading = "Assigning activities";
            alertContent = "Activities are successfully assigned.\nPlease reload the board.";
        }

        List<String> unassignedActivities = unassignedListView.getItems();
        List<String> assignedActivities = assignedListView.getItems();

        // Update Activity's sprintId
        activityController.updateActivitySprint(assignedActivities, sprintId);
        activityController.updateActivitySprint(unassignedActivities, null);

        Optional<ButtonType> result = uiHelper.alertDialogGenerator(dialogPane,"success", alertHeading, alertContent);
        if (result.get() == ButtonType.OK) {
            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) throws IOException {
        Stage stage;
        stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleAssignButton(ActionEvent event) {
        String activity = (String) unassignedListView.getSelectionModel().getSelectedItem();
        if (activity != null) {
            unassignedListView.getSelectionModel().clearSelection();
            backlogActivities.remove(activity);
            sprintActivities.add(activity);
        }
    }

    @FXML
    private void handleUnassignButton(ActionEvent event) {
        String activity = (String) assignedListView.getSelectionModel().getSelectedItem();
        if (activity != null) {
            assignedListView.getSelectionModel().clearSelection();
            sprintActivities.remove(activity);
            backlogActivities.add(activity);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (Session.getWindowMode().equals("edit")) {
            windowModeTitle.setText("Current sprint activities");
        }
        setListViews();
        unassignedListView.setItems(backlogActivities);
        assignedListView.setItems(sprintActivities);

        if (assignedListView.getItems() == null) {
            assignedListView.setPlaceholder(new Text("No activities selected yet"));
        }
        generateSpringDatePickers(null, null);
    }

    private void setListViews() {
        ArrayList<Activity> backlogList = activityController.getBacklogList();
        ArrayList<String> backlogActivityNames = new ArrayList<>();
        for (Activity activity : backlogList) {
            String backlogName = activity.getName();
            backlogActivityNames.add(backlogName);
        }
        System.out.println("Backlog items:");
        System.out.println(backlogActivityNames);

        ObjectId sprintId = Session.getCurrentSprintId();
        ArrayList<Activity> sprintList = activityController.getSprintActivities(sprintId);
        ArrayList<String> sprintActivityNames = new ArrayList<>();
        if (!sprintList.isEmpty()) {
            for (Activity activity : sprintList) {
                String activityName = activity.getName();
                sprintActivityNames.add(activityName);
            }
        }

        System.out.println("Sprint items:");
        System.out.println(sprintActivityNames);

        this.sprintActivities.addAll(sprintActivityNames);
        if (sprintActivityNames != null) {
            backlogActivityNames.removeAll(sprintActivityNames);
        }

        this.backlogActivities.addAll(backlogActivityNames);
    }

    private void generateSpringDatePickers(LocalDate startDate, LocalDate endDate) {
        LocalDate projectStartDate = Session.getProjectStartDate();
        LocalDate projectEndDate = Session.getProjectEndDate();

        if (Session.getWindowMode().equals("new")) {
            Callback<DatePicker, DateCell> projectDates = new Callback<DatePicker, DateCell>() {
                @Override
                public DateCell call(final DatePicker param) {
                    return new DateCell() {
                        @Override
                        public void updateItem(LocalDate item, boolean empty) {
                            super.updateItem(item, empty);

                            setDisable((empty || item.compareTo(projectStartDate) < 0) || (empty || item.compareTo(projectEndDate) > 0));
                        }

                    };
                }
            };

            ObjectId projectId = Session.getOpenProjectId();
            ArrayList<Sprint> projectSprints = sprintController.getProjectSprintList(projectId);

            if (projectSprints.isEmpty() || projectSprints == null) {
                this.sprintStartDate.setValue(projectStartDate);
                this.sprintEndDate.setValue(projectStartDate.plusDays(13));
            } else {
                Collections.sort(projectSprints, new Comparator<Sprint>() {
                    public int compare(Sprint o1, Sprint o2) {
                        return o2.getEndDate().compareTo(o1.getEndDate());
                    }
                });

                Sprint lastSprint = projectSprints.get(0);
                LocalDate lastSprintEndDate = lastSprint.getEndDate();
                DayOfWeek day = lastSprintEndDate.getDayOfWeek();
                LocalDate sprintStartDate;
                LocalDate sprintEndDate;

                if (day == DayOfWeek.SATURDAY) {
                    sprintStartDate = lastSprintEndDate.plusDays(2);
                } else {
                    sprintStartDate = lastSprintEndDate.plusDays(1);
                }
                sprintEndDate = sprintStartDate.plusDays(13);
                if (sprintEndDate.compareTo(projectEndDate) > 0) {
                    sprintEndDate = projectEndDate;
                }

                this.sprintStartDate.setValue(sprintStartDate);
                this.sprintEndDate.setValue(sprintEndDate);
            }

            this.sprintStartDate.setDayCellFactory(projectDates);
            this.sprintEndDate.setDayCellFactory(projectDates);
            this.sprintStartDate.valueProperty().addListener((ov, oldValue, newValue) -> {
                this.sprintEndDate.setValue(newValue.plusDays(13));
            });
            this.sprintNameEdit.setVisible(false);
            this.sprintStartDateEdit.setVisible(false);
            this.sprintEndDateEdit.setVisible(false);


        } else if (Session.getWindowMode().equals("edit")) {
            ObjectId sprintId = Session.getCurrentSprintId();
            String sprintName = sprintController.getSprintName(sprintId);
            LocalDate sprintStartDate = sprintController.getSprintDate(sprintId, "startDate");
            LocalDate sprintEndDate = sprintController.getSprintDate(sprintId, "endDate");
            String startDateText = sprintStartDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
            String endDateText = sprintEndDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
            this.sprintName.setVisible(false);
            this.sprintStartDate.setVisible(false);
            this.sprintEndDate.setVisible(false);

            this.sprintNameEdit.setText(sprintName);
            this.sprintStartDateEdit.setText(startDateText);
            this.sprintEndDateEdit.setText(endDateText);
        }
    }
}