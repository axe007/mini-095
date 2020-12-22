package com.group8.entity;

import java.time.LocalDate;

public class ActivityForGanttChart {
    // TODO: merge with Activity class
    private String title;
    private LocalDate estimateStartDate;
    private LocalDate estimateEndDate;
    private LocalDate actualStartDate;
    private LocalDate actualEndDate;
    private String activityID;

    public ActivityForGanttChart(String title, String activityID, LocalDate estimateStartDate,
            LocalDate estimateEndDate) {
        this(title, activityID, estimateStartDate, estimateEndDate, null, null);

    }

    public ActivityForGanttChart(String title, String activityID, LocalDate estimateStartDate,
            LocalDate estimateEndDate, LocalDate actualStartDate) {
        this(title, activityID, estimateStartDate, estimateEndDate, actualStartDate, null);
    }

    public ActivityForGanttChart(String title, String activityID, LocalDate estimateStartDate,
            LocalDate estimateEndDate, LocalDate actualStartDate, LocalDate actualEndDate) {
        this.title = title;
        this.activityID = activityID;
        this.estimateStartDate = estimateStartDate;
        this.estimateEndDate = estimateEndDate;
        this.actualStartDate = actualStartDate;
        this.actualEndDate = actualEndDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getEstimateStartDate() {
        return estimateStartDate;
    }

    public void setEstimateStartDate(LocalDate estimateStartDate) {
        this.estimateStartDate = estimateStartDate;
    }

    public LocalDate getEstimateEndDate() {
        return estimateEndDate;
    }

    public void setEstimateEndDate(LocalDate estimateEndDate) {
        this.estimateEndDate = estimateEndDate;
    }

    public LocalDate getActualStartDate() {
        return actualStartDate;
    }

    public void setActualStartDate(LocalDate actualStartDate) {
        this.actualStartDate = actualStartDate;
    }

    public LocalDate getActualEndDate() {
        return actualEndDate;
    }

    public void setActualEndDate(LocalDate actualEndDate) {
        this.actualEndDate = actualEndDate;
    }

    public String getActivityID() {
        return activityID;
    }

    public void setActivityID(String activityID) {
        this.activityID = activityID;
    }

}
