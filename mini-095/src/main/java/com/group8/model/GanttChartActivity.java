package com.group8.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class GanttChartActivity {
    // MARK: For trial only
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private int topLevelID;
    private int middleLevelID;
    private int bottomLevelID;
    private ArrayList<Integer> workingSchedule;

    // public GanttChartActivity(String title, LocalDate startDate, LocalDate
    // endDate, int topLevelID, int middleLevelID,
    // int bottomLevelID, ArrayList<Integer> workingSchedule) {
    // this.title = title;
    // this.startDate = startDate;
    // this.endDate = endDate;
    // this.topLevelID = topLevelID;
    // this.middleLevelID = middleLevelID;
    // this.bottomLevelID = bottomLevelID;
    // this.workingSchedule = workingSchedule;
    // }

    public GanttChartActivity(String title, LocalDate startDate, LocalDate endDate, int topLevelID, int middleLevelID,
            int bottomLevelID) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.topLevelID = topLevelID;
        this.middleLevelID = middleLevelID;
        this.bottomLevelID = bottomLevelID;
        // this.workingSchedule = new ArrayList<>();
    }

    // public GanttChartActivity(String title, LocalDate startDate, LocalDate
    // endDate, int topLevelID, int middleLevelID,
    // int bottomLevelID, LocalDate projectStartDate, LocalDate projectEndDate) {
    // this.title = title;
    // this.startDate = startDate;
    // this.endDate = endDate;
    // this.topLevelID = topLevelID;
    // this.middleLevelID = middleLevelID;
    // this.bottomLevelID = bottomLevelID;
    // setWorkingSchedule(projectStartDate, projectEndDate);

    // }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getTopLevelID() {
        return topLevelID;
    }

    public void setTopLevelID(int topLevelID) {
        this.topLevelID = topLevelID;
    }

    public int getMiddleLevelID() {
        return middleLevelID;
    }

    public void setMiddleLevelID(int middleLevelID) {
        this.middleLevelID = middleLevelID;
    }

    public int getBottomLevelID() {
        return bottomLevelID;
    }

    public void setBottomLevelID(int bottomLevelID) {
        this.bottomLevelID = bottomLevelID;
    }

    // public ArrayList<Integer> getWorkingSchedule() {
    // return workingSchedule;
    // }

    // public void setWorkingSchedule(ArrayList<Integer> workingSchedule) {
    // this.workingSchedule = workingSchedule;
    // }

    // public void setWorkingSchedule(LocalDate projectStartDate, LocalDate
    // projectEndDate) {
    // ArrayList<Integer> temp = new ArrayList<>();
    // // Get Start day of the project
    // Locale locale = new Locale("SE");
    // int currentWeek = projectStartDate.get(WeekFields.of(locale).weekOfYear()) -
    // 1;
    // int currentYear = projectStartDate.getYear();
    // Calendar cal = Calendar.getInstance();
    // cal.setWeekDate(currentYear, currentWeek, 1);
    // //
    // LocalDate localDate = LocalDateTime.ofInstant(cal.toInstant(),
    // cal.getTimeZone().toZoneId()).toLocalDate();
    // int count = 0;

    // for (LocalDate i = localDate; i.isBefore(projectEndDate); i.plusDays(1)) {
    // if (i.isEqual(projectStartDate) || i.isEqual(projectEndDate)) {
    // temp.add(1);
    // } else if (i.isAfter(projectStartDate) && i.isBefore(projectEndDate)) {
    // temp.add(1);

    // } else {
    // temp.add(0);
    // }
    // count++;
    // }
    // this.workingSchedule = temp;
    // System.out.println("Class count: " + count);
    // }

}
