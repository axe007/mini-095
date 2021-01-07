package com.group8.controllers.viewcontroller;

import com.group8.controllers.ActivityController;
import com.group8.controllers.SprintController;
import com.group8.helper.UIHelper;
import com.group8.model.*;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

public class ScrumboardViewController implements Initializable {
    @FXML
    private StackPane scrumboardView;
    @FXML
    private GridPane projectBreadcrumb;
    @FXML
    private ListView<ListCellItem> listToDo;
    @FXML
    private ListView<ListCellItem> listInProgress;
    @FXML
    private ListView<ListCellItem> listReview;
    @FXML
    private ListView<ListCellItem> listDone;
    @FXML
    private Button btnActivityItemAssign;
    @FXML
    private Label sprintTitle;
    @FXML
    private Label sprintPeriod;
    @FXML
    private Button sprintNewButton;
    @FXML
    private Button activityUpdateButton;
    @FXML
    private Button activityAssignButton;
    @FXML
    private Button activityTimeLogButton;
    @FXML
    private Button sprintCompleteButton;
    @FXML
    private TextField activitySearch;

    private static SprintController sprintController = new SprintController();
    private static ActivityController activityController = new ActivityController();
    private static UIHelper uiHelper = new UIHelper();
    public static BooleanProperty isUpdated = new SimpleBooleanProperty();

    @FXML
    private void handleSprintButtons(ActionEvent event) throws IOException {
        isUpdated.setValue(false);
        if (event.getSource() == sprintNewButton) {
            if (Session.getCurrentSprintId() != null) {
                uiHelper.alertDialogGenerator(scrumboardView,"error", "New sprint", "Current sprint is not completed.\nPlease complete the current sprint and try again.");
                return;
            } else if (validateNewSprintDate()) {
                    String alertHeading = "Create new sprint";
                    String alertContent = "Previous sprint ended on project's end date.\nUnable to create next sprint.\n Please update project dates and try again.";
                    uiHelper.alertDialogGenerator(scrumboardView,"error", alertHeading, alertContent);
            } else {
                Session.setWindowMode("new");
                uiHelper.loadWindow("SprintAddView", sprintNewButton, "Create new sprint");
            }
        } else if (event.getSource() == activityAssignButton) {
            if (Session.getCurrentSprintId() == null) {
                uiHelper.alertDialogGenerator(scrumboardView,"error", "Assign activities", "No sprint has been created.\nPlease start a new sprint and try again.");
                return;
            } else {
                Session.setWindowMode("edit");
                uiHelper.loadWindow("SprintAddView", activityAssignButton, "Sprint activities");
            }
        } else if (event.getSource() == activityUpdateButton || event.getSource() == activityTimeLogButton) {
            ArrayList<ListView> listViews = new ArrayList<>(Arrays.asList(listToDo, listInProgress, listReview, listDone));
            ListCellItem listItem = null;
            for (ListView list : listViews) {
                if (!list.getSelectionModel().isEmpty()) {
                    listItem = (ListCellItem) list.getSelectionModel().getSelectedItem();
                }
            }

            if (listItem == null) {
                uiHelper.alertDialogGenerator(scrumboardView,"error", "Update activity", "No activity selected.\nPlease select an activity and try again.");
                return;
            } else {
                String name = listItem.getName();
                Activity activity = activityController.getActivity("name", name);
                Session.setSetOpenItem(activity);
                if (event.getSource() == activityUpdateButton) {
                    uiHelper.loadWindow("ActivityUpdateView", activityUpdateButton, "Update activity");
                } else if (event.getSource() == activityTimeLogButton) {
                    uiHelper.loadWindow("ActivityTimeLogView", activityUpdateButton, "Update time log");
                }
            }
        }  else if (event.getSource() == sprintCompleteButton) {
            if (Session.getCurrentSprintId() == null) {
                uiHelper.alertDialogGenerator(scrumboardView,"error", "Complete a sprint", "No sprint has been created.\nPlease start a new sprint and try again.");
                return;
            } else {
                uiHelper.loadWindow("SprintCompleteView", sprintNewButton, "Complete a sprint");
            }
        }
    }

