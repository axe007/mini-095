package com.group8.controllers.viewcontroller;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.ResourceBundle;

import com.group8.controllers.ActivityController;
import com.group8.model.Activity;
import com.group8.model.Bug;
import com.group8.model.GanttChartActivity;
import com.group8.model.Session;
import com.group8.model.Task;
import com.group8.model.UserStory;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class GanttChartViewController implements Initializable {

    private static int columnCount = 0;
    private ArrayList<GanttChartActivity> topLevelList;
    private ArrayList<GanttChartActivity> middleLevelList;
    private ArrayList<GanttChartActivity> bottomLevelList;
    private ArrayList<Activity> activitiesList = new ArrayList<>();

    private static LocalDate calenderStartDate;
    private static LocalDate calenderEndDate;
    private static int numberOfCalenderDays;
    @FXML
    private Label findActivityLabel;
    @FXML
    private TextField activitySearchTextField;
    @FXML
    private Button searchButton;
    @FXML
    private TreeTableView<GanttChartActivity> ganttChartTreeTableView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GanttChartActivity projectActivityItem = new GanttChartActivity(Session.getOpenProjectName(),
                Session.getProjectStartDate(), Session.getProjectEndDate(), null, null, null);
        loadActivitiesData();
        if (projectActivityItem.getStartDate().getDayOfWeek().equals(DayOfWeek.MONDAY)) {
            calenderStartDate = projectActivityItem.getStartDate();
        } else {
            calenderStartDate = projectActivityItem.getStartDate().with(DayOfWeek.MONDAY);
        }
        if (projectActivityItem.getEndDate().getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            calenderEndDate = projectActivityItem.getEndDate();
        } else {
            calenderEndDate = projectActivityItem.getEndDate().plusWeeks(1).with(DayOfWeek.SUNDAY);
        }

        numberOfCalenderDays = (int) ChronoUnit.DAYS.between(calenderStartDate, calenderEndDate) + 1;

        topLevelList = new ArrayList<>();
        middleLevelList = new ArrayList<>();
        bottomLevelList = new ArrayList<>();

        TreeItem<GanttChartActivity> projectItem = new TreeItem<>(projectActivityItem);
        convertActivityToGanttChartActivity();

        for (GanttChartActivity topGanttChartActivity : topLevelList) {
            TreeItem<GanttChartActivity> topLevelItem = new TreeItem<>(topGanttChartActivity);
            for (GanttChartActivity middleGanttChartActivity : middleLevelList) {
                if (middleGanttChartActivity.getMiddleLevelID().equals(topGanttChartActivity.getTopLevelID())) {
                    TreeItem<GanttChartActivity> middleLevelItem = new TreeItem<>(middleGanttChartActivity);
                    for (GanttChartActivity bottomGanttChartActivity : bottomLevelList) {
                        if (middleGanttChartActivity.getTopLevelID()
                                .equals(bottomGanttChartActivity.getMiddleLevelID())) {
                            TreeItem<GanttChartActivity> bottomLevelItem = new TreeItem<>(bottomGanttChartActivity);
                            middleLevelItem.getChildren().add(bottomLevelItem);
                            middleLevelItem.setExpanded(true);
                        }
                    }
                    topLevelItem.getChildren().add(middleLevelItem);
                    topLevelItem.setExpanded(true);
                }
            }
            projectItem.getChildren().add(topLevelItem);
            projectItem.setExpanded(true);

        }

        TreeTableColumn<GanttChartActivity, String> treeTableColumn1 = new TreeTableColumn<>("Name");
        treeTableColumn1.setPrefWidth(180.0);
        treeTableColumn1.setMinWidth(180.0);
        TreeTableColumn<GanttChartActivity, String> treeTableColumn2 = new TreeTableColumn<>("Start Date");
        treeTableColumn2.setPrefWidth(80);
        treeTableColumn2.setMinWidth(80.0);
        TreeTableColumn<GanttChartActivity, String> treeTableColumn3 = new TreeTableColumn<>("End Date");
        treeTableColumn3.setPrefWidth(80.0);
        treeTableColumn3.setMinWidth(80.0);

        treeTableColumn1.setCellValueFactory(new TreeItemPropertyValueFactory<>("title"));
        treeTableColumn2.setCellValueFactory(new TreeItemPropertyValueFactory<>("startDate"));
        treeTableColumn3.setCellValueFactory(new TreeItemPropertyValueFactory<>("endDate"));

        ganttChartTreeTableView.getColumns().add(treeTableColumn1);
        ganttChartTreeTableView.getColumns().add(treeTableColumn2);
        ganttChartTreeTableView.getColumns().add(treeTableColumn3);

        LocalDate currentDate = calenderStartDate;
        TreeTableColumn<GanttChartActivity, String> currentWeekColumn;
        Locale locale = new Locale("EN");
        while (currentDate.isBefore(calenderEndDate)) {

            int currentWeek = currentDate.get(WeekFields.of(locale).weekOfYear());
            int currentYear = currentDate.getYear();

            String firstDayOfWeekString = currentDate.format(DateTimeFormatter.ofPattern("LLL d"));

            currentWeekColumn = new TreeTableColumn<>(
                    currentYear + " Week " + (currentWeek) + " (" + firstDayOfWeekString + ")");
            currentWeekColumn.getStyleClass().add("gantt-chart-week-column");

            LocalDate tempDate = currentDate;

            for (int i = 0; i < 7; i++) {

                TreeTableColumn<GanttChartActivity, String> currentDateColumn = new TreeTableColumn<>(
                        tempDate.format(DateTimeFormatter.ofPattern("dd")));

                if (tempDate.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
                    currentDateColumn.getStyleClass().add("gantt-chart-day-column-weekend");
                } else if (tempDate.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                    currentDateColumn.getStyleClass().add("gantt-chart-day-column-weekend");
                    currentDateColumn.setStyle("-fx-border-color: transparent red transparent transparent");

                } else {
                    currentDateColumn.getStyleClass().add("gantt-chart-day-column-weekday");
                }

                currentWeekColumn.getColumns().add(currentDateColumn);
                currentDateColumn.setCellFactory(
                        new Callback<TreeTableColumn<GanttChartActivity, String>, TreeTableCell<GanttChartActivity, String>>() {
                            @Override
                            public TreeTableCell<GanttChartActivity, String> call(
                                    TreeTableColumn<GanttChartActivity, String> param) {
                                return new TreeTableCell<GanttChartActivity, String>() {

                                    @Override
                                    protected void updateItem(String item, boolean empty) {
                                        setText(null);
                                        getStyleClass().clear();
                                        super.updateItem(item, empty);
                                        GanttChartActivity activity = getTreeTableRow().getItem();
                                        int columnIndex = columnCount % numberOfCalenderDays;
                                        if (activity != null) {
                                            int daysBeforeActivityStart = (int) ChronoUnit.DAYS
                                                    .between(calenderStartDate, activity.getStartDate());
                                            int daysUntilActivityEnd = (int) ChronoUnit.DAYS.between(calenderStartDate,
                                                    activity.getEndDate());

                                            int rowIndex = getTreeTableRow().getIndex() % 7 + 1;

                                            if (columnIndex >= daysBeforeActivityStart
                                                    && columnIndex <= daysUntilActivityEnd) {
                                                int dayOffSets = (int) ChronoUnit.DAYS.between(calenderStartDate,
                                                        projectActivityItem.getStartDate());
                                                setText(Integer.toString(columnIndex - dayOffSets));
                                                setTextFill(Color.GREEN);
                                                getStyleClass().add("gantt-chart-cell" + rowIndex);

                                                // if (columnIndex % 7 == 6) {
                                                // setStyle(
                                                // "-fx-border-color: transparent red transparent transparent");
                                                // }

                                            } else if (columnIndex % 7 == 6 || columnIndex % 7 == 5) {
                                                getStyleClass().add("gantt-chart-day-column-weekend");

                                            } else {
                                                getStyleClass().add("gantt-chart-day-column-weekday");
                                            }
                                        } else if (columnIndex % 7 == 6 || columnIndex % 7 == 5) {
                                            getStyleClass().add("gantt-chart-day-column-weekend");

                                        }
                                        columnCount++;
                                        if (columnCount == numberOfCalenderDays) {
                                            columnCount = 0;
                                        }

                                    }

                                };
                            }
                        });
                tempDate = tempDate.plusDays(1);
            }

            ganttChartTreeTableView.getColumns().add(currentWeekColumn);
            currentDate = currentDate.plusDays(7);
        }

        ganttChartTreeTableView.setRoot(projectItem);
    }

    public void convertActivityToGanttChartActivity() {
        for (Activity activity : activitiesList) {

            if (activity instanceof UserStory) {
                GanttChartActivity ganttUserStory = new GanttChartActivity(activity.getName(), activity.getStartDate(),
                        activity.getEndDate(), activity.getId(), null, null);
                topLevelList.add(ganttUserStory);

            } else if (activity instanceof Task) {
                Task task = (Task) activity;
                GanttChartActivity ganttTask = new GanttChartActivity(task.getName(), task.getStartDate(),
                        task.getEndDate(), task.getId(), task.getParentId(), task.getGrandId());
                if (task.getGrandId() == null && task.getParentId() == null) {
                    topLevelList.add(ganttTask);
                } else if (task.getGrandId() == null) {
                    middleLevelList.add(ganttTask);
                } else {
                    bottomLevelList.add(ganttTask);
                }

            } else if (activity instanceof Bug) {
                Bug bug = (Bug) activity;
                GanttChartActivity ganttBug = new GanttChartActivity(bug.getName(), bug.getStartDate(),
                        bug.getEndDate(), bug.getId(), bug.getParentId(), bug.getGrandId());
                if (bug.getGrandId() == null && bug.getParentId() == null) {
                    topLevelList.add(ganttBug);
                } else if (bug.getGrandId() == null) {
                    middleLevelList.add(ganttBug);
                } else {
                    bottomLevelList.add(ganttBug);
                }

            }
        }
    }

    private void loadActivitiesData() {
        ActivityController activityController = new ActivityController();
        activitiesList = activityController.getActivitiesList();
        Collections.sort(activitiesList, new Comparator<Activity>() {
            public int compare(Activity o1, Activity o2) {
                return o1.getStartDate().compareTo(o2.getStartDate());
            }
        });
    }

    @FXML
    private void handleSearchButton(ActionEvent event) {
        if (!activitySearchTextField.getText().isEmpty()) {
            int count = 0;
            boolean findItem = false;
            ObservableList<TreeItem<GanttChartActivity>> activityList = ganttChartTreeTableView.getRoot().getChildren();
            toploop: for (TreeItem<GanttChartActivity> activity : activityList) {
                count++;
                if (activity.getValue().getTitle().contains(activitySearchTextField.getText())) {
                    findItem = true;
                    break;
                } else {
                    if (!activity.isLeaf()) {
                        for (TreeItem<GanttChartActivity> childItem : activity.getChildren()) {
                            count++;
                            if (childItem.getValue().getTitle().contains(activitySearchTextField.getText())) {
                                findItem = true;
                                break toploop;
                            } else {
                                if (!childItem.isLeaf()) {
                                    for (TreeItem<GanttChartActivity> grandChildItem : childItem.getChildren()) {
                                        count++;
                                        if (grandChildItem.getValue().getTitle()
                                                .contains(activitySearchTextField.getText())) {
                                            findItem = true;
                                            break toploop;
                                        }

                                    }

                                }

                            }
                        }

                    }
                }

            }
            if (findItem) {
                ganttChartTreeTableView.getSelectionModel().clearAndSelect(count);
                System.out.println(count);
            }

        }

    }

}
