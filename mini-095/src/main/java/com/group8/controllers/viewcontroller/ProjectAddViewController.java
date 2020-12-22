package com.group8.controllers.viewcontroller;

import com.group8.controllers.ProjectController;
import com.group8.controllers.UserController;
import com.group8.helper.UIHelper;
import com.group8.model.Project;
import com.group8.model.ProjectType;
import com.group8.model.Session;
import com.group8.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProjectAddViewController implements Initializable {

    private static ProjectController projectController = new ProjectController();
    @FXML
    private StackPane dialogPane;
    @FXML
    private StackPane projectView;
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
    private TextField name;
    @FXML
    private TextField startDate;
    @FXML
    private TextField endDate;
    @FXML
    private ToggleGroup projectTypeToggle;
    @FXML
    private Toggle software;
    @FXML
    private Toggle personal;
    @FXML
    private Toggle business;


    /*@FXML
    private void handleSaveProjectBtn(ActionEvent event) throws IOException {
        // clear all text field
        String name;
        String startDate;
        String endDate;
        String type;
        String status;
        String alertHeading = "Creating new project";
        String alertContent = "New project successfully created.\nPlease refresh in Proejct view.";

        name = this.name.getText();
        startDate = this.startDate.getText();
        endDate = this.endDate.getText();
        status = "In progress";



        RadioButton selectedRadioButton = (RadioButton) typeToggle.getSelectedToggle();
        type = selectedRadioButton.getText();

        UIHelper uiHelper = new UIHelper();
        boolean validation = true;

        if (Session.getWindowMode().equals("new")) {


        } else if (Session.getWindowMode().equals("edit")) {
            alertHeading = "Edit project details";
        }

        if (name.equals("") || startDate.equals("") || endDate.equals("") ||type.equals("") ) {
            uiHelper.alertDialogGenerator(dialogPane,"error", alertHeading, "No fields can be empty.\nPlease check project details and try again.");
            validation = false;
        } else {
            if (Session.getWindowMode().equals("new")) {

                projectController.createProject(name, startDate, endDate, type, status);
            } else if (Session.getWindowMode().equals("edit")) {
                //projectController.modifyProject(name, localStartDate, localEndDate, ProjectType.valueOf(type), status);
                alertHeading = "Edit user details";
                alertContent = "User details successfully updated.\nPlease refresh in Users view.";
            }
            Optional<ButtonType> result = uiHelper.alertDialogGenerator(dialogPane,"success", alertHeading, alertContent);
            if (result.get() == ButtonType.OK) {
                Stage stage = (Stage) saveButton.getScene().getWindow();
                stage.close();
            }
        }
    }*/

    @FXML
    private void handleSaveProjectBtn(ActionEvent event) throws IOException {
        // clear all text field
        String name;
        String startDate;
        String endDate;
        String type;
        String status = "In progress";
        String alertHeading = "Creating new Project";
        String alertContent = "New project successfully created.\nPlease refresh in projects view.";

        name = this.name.getText();
        startDate = this.startDate.getText();
        endDate = this.endDate.getText();

        RadioButton selectedRadioButton = (RadioButton) projectTypeToggle.getSelectedToggle();
        type = selectedRadioButton.getText();

        UIHelper uiHelper = new UIHelper();
        boolean validation = true;

        if (Session.getWindowMode().equals("new")) {
            System.out.println("Creating new project ...");
        } else if (Session.getWindowMode().equals("edit")) {
            alertHeading = "Edit project details";
        }

        if (name.equals("") || startDate.equals("") || endDate.equals("")) {
            uiHelper.alertDialogGenerator(dialogPane,"error", alertHeading, "No fields can be empty.\nPlease check project details and try again.");
            validation = false;
        } else {
            if (Session.getWindowMode().equals("new")) {
                projectController.createProject(name, startDate, endDate, type);
            } else if (Session.getWindowMode().equals("edit")) {
                projectController.modifyProject(name, startDate, endDate, type);
                alertHeading = "Edit project details";
                alertContent = "Project details successfully updated.\nPlease refresh in Projects view.";
            }
            Optional<ButtonType> result = uiHelper.alertDialogGenerator(dialogPane,"success", alertHeading, alertContent);
            if (result.get() == ButtonType.OK) {
                Stage stage = (Stage) saveButton.getScene().getWindow();
                stage.close();
            }
        }
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
        try {
            if (Session.getWindowMode().equals("new")) {
                windowModeTitle.setText("Enter new project details:");
            } else if (Session.getWindowMode().equals("edit")) {
                windowModeTitle.setText("Edit project details:");

                Project project = (Project) Session.getOpenItem();
                name.setText(project.getName());
                startDate.setText(String.valueOf(project.getStartDate()));
                endDate.setText(String.valueOf(project.getEndDate()));

                if (project.getType().equals("Software")) {
                    software.setSelected(true);
                } else if (project.getType().equals("Personal")) {
                    personal.setSelected(true);
                } else if (project.getType().equals("Business")) {
                   business.setSelected(true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}