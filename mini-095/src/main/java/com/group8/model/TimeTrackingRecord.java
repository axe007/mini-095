package com.group8.model;

import com.group8.controllers.viewcontroller.ReportViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TimeTrackingRecord {

    private String activityType;
    private String activityName;
    private String userFullname;
    private String userRole;
    private double estimatedEffort;
    private double totalHours;
    private String activityStatus;

    public TimeTrackingRecord() { }

    public TimeTrackingRecord(String activityType, String activityName, String userFullname,
                               String userRole, double estimatedEffort, double totalHours, String activityStatus) {
        this.activityType = activityType;
        this.activityName = activityName;
        this.userFullname = userFullname;
        this.userRole = userRole;
        this.estimatedEffort = estimatedEffort;
        this.totalHours = totalHours;
        this.activityStatus = activityStatus;
    }

    public String getActivityType() { return activityType; }
    public String getActivityName() { return activityName; }
    public String getUserFullname() { return userFullname; }
    public String getUserRole() { return userRole; }
    public double getEstimatedEffort() { return estimatedEffort; }
    public double getTotalHours() { return totalHours; }
    public String getActivityStatus() { return activityStatus; }

    public void setActivityType(String activityType) { this.activityType = activityType; }
    public void setActivityName(String activityName) { this.activityName = activityName; }
    public void setUserFullname(String userFullname) { this.userFullname = userFullname; }
    public void setUserRole(String userRole) { this.userRole = userRole; }
    public void setEstimatedEffort(double estimatedEffort) { this.estimatedEffort = estimatedEffort; }
    public void setTotalHours(double totalHours) { this.totalHours = totalHours; }
    public void setActivityStatus(String activityStatus) { this.activityStatus = activityStatus; }
}