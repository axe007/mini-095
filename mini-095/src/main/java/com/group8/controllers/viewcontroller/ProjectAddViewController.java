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
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
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
    private TextArea description;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private ToggleGroup projectTypeToggle;
    @FXML
    private Toggle software;
    @FXML
    private Toggle personal;
    @FXML
    private Toggle business;

    @FXML
    private void handleSaveProjectBtn(ActionEvent event) throws IOException {
        UIHelper uiHelper = new UIHelper();
        // clear all text field
        String name;
        String description;
        LocalDate startDate;
        LocalDate endDate;
        String type;
        String status = "In progress";
        String alertHeading = "Creating new Project";
        String alertContent = "New project successfully created.\nPlease refresh in projects view.";

        if (Session.getWindowMode().equals("new")) {
            System.out.println("Creating new project ...");
        } else if (Session.getWindowMode().equals("edit")) {
            alertHeading = "Edit project details";
        }

        name = this.name.getText();
        description = this.description.getText();
        startDate = this.startDate.getValue();
        endDate = this.endDate.getValue();

        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate.plusDays(1));

        if (daysBetween < 1) {
            uiHelper.alertDialogGenerator(dialogPane,"error", alertHeading, "End date cannot be earlier than Start Date.\nPlease check project dates and try again.");
        } else if (daysBetween == 1) {
            alertContent = "Project duration looks to be only 1 day.\n Are you sure?";
            Optional<ButtonType> dateConfirm = uiHelper.alertDialogGenerator(dialogPane,"confirm", alertHeading, alertContent);
            if (dateConfirm.get() == ButtonType.CANCEL) {
                return;
            }
        }

        RadioButton selectedRadioButton = (RadioButton) projectTypeToggle.getSelectedToggle();
        type = selectedRadioButton.getText();

        if (name.equals("") || startDate.equals("") || endDate.equals("")) {
            uiHelper.alertDialogGenerator(dialogPane,"error", alertHeading, "No fields can be empty.\nPlease check project details and try again.");
        } else {
            if (Session.getWindowMode().equals("new")) {
                projectController.createProject(name, description, startDate, endDate, type);
            } else if (Session.getWindowMode().equals("edit")) {
                projectController.modifyProject(name, description, startDate, endDate, type);
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

                Callback<DatePicker, DateCell> callB = new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker param) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
                                LocalDate today = LocalDate.now();
                                setDisable(empty || item.compareTo(today) < 0);
                            }

                        };
                    }
                };

                startDate.setDayCellFactory(callB);
                endDate.setDayCellFactory(callB);
                startDate.setValue(LocalDate.now());
                endDate.setValue(LocalDate.now());
            } else if (Session.getWindowMode().equals("edit")) {
                windowModeTitle.setText("Edit project details:");

                Project project = (Project) Session.getOpenItem();
                name.setText(project.getName());
                description.setText(project.getDescription());
                startDate.setValue(LocalDate.from(project.getStartDate()));
                endDate.setValue(LocalDate.from(project.getEndDate()));

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