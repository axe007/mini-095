package com.group8.controllers.viewcontroller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import com.group8.constants.FakeDataForGanttChart;
import com.group8.model.Activity;
import com.group8.model.GanttChartActivity;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
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
    private GanttChartActivity projectActivityItem = new GanttChartActivity("Project", LocalDate.of(2020, 10, 20),
            LocalDate.of(2020, 12, 20), 0, 0, 0);
    private static LocalDate calenderStartDate;
    private static LocalDate calenderEndDate;
    private static int numberOfCalenderDays;
    @FXML
    private Label findActivityLabel;
    @FXML
    private TextField activitySearchTextField;
    @FXML
    private ComboBox searchDomainComboBox;
    @FXML
    private Button searchButton;
    @FXML
    private TreeTableView<GanttChartActivity> ganttChartTreeTableView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        if (projectActivityItem.getStartDate().getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            calenderStartDate = projectActivityItem.getStartDate();
        } else {
            calenderStartDate = projectActivityItem.getStartDate().minusWeeks(1).with(DayOfWeek.SUNDAY);
        }
        if (projectActivityItem.getEndDate().getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
            calenderEndDate = projectActivityItem.getStartDate();
        } else {
            calenderEndDate = projectActivityItem.getEndDate().plusWeeks(1).with(DayOfWeek.SATURDAY);
        }
        System.out.println(calenderEndDate);
        System.out.println(calenderStartDate);
        numberOfCalenderDays = (int) ChronoUnit.DAYS.between(calenderStartDate, calenderEndDate) + 1;

        ArrayList<GanttChartActivity> actitiviesList = new ArrayList<>();
        // ObservableList<GanttChartActivity> actitiviesList =
        // FXCollections.observableArrayList();
        topLevelList = new ArrayList<>();
        middleLevelList = new ArrayList<>();
        bottomLevelList = new ArrayList<>();
        actitiviesList.add(FakeDataForGanttChart.fakeActivitiy1);
        actitiviesList.add(FakeDataForGanttChart.fakeActivitiy2);
        actitiviesList.add(FakeDataForGanttChart.fakeActivitiy3);
        actitiviesList.add(FakeDataForGanttChart.fakeActivitiy4);
        actitiviesList.add(FakeDataForGanttChart.fakeActivitiy5);

        for (GanttChartActivity ganttChartActivity : actitiviesList) {
            if (ganttChartActivity.getMiddleLevelID() == 0 && ganttChartActivity.getBottomLevelID() == 0) {
                topLevelList.add(ganttChartActivity);
            } else if (ganttChartActivity.getBottomLevelID() == 0) {
                middleLevelList.add(ganttChartActivity);

            } else {
                bottomLevelList.add(ganttChartActivity);
            }
        }
        TreeItem projectItem = new TreeItem<>(
                new GanttChartActivity("Project", LocalDate.of(2020, 10, 20), LocalDate.of(2020, 11, 20), 0, 0, 0));

        for (GanttChartActivity topGanttChartActivity : topLevelList) {
            TreeItem topLevelItem = new TreeItem<>(topGanttChartActivity);
            for (GanttChartActivity middleGanttChartActivity : middleLevelList) {
                if (middleGanttChartActivity.getTopLevelID() == topGanttChartActivity.getTopLevelID()) {
                    TreeItem middleLevelItem = new TreeItem<>(middleGanttChartActivity);
                    for (GanttChartActivity bottomGanttChartActivity : bottomLevelList) {
                        if (middleGanttChartActivity.getMiddleLevelID() == bottomGanttChartActivity
                                .getMiddleLevelID()) {
                            TreeItem bottomLevelItem = new TreeItem<>(bottomGanttChartActivity);
                            middleLevelItem.getChildren().add(bottomLevelItem);
                        }
                    }
                    topLevelItem.getChildren().add(middleLevelItem);
                }
            }
            projectItem.getChildren().add(topLevelItem);
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
        ganttChartTreeTableView.setColumnResizePolicy(ganttChartTreeTableView.CONSTRAINED_RESIZE_POLICY);

        // calender header
        LocalDate currentDate = projectActivityItem.getStartDate();
        TreeTableColumn<GanttChartActivity, String> currentWeekColumn;
        Locale locale = new Locale("EN");

        while (currentDate.get(WeekFields.of(locale).weekOfYear()) <= projectActivityItem.getEndDate()
                .get(WeekFields.of(locale).weekOfYear())) {
            int currentWeek = currentDate.get(WeekFields.of(locale).weekOfYear());
            int currentYear = currentDate.getYear();
            // String firstDayOfWeekString = currentDate.with(DayOfWeek.MONDAY).format(DateTimeFormatter.ofPattern("LLLL dd"));
            String firstDayOfWeekString = currentDate.with(DayOfWeek.MONDAY)
                    .format(DateTimeFormatter.ofPattern("LLL d"));
            /*currentWeekColumn = new TreeTableColumn<>(
                    currentYear + " Week " + (currentWeek + 1) + " " + firstDayOfWeekString);*/
            currentWeekColumn = new TreeTableColumn<>(" Week " + (currentWeek) + " (" + firstDayOfWeekString + ")");
            currentWeekColumn.getStyleClass().add("gantt-chart-week-column");

            Calendar cal = Calendar.getInstance();
            cal.setWeekDate(currentYear, currentWeek, 2);

            for (int i = 0; i < 7; i++) {
                // SimpleDateFormat format = new SimpleDateFormat("MM/dd");
                SimpleDateFormat format = new SimpleDateFormat("dd");

                Date date = cal.getTime();
                TreeTableColumn<GanttChartActivity, String> currentDateColumn = new TreeTableColumn<>(
                        format.format(date));

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);

                if ((calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
                        || (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {
                    currentDateColumn.getStyleClass().add("gantt-chart-day-column-weekend");
                } else {
                    currentDateColumn.getStyleClass().add("gantt-chart-day-column-weekday");
                }

                // currentDateColumn.getStyleClass().add("gantt-chart-day-column");

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

                                        if (activity != null) {
                                            int daysBeforeActivityStart = (int) ChronoUnit.DAYS
                                                    .between(calenderStartDate, activity.getStartDate());
                                            int daysUntilActivityEnd = (int) ChronoUnit.DAYS.between(calenderStartDate,
                                                    activity.getEndDate());
                                            int columnIndex = columnCount % numberOfCalenderDays;

                                            /*if (columnIndex % 7 == 0 || columnIndex % 7 == 6) {
                                                getStyleClass().add("gantt-chart-cell-holiday");
                                            } else if (columnIndex >= daysBeforeActivityStart
                                                    && columnIndex <= daysUntilActivityEnd) {
                                                int dayOffSets = (int) ChronoUnit.DAYS.between(calenderStartDate,
                                                        projectActivityItem.getStartDate());
                                                setText(Integer.toString(columnIndex - dayOffSets));
                                                setTextFill(Color.GREEN);
                                                getStyleClass().add("gantt-chart-cell");
                                            }*/

                                            if (columnIndex >= daysBeforeActivityStart
                                                    && columnIndex <= daysUntilActivityEnd) {
                                                int dayOffSets = (int) ChronoUnit.DAYS.between(calenderStartDate,
                                                        projectActivityItem.getStartDate());
                                                setText(Integer.toString(columnIndex - dayOffSets));
                                                setTextFill(Color.GREEN);
                                                getStyleClass().add("gantt-chart-cell");
                                            }
                                        }
                                        columnCount++;
                                        if (columnCount == numberOfCalenderDays) {
                                            columnCount = 0;
                                        }

                                    }

                                };
                            }
                        });
                cal.add(Calendar.DATE, 1);
            }

            ganttChartTreeTableView.getColumns().add(currentWeekColumn);
            currentDate = currentDate.plusDays(7);
        }

        ganttChartTreeTableView.setRoot(projectItem);
    }

    public void matchDateToCalender() {

    }

}
