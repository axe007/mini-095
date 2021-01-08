package com.group8.controllers.viewcontroller;

import com.group8.controllers.ProjectController;
import com.group8.controllers.UserController;
import com.group8.helper.UIHelper;
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
import java.util.*;

public class UserAssignViewController implements Initializable {

    private static UserController userController = new UserController();
    private static ProjectController projectController = new ProjectController();
    private static UIHelper uiHelper = new UIHelper();

    // Users
    private ObservableList<String> allUsers = FXCollections.observableArrayList();
    private ObservableList<String> projectUsers = FXCollections.observableArrayList();

    @FXML
    private StackPane dialogPane;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Text projectName;
    @FXML
    private Text projectStartDate;
    @FXML
    private Text projectEndDate;
    @FXML
    private ListView unassignedListView;
    @FXML
    private ListView assignedListView;

    @FXML
    private void handleSaveBtn(ActionEvent event) throws IOException {
        // List<String> unassignedUsers = unassignedListView.getItems();
        List<String> assignedUsers = assignedListView.getItems();
        projectController.updateDeveloperTeam(assignedUsers);

        String alertHeading = "Assigning users";
        String alertContent = "Current project users have been \nsuccessfully updated.";

        Optional<ButtonType> result = uiHelper.alertDialogGenerator(dialogPane, "success", alertHeading, alertContent);
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
        String user = (String) unassignedListView.getSelectionModel().getSelectedItem();

        if (user != null) {
            unassignedListView.getSelectionModel().clearSelection();
            allUsers.remove(user);
            projectUsers.add(user);
        }
    }

    @FXML
    private void handleUnassignButton(ActionEvent event) {
        String user = (String) assignedListView.getSelectionModel().getSelectedItem();

        if (user != null) {
            assignedListView.getSelectionModel().clearSelection();
            projectUsers.remove(user);
            allUsers.add(user);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObjectId openProjectId = Session.getOpenProjectId();
        String openProjectName = projectController.getProjectDetail(openProjectId, "projectName");
        LocalDate startDate = projectController.getProjectDate(openProjectId, "startDate");
        LocalDate endDate = projectController.getProjectDate(openProjectId, "endDate");

        String projectStartDate = startDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
        String projectEndDate = endDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));

        this.projectName.setText(openProjectName);
        this.projectStartDate.setText(projectStartDate);
        this.projectEndDate.setText(projectEndDate);

        // final ListView<String> unassignedList = new ListView<>(allUsers);
        // final ListView<String> assignedList = new ListView<>(projectUsers);

        setListViews();

        unassignedListView.setItems(allUsers);
        assignedListView.setItems(projectUsers);

        if (assignedListView.getItems() == null) {
            assignedListView.setPlaceholder(new Text("No user selected yet"));
        }
    }

    private void setListViews() {
        ArrayList<String> allUsernames = userController.getUserDetailList("username");

        ObjectId openProjectId = Session.getOpenProjectId();
        ArrayList<String> projectUsernames = projectController.getProjectUsernameList(openProjectId);
        projectUsers.addAll(projectUsernames);
        if (projectUsernames != null) {
            allUsernames.removeAll(projectUsernames);
        }
        allUsers.addAll(allUsernames);
    }
}