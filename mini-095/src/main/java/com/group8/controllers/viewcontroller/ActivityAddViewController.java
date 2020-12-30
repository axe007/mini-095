package com.group8.controllers.viewcontroller;

import com.group8.controllers.ActivityController;
import com.group8.helper.UIHelper;
import com.group8.model.*;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import java.lang.reflect.Array;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class ActivityAddViewController implements Initializable {

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
        LocalDate startDate = null;
        LocalDate endDate = null;
        Double storyPoints = 0.0;
        Double estimatedHours = 0.0;
        double priority;
        ObjectId grandId = null;
        ObjectId parentId = null;
        String parentItemName = null;

        String alertHeading = "Creating new activity";
        String alertContent = "New activity successfully created.\nPlease refresh in Activities view.";

        name = this.activityName.getText();
        description = this.activityDescription.getText();
        activityType = this.activityType.getValue().toString();

        if (this.startDate != null || this.endDate != null) {
            startDate = this.startDate.getValue();
            endDate = this.endDate.getValue();
        }

        // Get parentId and grandId
        if (parentActivityCombo != null || !parentActivityCombo.getValue().equals(null)) {
            parentItemName = parentActivityCombo.getValue().toString();
            for (Activity activity : activitiesList) {
                if (activity.getName().equals(parentItemName)) {
                    parentId = activity.getId();
                }
            }
            for (Activity activity : activitiesList) {
                if (activity instanceof Task && activity.getId().equals(parentId)) {
                    grandId = ((Task) activity).getParentId();
                } else if (activity instanceof Bug && activity.getId().equals(parentId)) {
                    grandId = ((Bug) activity).getParentId();
                }
            }
        }

        // Validate activity name
        for (Activity activity : activitiesList) {
            if (name.equals(activity.getName())) {
                uiHelper.alertDialogGenerator(dialogPane,"error", alertHeading, "Duplicate activity name.\nPlease check activity name and try again.");
                activityName.getStyleClass().add("textfield-error-highlight");
                activityName.requestFocus();
                return;
            }
        }

        priority = this.activityPriority.getValue();
        String storyPointsString = this.storyPoints.getText();
        String estimatedHoursString = this.estimatedHours.getText();

        if (activityType.equals("User story") && (storyPointsString.equals("")) )  {
            uiHelper.alertDialogGenerator(dialogPane,"error", alertHeading, "Story point missing.\nPlease check the field and try again.");
            this.storyPoints.getStyleClass().add("textfield-error-highlight");
            this.storyPoints.requestFocus();
            return;
        } else if (activityType.equals("User story") && (!storyPointsString.equals("")) )  {
            storyPoints = Double.parseDouble(this.storyPoints.getText());
        } else if ((activityType.equals("Task") || activityType.equals("Bug")) && (estimatedHoursString.equals("")) )  {
            uiHelper.alertDialogGenerator(dialogPane,"error", alertHeading, "Estimated hours missing.\nPlease check the field and try again.");
            this.storyPoints.getStyleClass().add("textfield-error-highlight");
            this.storyPoints.requestFocus();
            return;
        } else if (!estimatedHoursString.equals("")) {
            estimatedHours = Double.parseDouble(this.estimatedHours.getText());
        }

        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate.plusDays(1));
        if (daysBetween < 1) {
            uiHelper.alertDialogGenerator(dialogPane,"error", alertHeading, "End date cannot be earlier than Start Date.\nPlease check activity dates and try again.");
            return;
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
        System.out.println("Parent id: " + String.valueOf(parentId));
        System.out.println("Parent item: " + parentItemName);
        System.out.println("Grandparent id: " + String.valueOf(grandId));

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
                activityController.createActivity(grandId, parentId, activityType, name, description, startDate, endDate, priority, storyPoints, estimatedHours);
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
        activitiesList.clear();
        activitiesList = activityController.getActivitiesList();

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

                Callback<DatePicker, DateCell> projectDates = new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker param) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);
                                LocalDate projectStartDate = Session.getProjectStartDate();
                                LocalDate projectEndDate = Session.getProjectEndDate();
                                setDisable((empty || item.compareTo(projectStartDate) < 0) || (empty || item.compareTo(projectEndDate) > 0));
                            }

                        };
                    }
                };

                startDate.setDayCellFactory(projectDates);
                endDate.setDayCellFactory(projectDates);

                // Listener for updating the endDate as +1 from startDate selected
                startDate.valueProperty().addListener((ov, oldValue, newValue) -> {
                    endDate.setValue(newValue.plusDays(1));
                });


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

        // No parent item - Default item for non-selection
        parentActivityCombo.getItems().add(new ComboBoxItem("No parent item",true, 0));
        // Header line for Tasks
        parentActivityCombo.getItems().add(new ComboBoxItem("User stories",false, 0));

        ObjectId topLevelActivityID = null;
        ObjectId midLevelActivityID = null;
        ArrayList<ComboBoxItem> comboBoxItems = new ArrayList<>();

        // Loop for top level User stories
        for (Activity activity : activitiesList) {
            if (activity instanceof UserStory) {
                String activityName = activity.getName();
                comboBoxItems.add(new ComboBoxItem(activityName, true, 1));
                topLevelActivityID = activity.getId();

                // Loop for middle level tasks and bugs
                for (Activity activity2 : activitiesList) {
                    midLevelActivityID = activity2.getId();
                    if ((activity2 instanceof Task) && ((Task ) activity2).getGrandId() == null && ((Task ) activity2).getParentId() != null)  {
                        if (((Task) activity2).getParentId().equals(topLevelActivityID)) {
                            String midLevelActivityName = activity2.getName();
                            comboBoxItems.add(new ComboBoxItem(midLevelActivityName, true, 2));
                            // Loop for bottom level tasks and bug under Middle level tasks
                            for (Activity activity3 : activitiesList) {
                                if ((activity3 instanceof Task) && ((Task) activity3).getGrandId() != null && ((Task) activity3).getParentId() != null)  {
                                    if (((Task) activity3).getGrandId().equals(topLevelActivityID) && ((Task) activity3).getParentId().equals(midLevelActivityID)) {
                                        String bottomLevelActivityName = activity3.getName();
                                        comboBoxItems.add(new ComboBoxItem(bottomLevelActivityName, false,3));
                                    }
                                } else if ((activity3 instanceof Bug) && ((Bug) activity3).getGrandId() != null && ((Bug) activity3).getParentId() != null) {
                                    if (((Bug) activity3).getGrandId().equals(topLevelActivityID) && ((Bug) activity3).getParentId().equals(midLevelActivityID)) {
                                        String bottomLevelActivityName = activity3.getName();
                                        comboBoxItems.add(new ComboBoxItem(bottomLevelActivityName, false,3));
                                    }
                                }
                            }
                        }
                    } else if ((activity2 instanceof Bug) && ((Bug) activity2).getGrandId() == null && ((Bug) activity2).getGrandId() != null) {
                        if (((Bug) activity2).getParentId().equals(topLevelActivityID)) {
                            String midLevelActivityName = activity2.getName();
                            comboBoxItems.add(new ComboBoxItem(midLevelActivityName, true, 2));
                            // Loop for bottom level tasks and bug under Middle level bugs
                            for (Activity activity3 : activitiesList) {
                                if ((activity3 instanceof Task) && ((Task) activity3).getGrandId() != null && ((Task) activity3).getParentId() != null)  {
                                    if (((Task) activity3).getGrandId().equals(topLevelActivityID) && ((Task) activity3).getParentId().equals(midLevelActivityID)) {
                                        String bottomLevelActivityName = activity3.getName();
                                        comboBoxItems.add(new ComboBoxItem(bottomLevelActivityName, false,3));
                                    }
                                } else if ((activity3 instanceof Bug) && ((Bug) activity3).getGrandId() != null && ((Bug) activity3).getParentId() != null) {
                                    if (((Bug) activity3).getGrandId().equals(topLevelActivityID) && ((Bug) activity3).getParentId().equals(midLevelActivityID)) {
                                        String bottomLevelActivityName = activity3.getName();
                                        comboBoxItems.add(new ComboBoxItem(bottomLevelActivityName, false,3));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Header line for Tasks
        comboBoxItems.add(new ComboBoxItem("Tasks",false,0));

        // Loop for top level Tasks and subtasks (or bugs)
        for (Activity activity : activitiesList) {
            if ((activity instanceof Task) && ((Task ) activity).getGrandId() == null && ((Task ) activity).getParentId() == null) {
                String activityName = activity.getName();
                comboBoxItems.add(new ComboBoxItem(activityName, true, 1));
                topLevelActivityID = activity.getId();

                // Loop for middle level tasks and bugs
                for (Activity activity2 : activitiesList) {
                    midLevelActivityID = activity2.getId();
                    if ((activity2 instanceof Task) && ((Task ) activity2).getGrandId() == null && ((Task ) activity2).getParentId() != null)  {
                        if (((Task) activity2).getParentId().equals(topLevelActivityID)) {
                            String midLevelActivityName = activity2.getName();
                            comboBoxItems.add(new ComboBoxItem(midLevelActivityName, true, 2));
                            // Loop for bottom level tasks and bug under Middle level tasks
                            for (Activity activity3 : activitiesList) {
                                if ((activity3 instanceof Task) && ((Task) activity3).getGrandId() != null && ((Task) activity3).getParentId() != null)  {
                                    if (((Task) activity3).getGrandId().equals(topLevelActivityID) && ((Task) activity3).getParentId().equals(midLevelActivityID)) {
                                        String bottomLevelActivityName = activity3.getName();
                                        comboBoxItems.add(new ComboBoxItem(bottomLevelActivityName, false,3));
                                    }
                                } else if ((activity3 instanceof Bug) && ((Bug) activity3).getGrandId() != null && ((Bug) activity3).getParentId() != null) {
                                    if (((Bug) activity3).getGrandId().equals(topLevelActivityID) && ((Bug) activity3).getParentId().equals(midLevelActivityID)) {
                                        String bottomLevelActivityName = activity3.getName();
                                        comboBoxItems.add(new ComboBoxItem(bottomLevelActivityName, false,3));
                                    }
                                }
                            }
                        }
                    } else if ((activity2 instanceof Bug) && ((Bug) activity2).getGrandId() == null && ((Bug) activity2).getGrandId() != null) {
                        if (((Bug) activity2).getParentId().equals(topLevelActivityID)) {
                            String midLevelActivityName = activity2.getName();
                            comboBoxItems.add(new ComboBoxItem(midLevelActivityName, true, 2));
                            // Loop for bottom level tasks and bug under Middle level bugs
                            for (Activity activity3 : activitiesList) {
                                if ((activity3 instanceof Task) && ((Task) activity3).getGrandId() != null && ((Task) activity3).getParentId() != null)  {
                                    if (((Task) activity3).getGrandId().equals(topLevelActivityID) && ((Task) activity3).getParentId().equals(midLevelActivityID)) {
                                        String bottomLevelActivityName = activity3.getName();
                                        comboBoxItems.add(new ComboBoxItem(bottomLevelActivityName, false,3));
                                    }
                                } else if ((activity3 instanceof Bug) && ((Bug) activity3).getGrandId() != null && ((Bug) activity3).getParentId() != null) {
                                    if (((Bug) activity3).getGrandId().equals(topLevelActivityID) && ((Bug) activity3).getParentId().equals(midLevelActivityID)) {
                                        String bottomLevelActivityName = activity3.getName();
                                        comboBoxItems.add(new ComboBoxItem(bottomLevelActivityName, false,3));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Add items to the comboBox list
        parentActivityCombo.getItems().addAll(comboBoxItems);

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
                        setPadding(new Insets(0,0,0,5));
                    } else if (item.getName().equals("Tasks")) {
                        setTextFill(Color.WHITE);
                        setStyle("-fx-font-weight: 900;-fx-background-color: #5179d6;");
                        setPadding(new Insets(0,0,0,5));
                    } else if (item.getLevel() == 1) {
                        setPadding(new Insets(0,0,0,10));
                    } else if (item.getLevel() == 2) {
                        setPadding(new Insets(0,0,0,15));
                        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.ANGLE_RIGHT, "8");
                        setGraphic(icon);
                    } else if (item.getLevel() == 3) {
                        setPadding(new Insets(0,0,0,20));
                        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.ANGLE_DOUBLE_RIGHT, "8");
                        setGraphic(icon);
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
        parentActivityCombo.getSelectionModel().selectFirst();

    }

    public static class ComboBoxItem {
        private final String name;
        private final boolean selectable;
        private final int level;

        public ComboBoxItem(String name, boolean selectable, int level) {
            this.name = name;
            this.selectable = selectable;
            this.level = level;
        }

        public String getName() {
            return name;
        }
        public boolean isSelectable() {
            return selectable;
        }
        public int getLevel() {
            return level;
        }

        @Override
        public String toString() {
            return name;
        }
    }

}
