package com.group8.controllers.viewcontroller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import com.group8.constants.FakeDataForGanttChart;
import com.group8.model.Activity;
import com.group8.model.GanttChartActivity;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.util.Callback;

public class GanttChartViewController implements Initializable {
    private static int currentWeekIndex = 0;
    private static int currentDayIndex = 0;
    private ArrayList<GanttChartActivity> topLevelList;
    private ArrayList<GanttChartActivity> middleLevelList;
    private ArrayList<GanttChartActivity> bottomLevelList;
    private GanttChartActivity projectActivityItem = new GanttChartActivity("Project", LocalDate.of(2020, 10, 20),
            LocalDate.of(2020, 12, 20), 0, 0, 0);
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

        ArrayList<GanttChartActivity> actitiviesList = new ArrayList();
        // ObservableList<GanttChartActivity> actitiviesList =
        // FXCollections.observableArrayList();
        topLevelList = new ArrayList();
        middleLevelList = new ArrayList();
        bottomLevelList = new ArrayList();
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
        TreeItem projectItem = new TreeItem<>(new GanttChartActivity("Project", LocalDate.of(2020, 10, 20),
                LocalDate.of(2020, 12, 20), 0, 0, 0, LocalDate.of(2020, 10, 20), LocalDate.of(2020, 12, 20)));

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
        TreeTableColumn<GanttChartActivity, String> treeTableColumn2 = new TreeTableColumn<>("StartDate");
        TreeTableColumn<GanttChartActivity, String> treeTableColumn3 = new TreeTableColumn<>("EndDate");

        treeTableColumn1.setCellValueFactory(new TreeItemPropertyValueFactory<>("title"));
        treeTableColumn2.setCellValueFactory(new TreeItemPropertyValueFactory<>("startDate"));
        treeTableColumn3.setCellValueFactory(new TreeItemPropertyValueFactory<>("endDate"));
        ganttChartTreeTableView.getColumns().add(treeTableColumn1);
        ganttChartTreeTableView.getColumns().add(treeTableColumn2);
        ganttChartTreeTableView.getColumns().add(treeTableColumn3);

        // calender header
        int weeksOfProject = (int) ChronoUnit.WEEKS.between(projectActivityItem.getStartDate(),
                projectActivityItem.getEndDate());
        LocalDate currentDate = projectActivityItem.getStartDate();
        TreeTableColumn<GanttChartActivity, String> currentWeekColumn;
        Locale locale = new Locale("SE");
        int count = 0;

        // System.out.println(weeksOfProject);
        while (currentDate.get(WeekFields.of(locale).weekOfYear()) <= projectActivityItem.getEndDate()
                .get(WeekFields.of(locale).weekOfYear())) {
            int currentWeek = currentDate.get(WeekFields.of(locale).weekOfYear()) - 1;
            int currentYear = currentDate.getYear();
            currentWeekColumn = new TreeTableColumn<>(currentYear + "Week " + (currentWeek + 1));

            Calendar cal = Calendar.getInstance();
            cal.setWeekDate(currentYear, currentWeek, 1);
            for (int i = 0; i < 7; i++) {
                // System.out.println(cal.getTime());
                SimpleDateFormat format = new SimpleDateFormat("MM/dd");

                Date date = cal.getTime();
                currentDayIndex = i;
                TreeTableColumn<GanttChartActivity, Boolean> currentDateColumn = new TreeTableColumn<>(
                        format.format(date));
                currentDateColumn.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue()
                        .getValue().getWorkingSchedule().get(currentWeekIndex * 7 + currentDayIndex)));

                currentWeekColumn.getColumns().add(currentDateColumn);
                cal.add(Calendar.DATE, 1);
                // count++;
            }
            ganttChartTreeTableView.getColumns().add(currentWeekColumn);

            currentDate = currentDate.plusDays(7);
            currentWeekIndex++;
        }
        // System.out.println("Table count: " + count);
        // TableColumn firstEmailCol = new TableColumn("Primary");
        // TableColumn secondEmailCol = new TableColumn("Secondary");

        // emailCol.getColumns().addAll(firstEmailCol, secondEmailCol);

        ganttChartTreeTableView.setRoot(projectItem);
    }

    public void matchDateToCalender() {

    }

}
