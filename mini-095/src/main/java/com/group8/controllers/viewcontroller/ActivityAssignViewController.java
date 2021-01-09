package com.group8.controllers.viewcontroller;

import com.group8.controllers.ActivityController;
import com.group8.controllers.ProjectController;
import com.group8.controllers.UserController;
import com.group8.helper.UIHelper;
import com.group8.model.Activity;
import com.group8.model.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
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

public class ActivityAssignViewController implements Initializable {

    private static UserController userController = new UserController();
    private static ProjectController projectController = new ProjectController();
    private static ActivityController activityController = new ActivityController();

    // Users
    private ObservableList<String> projectUsers = FXCollections.observableArrayList();
    private ObservableList<String> activityUsers = FXCollections.observableArrayList();

    @FXML
    private StackPane dialogPane;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Text activityName;
    @FXML
    private Text activityStartDate;
    @FXML
    private Text activityEndDate;
    @FXML
    private ListView unassignedListView;
    @FXML
    private ListView assignedListView;

    @FXML
    private void handleSaveBtn(ActionEvent event) throws IOException {

        // List<String> unassignedUsers = unassignedListView.getItems();
        List<String> assignedUsers = assignedListView.getItems();
        activityController.updateActivityAssignee(assignedUsers);

        String alertHeading = "Assigning activities";
        String alertContent = "Current activity is assigned to \nselected users.";
        UIHelper uiHelper = new UIHelper();
        Optional<ButtonType> result = uiHelper.alertDialogGenerator(dialogPane, "success", alertHeading, alertContent);
        if (result.get() == ButtonType.OK) {
            ActivitiesViewController.isUpdated.setValue(true);
            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();
        }
        ActivitiesViewController.isUpdated.setValue(true);
    }

    @FXML
    private void handleCancel(ActionEvent event) throws IOException {
        Stage stage;
        stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleAssignButton(ActionEvent event) {
        String user = (String) unassignedListView.getSelectionModel().getSelectedItem();

        if (user != null) {
            unassignedListView.getSelectionModel().clearSelection();
            projectUsers.remove(user);
            activityUsers.add(user);
        }
    }

    @FXML
    private void handleUnassignButton(ActionEvent event) {
        String user = (String) assignedListView.getSelectionModel().getSelectedItem();

        if (user != null) {
            assignedListView.getSelectionModel().clearSelection();
            activityUsers.remove(user);
            projectUsers.add(user);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Activity activity = (Activity) Session.getOpenItem();
        String activityName = activity.getName();
        LocalDate startDate = activity.getStartDate();
        LocalDate endDate = activity.getEndDate();
        ActivitiesViewController.isUpdated.setValue(false);

        String activityStartDate = startDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
        String activityEndDate = endDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));

        this.activityName.setText(activityName);
        this.activityStartDate.setText(activityStartDate);
        this.activityEndDate.setText(activityEndDate);

        setListViews();

        unassignedListView.setItems(projectUsers);
        assignedListView.setItems(activityUsers);

        if (assignedListView.getItems() == null) {
            assignedListView.setPlaceholder(new Text("No user selected yet"));
        }
    }

    private void setListViews() {
        ObjectId projectId = Session.getOpenProjectId();
        ArrayList<String> allProjectUsers = projectController.getProjectUsernameList(projectId);
        Activity activity = (Activity) Session.getOpenItem();
        ArrayList<String> activityUsernames = new ArrayList<>();
        ArrayList<ObjectId> activityUserIds = activity.getAssigneeList();

        if (activityUserIds != null) {
            for (ObjectId userId : activityUserIds) {
                String username = userController.getUserDetail(userId, "username");
                activityUsernames.add(username);
            }
        }

        activityUsers.addAll(activityUsernames);
        if (activityUsernames != null) {
            allProjectUsers.removeAll(activityUsernames);
        }
        projectUsers.addAll(allProjectUsers);
    }
}