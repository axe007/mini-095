// package com.group8.controllers.viewcontroller;

// import java.net.URL;
// import java.util.ResourceBundle;
// import com.group8.model.Session;

// import javafx.event.ActionEvent;
// import javafx.fxml.FXML;
// import javafx.scene.control.Button;
// import javafx.scene.control.Label;
// import javafx.scene.control.TextField;
// import javafx.scene.control.ToggleGroup;
// import javafx.fxml.Initializable;

// public class ActivitiesAddViewController implements Initializable{

//     @FXML
//     private TextField name;
//     @FXML
//     private Label windowModeTitle;
//     @FXML
//     private ToggleGroup priority;

//     @FXML
//     private ToggleGroup type;

//     @FXML
//     private Button saveButton;

//     @FXML
//     private Button cancelButton;

//     @FXML
//     void handleCancel(ActionEvent event) {

//     }

//     @FXML
//     void handleSaveProjectBtn(ActionEvent event) {

//     }
//     @Override
//     public void initialize(URL location, ResourceBundle resources) {
//         try {
//             if (Session.getWindowMode().equals("new")) {
//                 // windowModeTitle.setText("Enter new user details:");
//             } else if (Session.getWindowMode().equals("edit")) {

//             }
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }
// }
package com.group8.controllers.viewcontroller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
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
import com.group8.model.Session;

import org.bson.types.ObjectId;

import com.group8.controllers.ActivityController;
import com.group8.helper.UIHelper;
import com.group8.model.Activity;
import com.group8.model.Project; 

public class ActivitiesAddViewController implements Initializable{
    private static ActivityController activityController = new ActivityController();

    @FXML
    private StackPane dialogPane;

    @FXML
    private AnchorPane mainContainer;

    @FXML
    private VBox vboxPane;

    @FXML
    private Label windowModeTitle;

    @FXML
    private TextField name;
    @FXML
    private TextArea description;
    // @FXML
    // private TextField description;

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

    @FXML
    private Label type;

    @FXML
    private RadioButton task;
    @FXML
    private RadioButton bug;
    // @FXML
    // private RadioButton userStory;
    @FXML
    private RadioButton one;
    @FXML
    private RadioButton two;
    @FXML
    private RadioButton three;
    @FXML
    private RadioButton four;
    @FXML
    private RadioButton five;
    @FXML
    private ToggleGroup activityTypeToggle;

    @FXML
    private ToggleGroup activityPriorityToggle;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    void handleCancel(ActionEvent event) {
        Stage stage;
        stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void handleSaveActivityBtn(ActionEvent event) throws IOException {
        UIHelper uiHelper = new UIHelper();
        String name; 
        LocalDate startDate;
        LocalDate endDate;
        String description;
        String priority; 
        String type; 
        String alertHeading = "Creating new Activity";
        String alertContent = "New activity successfully created.\nPlease refresh in activities view.";
        System.out.println(Session.getWindowMode()); 

        if (Session.getWindowMode().equals("new")) {
            System.out.println("Creating new activity ...");
        } else if (Session.getWindowMode().equals("edit")) {
            alertHeading = "Edit activity details";
        }
        name = this.name.getText();
        startDate = this.startDate.getValue();
        endDate = this.endDate.getValue();
        description = this.description.getText();

        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate.plusDays(1));

        if (daysBetween < 1) {
            uiHelper.alertDialogGenerator(dialogPane,"error", alertHeading, "End date cannot be earlier than Start Date.\nPlease check activity dates and try again.");
        } else if (daysBetween == 1) {
            alertContent = "Activity duration looks to be only 1 day.\n Are you sure?";
            Optional<ButtonType> dateConfirm = uiHelper.alertDialogGenerator(dialogPane,"confirm", alertHeading, alertContent);
            if (dateConfirm.get() == ButtonType.CANCEL) {
                return;
            }
        }
        RadioButton priorityRadioButton = (RadioButton) activityPriorityToggle.getSelectedToggle();
        priority = priorityRadioButton.getText();
        RadioButton typeRadioButton = (RadioButton) activityTypeToggle.getSelectedToggle();
        type = typeRadioButton.getText(); 
        if (name.equals("") || startDate.equals("") || endDate.equals("") || description.equals("") || priority.equals("") || type.equals("")) {
            uiHelper.alertDialogGenerator(dialogPane,"error", alertHeading, "No fields can be empty.\nPlease check activity details and try again.");
        } else {
            if (Session.getWindowMode().equals("new")) {
                ObjectId openProjectId = Session.getOpenProjectId();
                activityController.createActivity(openProjectId, name, description, startDate, endDate, priority, type);
            } else if (Session.getWindowMode().equals("edit")) {
                // projectController.modifyProject(name, startDate, endDate, type);
                // alertHeading = "Edit project details";
                // alertContent = "Project details successfully updated.\nPlease refresh in Projects view.";
            }
            Optional<ButtonType> result = uiHelper.alertDialogGenerator(dialogPane,"success", alertHeading, alertContent);
            if (result.get() == ButtonType.OK) {
                Stage stage = (Stage) saveButton.getScene().getWindow();
                stage.close();
            }
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            if (Session.getWindowMode().equals("new")) {
                // windowModeTitle.setText("Enter new activity details:");

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
                startDate.setValue(LocalDate.now());
                endDate.setValue(LocalDate.now());
            } else if (Session.getWindowMode().equals("edit")) {
                // windowModeTitle.setText("Edit project details:");

                // Project project = (Project) Session.getOpenItem();
                // name.setText(project.getName());
                // startDate.setValue(LocalDate.from(project.getStartDate()));
                // endDate.setValue(LocalDate.from(project.getEndDate()));

                // if (project.getType().equals("Software")) {
                //     software.setSelected(true);
                // } else if (project.getType().equals("Personal")) {
                //     personal.setSelected(true);
                // } else if (project.getType().equals("Business")) {
                //    business.setSelected(true);
                // }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}