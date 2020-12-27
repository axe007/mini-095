package com.group8.controllers.viewcontroller;

import com.group8.controllers.ActivityController;
import com.group8.helper.UIHelper;
import com.group8.model.Session;
import com.group8.model.Activity;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.*;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class ActivityAddViewController implements Initializable {

    private static ActivityController activityController = new ActivityController();
    private static UIHelper uiHelper = new UIHelper();

    @FXML
    private StackPane dialogPane;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label windowModeTitle;
    @FXML
    private Label activityNameLabel;
    @FXML
    private VBox vboxPane;
    @FXML
    private VBox vboxParentActivity;
    @FXML
    private VBox vboxStoryPoints;
    @FXML
    private VBox vboxEstimatedHours;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private ComboBox activityType;
    @FXML
    private ComboBox<ComboBoxItem> parentActivityCombo;
    @FXML
    private Slider activityPriority;
    @FXML
    private TextField activityName;
    @FXML
    private TextArea activityDescription;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private TextField storyPoints;
    @FXML
    private TextField estimatedHours;

    @FXML
    private void handleSaveActivityBtn(ActionEvent event) throws IOException {
        // clear all text field
        String activityType;
        String name;
        String description;
        LocalDate startDate;
        LocalDate endDate;
        Double storyPoints = 0.0;
        Double estimatedHours = 0.0;
        double priority;
        ObjectId parentId = null;

        String alertHeading = "Creating new activity";
        String alertContent = "New activity successfully created.\nPlease refresh in Activities view.";

        name = this.activityName.getText();
        description = this.activityDescription.getText();
        startDate = this.startDate.getValue();
        endDate = this.endDate.getValue();
        activityType = this.activityType.getValue().toString();
        priority = this.activityPriority.getValue();
        String storyPointsString = this.storyPoints.getText();
        String estimatedHoursString = this.estimatedHours.getText();

        if (activityType.equals("User story") && (!storyPointsString.equals("")) )  {
            storyPoints = Double.parseDouble(this.storyPoints.getText());
        } else if (!estimatedHoursString.equals("")) {
            estimatedHours = Double.parseDouble(this.estimatedHours.getText());
        }

        // Print to console for testing purposes
        System.out.println("Name: " + name);
        System.out.println("Description: " + description);
        System.out.println("Start date: " + String.valueOf(startDate));
        System.out.println("End date: " + String.valueOf(endDate));
        System.out.println("Activity type: " + activityType);
        System.out.println("Priority: " + String.valueOf(priority));
        System.out.println("Story points: " + String.valueOf(storyPoints));
        System.out.println("Estimate hours: " + String.valueOf(estimatedHours));

        UIHelper uiHelper = new UIHelper();

        if (Session.getWindowMode().equals("new")) {

        } else if (Session.getWindowMode().equals("edit")) {
            alertHeading = "Edit user details";
        }

        if (activityName.equals("") || activityDescription.equals("") || startDate.equals("") || endDate.equals("") ||
                (activityType.equals("User story") && storyPoints == 0.0) || ((activityType.equals("Task") || activityType.equals("Bug")) && estimatedHours == 0.0)) {
            uiHelper.alertDialogGenerator(dialogPane,"error", alertHeading, "Some required fields are empty.\nPlease check activity details and try again.");
        } else {
            if (Session.getWindowMode().equals("new")) {
                activityController.createActivity(parentId, activityType, name, description, startDate, endDate, priority, storyPoints, estimatedHours);
                activityController.updateActivitiesList(name);
            } else if (Session.getWindowMode().equals("edit")) {
                // userController.modifyUser(username, password, fullname, emailAddress, userRole);
                alertHeading = "Edit user details";
                alertContent = "User details successfully updated.\nPlease refresh in Users view.";
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

    @FXML
    private void activityTypeSwitch(ActionEvent event) throws IOException {
        // TODO
        String activityTypeSelected = activityType.getValue().toString();
        if (activityTypeSelected.equals("User story")) {
            activityNameLabel.setText("User story name");
            vboxEstimatedHours.setVisible(false);
            vboxStoryPoints.setVisible(true);
            vboxParentActivity.setVisible(false);
        } else if (activityTypeSelected.equals("Task")) {
            activityNameLabel.setText("Task name");
            vboxEstimatedHours.setVisible(true);
            vboxStoryPoints.setVisible(false);
            vboxParentActivity.setVisible(true);
            parentActivityCombo.setVisibleRowCount(20);
        } else {
            activityNameLabel.setText("Bug name");
            vboxEstimatedHours.setVisible(true);
            vboxStoryPoints.setVisible(false);
            vboxParentActivity.setVisible(true);
            parentActivityCombo.setVisibleRowCount(20);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            if (Session.getWindowMode().equals("new")) {
                windowModeTitle.setText("Enter new activity details:");
                // final ComboBox activityType = new ComboBox();
                setPrioritySlider();
                activityType.getItems().addAll(
                        "User story",
                        "Task",
                        "Bug");

                activityType.setValue("User story");
                vboxEstimatedHours.setVisible(false);
                activityType.setCellFactory(
                        new Callback<ListView<String>, ListCell<String>>() {
                            @Override public ListCell<String> call(ListView<String> param) {
                                final ListCell<String> cell = new ListCell<String>() {
                                    @Override public void updateItem(String item, boolean empty) {
                                        super.updateItem(item, empty);
                                        if (item != null) {
                                            setText(item);
                                            if (item.contains("User story")) {
                                                setTextFill(Color.GREEN);
                                            } else if (item.contains("Task")){
                                                setTextFill(Color.BLUE);
                                            } else if (item.contains("Bug")){
                                                setTextFill(Color.RED);
                                            }
                                        }
                                        else {
                                            setText(null);
                                        }
                                    }
                                };
                                return cell;
                            }
                        });

                generateActivityListComboBox();

            } else if (Session.getWindowMode().equals("edit")) {
                windowModeTitle.setText("Edit activity details:");

                Activity activity = (Activity) Session.getOpenItem();
                // username.setText(user.getUsername());
                // password.setText(user.getPassword());
                // fullname.setText(user.getFullname());
                // emailAddress.setText(user.getEmailAddress());

                /*if (user.getUserRole().equals("Developer")) {
                    developer.setSelected(true);
                } else if (user.getUserRole().equals("Project Manager")) {
                    projectManager.setSelected(true);
                } else if (user.getUserRole().equals("Scrum Master")) {
                    scrumMaster.setSelected(true);
                }*/

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setPrioritySlider() {
        activityPriority.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double n) {
                if (n < 6 && n > 4) return "Urgent";
                if (n < 5 && n > 3) return "High";
                if (n < 3 && n > 1) return "Low";
                if (n < 2 && n > 0) return "Very low";
                return "Normal";
            }

            @Override
            public Double fromString(String s) {
                switch (s) {
                    case "Urgent":
                        return 5.0;
                    case "High":
                        return 4.0;
                    case "Low":
                        return 2.0;
                    case "Very low":
                        return 1.0;

                    default:
                        return 3.0;
                }
            }
        });
    }

    private void generateActivityListComboBox() {
        final PseudoClass header = PseudoClass.getPseudoClass("section-header");
        Label parentActivityLabel = new Label();
        parentActivityLabel.setText("Parent activity");
        parentActivityLabel.setPadding(new Insets(0,0,3,1));
        vboxParentActivity.getChildren().clear();
        vboxParentActivity.getChildren().add(parentActivityLabel);

        parentActivityCombo = new ComboBox<>();
        parentActivityCombo.setPrefWidth(200.0);
        parentActivityCombo.setPadding(new Insets(0,0,0,20));
        parentActivityCombo.getStyleClass().add("activity-list");
        parentActivityCombo.getItems().addAll(
                new ComboBoxItem("User stories",false),
                new ComboBoxItem("User story 1.1",true),
                new ComboBoxItem("User story 1.2",true),
                new ComboBoxItem("User story 1.3",true),
                new ComboBoxItem("Tasks",false),
                new ComboBoxItem("Task 101",true),
                new ComboBoxItem("Task 201",true),
                new ComboBoxItem("Task 301",true),
                new ComboBoxItem("Task 401",true),
                new ComboBoxItem("Task 501",true),
                new ComboBoxItem("Task 601",true),
                new ComboBoxItem("Task 701",true),
                new ComboBoxItem("Task 801",true),
                new ComboBoxItem("Task 901",true),
                new ComboBoxItem("Task 1001",true),
                new ComboBoxItem("Task 1101",true),
                new ComboBoxItem("Task 1201",true),
                new ComboBoxItem("Task 1301",true),
                new ComboBoxItem("Task 1401",true),
                new ComboBoxItem("Task 1501",true),
                new ComboBoxItem("Task 1601",true),
                new ComboBoxItem("Task 1701",true),
                new ComboBoxItem("Task 1801",true),
                new ComboBoxItem("Task 1901",true),
                new ComboBoxItem("Task 2001",true),
                new ComboBoxItem("Task 2101",true),
                new ComboBoxItem("Task 2201",true),
                new ComboBoxItem("Task 2301",true),
                new ComboBoxItem("Task 2401",true)
        );

        parentActivityCombo.setCellFactory(listView -> new ListCell<ComboBoxItem>() {
            @Override
            public void updateItem(ComboBoxItem item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setDisable(false);
                    pseudoClassStateChanged(header, false);
                } else {
                    if (item.getName().equals("User stories")) {
                        setTextFill(Color.WHITE);
                        setStyle("-fx-font-weight: 900;-fx-background-color: #30c78d;");
                    } else if (item.getName().equals("Tasks")) {
                        setTextFill(Color.WHITE);
                        setStyle("-fx-font-weight: 900;-fx-background-color: #5179d6;");
                    } else {
                        setTextFill(Color.BLACK);
                        setStyle("-fx-font-weight: normal;");
                    }
                    setText(item.toString());
                    setDisable(! item.isSelectable());

                    pseudoClassStateChanged(header, ! item.isSelectable());
                }

            }
        });
        vboxParentActivity.getChildren().add(parentActivityCombo);
    }

    public static class ComboBoxItem {
        private final String name ;
        private final boolean selectable ;

        public ComboBoxItem(String name, boolean selectable) {
            this.name = name ;
            this.selectable = selectable ;
        }

        public String getName() {
            return name ;
        }

        public boolean isSelectable() {
            return selectable ;
        }

        @Override
        public String toString() {
            return name ;
        }
    }

}