package com.group8.controllers.viewcontroller;

import com.group8.controllers.NoteController;
import com.group8.controllers.ProjectController;
import com.group8.controllers.UserController;
import com.group8.helper.UIHelper;
import com.group8.model.Note;
import com.group8.model.Project;
import com.group8.model.Session;
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
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProjectCloseViewController implements Initializable {

    private static ProjectController projectController = new ProjectController();
    @FXML
    private StackPane dialogPane;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label windowModeTitle;
    @FXML
    private VBox vboxPane;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Text projectName;
    @FXML
    private Text projectDates;
    @FXML
    private Text projectType;
    @FXML
    private Text currentStatus;
    @FXML
    private Text projectDescription;
    @FXML
    private TextArea noteContent;

    @FXML
    private void handleCloseProjectBtn(ActionEvent event) throws IOException {
        UIHelper uiHelper = new UIHelper();
        // clear all text field
        String alertHeading = "Close project";
        String alertContent = "Are you sure to close this project?";
        Project project = (Project) Session.getOpenItem();
        ObjectId projectId = project.getId();

        Optional<ButtonType> result = uiHelper.alertDialogGenerator(dialogPane, "confirm", alertHeading,
                alertContent);
        if (result.get() == ButtonType.OK) {
            ProjectController projectController = new ProjectController();
            projectController.closeProject(projectId);

            String noteContent = this.noteContent.getText();

            if (!noteContent.isEmpty() || !noteContent.equals("")) {

                ObjectId userId = Session.getSessionUserId();
                UserController userController = new UserController();
                String userName = userController.getUserDetail(userId, "fullname");
                LocalDate createDate = LocalDate.now();
                String projectName = project.getName();

                Note newNote = new Note(projectId, projectId, projectName, "Project Note", userId, userName, createDate, "Project closing notes", noteContent);

                NoteController noteController = new NoteController();
                noteController.createNote(newNote);
            }

            ProjectViewController.isUpdated.setValue(true);
            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();
        } else {
            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();
        }
        ProjectViewController.isUpdated.setValue(true);
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
        ProjectViewController.isUpdated.setValue(false);
        try {
            windowModeTitle.setText("Close project");
            Project project = (Project) Session.getOpenItem();
            projectName.setText(project.getName());
            projectDescription.setText(project.getDescription());
            projectType.setText(project.getType());

            LocalDate startDate = project.getStartDate();
            LocalDate endDate = project.getEndDate();
            String startDateText = startDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
            String endDateText = endDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
            projectDates.setText(startDateText + " - " + endDateText);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}