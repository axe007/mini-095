package com.group8.model;

import org.bson.types.ObjectId;
import java.time.LocalDate;
import java.util.ArrayList;

public abstract class Activity implements Comparable<Activity> {

    private ObjectId id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private ArrayList<ObjectId> assigneeList;
    private double priority;
    private String activityStatus;

    public Activity() {}

    public Activity(String name, String description, LocalDate startDate, LocalDate endDate, double priority) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.assigneeList = null;
        this.priority = priority;
        this.activityStatus = "TODO";
    }

    // Getters
    public ObjectId getId(){
        return this.id;
    }
    public String getName() {
        return this.name; 
    }
    public String getDescription() {
        return this.description;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public double getPriority() {
        return this.priority ;
    }
    public String getActivityStatus() { return activityStatus; }
    public String getActivityType() { return getClass().getSimpleName(); }
    public ArrayList<ObjectId> getAssigneeList() { return assigneeList; }

    // Setters
    public void setId(ObjectId id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name; 
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate; 
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    public void setPriority(double priority) {
        this.priority = priority;
    }
    public void setActivityStatus(String status) {
        this.activityStatus = status;
    }
    public void setAssigneeList(ArrayList<ObjectId> assigneeList) { this.assigneeList = assigneeList; }

    @Override
    public String toString() {
        return "ID: " + this.id +  " " + this.name +
         " : " + this.description + " Start: " + this.startDate + " End: " + this.endDate +
          "priority: " + this.priority;
    }

    public String getClassName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public int compareTo(Activity o) {
        return getStartDate().compareTo(o.getStartDate());
    }
}