    @FXML
    private void handleSelection(MouseEvent event) throws IOException {
        ArrayList<ListView> listViews = new ArrayList<>(Arrays.asList(listToDo, listInProgress, listReview, listDone));

        for (ListView list : listViews) {
            if (event.getSource() != list) {
                list.getSelectionModel().clearSelection();
            }
        }
    }

    public void loadListViews(ArrayList<Activity> activitiesList) {
        ArrayList<ListCellItem> todoItems = new ArrayList<>();
        ArrayList<ListCellItem> inprogressItems = new ArrayList<>();
        ArrayList<ListCellItem> reviewItems = new ArrayList<>();
        ArrayList<ListCellItem> doneItems = new ArrayList<>();
        String activityName;
        String activityType;
        double activityPriority;

        for (Activity activity : activitiesList) {
            activityName = activity.getName();
            activityPriority = activity.getPriority();
            if (activity instanceof UserStory) {
                activityType = "User Story";
            } else if (activity instanceof Task) {
                activityType = "Task";
            } else {
                activityType = "Bug";
            }
            if (activity.getActivityStatus().equals("TODO")) {
                todoItems.add(new ListCellItem(activityName, activityType, (int)activityPriority));
            } else if (activity.getActivityStatus().equals("INPROGRESS")) {
                inprogressItems.add(new ListCellItem(activityName, activityType, (int)activityPriority));
            } else if (activity.getActivityStatus().equals("REVIEW")) {
                reviewItems.add(new ListCellItem(activityName, activityType, (int)activityPriority));
            } else if (activity.getActivityStatus().equals("DONE")) {
                doneItems.add(new ListCellItem(activityName, activityType, (int)activityPriority));
            }
        }

        Callback<ListView<ListCellItem>, ListCell<ListCellItem>> cellFactory = new Callback<ListView<ListCellItem>, ListCell<ListCellItem>>() {
            @Override
            public ListCell<ListCellItem> call(ListView<ListCellItem> param) {
                ListCell<ListCellItem> cell = new ListCell<ListCellItem>() {
                    @Override
                    protected void updateItem(ListCellItem item, boolean empty) {

                        super.updateItem(item, empty);
                        if (item != null && item.getType().equals("User Story")) {
                            setPrefHeight(45.0);
                            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.STACK_EXCHANGE, "12");
                            icon.setFill(Color.rgb(8,97,8));
                            setText(item.getName());
                            setGraphic(icon);
                            setTextFill(Color.rgb(8,97,8));
                            int priority = item.getPriority();
                            setBorder(paintPriority(priority));
                        } else if (item != null && item.getType().equals("Task")){
                            setPrefHeight(45);
                            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CODE_FORK, "12");
                            icon.setFill(Color.rgb(0,5,221));
                            setText(item.getName());
                            setGraphic(icon);
                            setTextFill(Color.rgb(0,5,221));
                            int priority = item.getPriority();
                            setBorder(paintPriority(priority));
                        } else if (item != null && item.getType().equals("Bug")){
                            setPrefHeight(45);
                            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.BUG, "12");
                            icon.setFill(Color.rgb(200,13,13));
                            setText(item.getName());
                            setGraphic(icon);
                            setTextFill(Color.rgb(200,13,13));
                            int priority = item.getPriority();
                            setBorder(paintPriority(priority));
                        } else if (empty || item == null || item.getName().equals("")) {
                            setVisible(false);
                            setPrefHeight(0.0);
                            setGraphic(null);
                        }
                    }
                };
                return cell;
            }
        };

        ArrayList<ListView> listViews = new ArrayList<>(Arrays.asList(listToDo, listInProgress, listReview, listDone));
        for (ListView listView : listViews) {
            listView.getItems().clear();
            listView.setPlaceholder(new Label("No Activities"));
            listView.setCellFactory(cellFactory);
            if (!todoItems.isEmpty() && listView == listToDo) {
                listView.getItems().addAll(todoItems);
            } else if (!inprogressItems.isEmpty() && listView == listInProgress) {
                listView.getItems().addAll(inprogressItems);
            } else if (!reviewItems.isEmpty() && listView == listReview) {
                listView.getItems().addAll(reviewItems);
            } else if (!doneItems.isEmpty() && listView == listDone) {
                listView.getItems().addAll(doneItems);
            }
        }
    }

    public Border paintPriority(int priority) {
        // Border itemBorder = new Border(new BorderStroke(Color.GREENYELLOW,BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 0, 0, 4)));
        Border itemBorder = null;
        if (priority == 1) {
            itemBorder = new Border(new BorderStroke(Color.web("0xDDDDDD"),BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 0, 0, 3)));
        } else if (priority == 2) {
            itemBorder = new Border(new BorderStroke(Color.web("0xcbea96"),BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 0, 0, 3)));
        } else if (priority == 3) {
            itemBorder = new Border(new BorderStroke(Color.web("0x41b337"),BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 0, 0, 3)));
        } else if (priority == 4) {
            itemBorder = new Border(new BorderStroke(Color.web("0xffe56b"),BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 0, 0, 3)));
        } else if (priority == 5) {
            itemBorder = new Border(new BorderStroke(Color.web("0xE56767"),BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 0, 0, 3)));
        }
        return itemBorder;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            reloadBoard();
        } catch (Exception e) {
            e.printStackTrace();
        }
        isUpdated.addListener((observable, oldValue, newValue) -> {
            // Only if completed
            if (newValue == true) {
                reloadBoard();
                isUpdated.setValue(false);
            }

        });
    }

    public void reloadBoard() {
        uiHelper.loadProjectBreadcrumbs(projectBreadcrumb);
        ArrayList<Activity> activitiesList = new ArrayList<>();

        ObjectId sprintId = Session.getCurrentSprintId();
        if (sprintId == null) {
            sprintTitle.setText("No active sprint");
            sprintPeriod.setText("Please create and start sprint by clicking \"Start new sprint\" button.");
        } else {
            sprintTitle.setText(sprintController.getSprintName(sprintId));
            LocalDate sprintStartDate = sprintController.getSprintDate(sprintId, "startDate");
            LocalDate sprintEndDate = sprintController.getSprintDate(sprintId, "endDate");
            String startDateText = sprintStartDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
            String endDateText = sprintEndDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
            sprintPeriod.setText("Sprint period: " + startDateText + " - " + endDateText);

            activitiesList = activityController.getSprintActivities(sprintId);
        }

        loadListViews(activitiesList);
    }

    @FXML
    private void btnActivityItemAssign(MouseEvent event) {
        System.out.println("Button pressed: " + event.getSource());
    }

    @FXML
    private void userSearchOnKeyReleased(KeyEvent event) {
        /*tblActivities.getItems().clear();
        activityController.activityName = activitiesSearch.getText();*/
    }

    public static class ListCellItem {
        private final String name;
        private final String type;
        private final int priority;

        public ListCellItem(String name, String type, int priority) {
            this.name = name;
            this.type = type;
            this.priority = priority;
        }

        public String getName() {
            return name;
        }
        public String getType() {
            return type;
        }
        public int getPriority() {
            return priority;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public boolean validateNewSprintDate() {
        boolean valid;
        LocalDate projectStartDate = Session.getProjectStartDate();
        LocalDate projectEndDate = Session.getProjectEndDate();

        ObjectId projectId = Session.getOpenProjectId();
        ArrayList<Sprint> projectSprints = sprintController.getProjectSprintList(projectId);

        if (projectSprints.isEmpty() || projectSprints == null) {
            valid = false;
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

            if (day == DayOfWeek.SATURDAY) {
                sprintStartDate = lastSprintEndDate.plusDays(2);
            } else {
                sprintStartDate = lastSprintEndDate.plusDays(1);
            }

            if ((sprintStartDate.compareTo(projectEndDate) >= 0)) {
                    valid = true;
            } else {
                valid = false;
            }
        }
        return valid;
    }

}