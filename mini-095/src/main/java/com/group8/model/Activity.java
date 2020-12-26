package com.group8.model;

import org.bson.types.ObjectId;
import java.time.LocalDate;
import java.util.ArrayList;

public abstract class Activity {

    private ObjectId id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private ArrayList<ObjectId> assignee;
    private double priority;
    private ActivityStatus status;

    enum ActivityStatus {
        TODO("To do"),
        INPROGRESS("In progress"),
        REVIEW("Review"),
        DONE("Done");

        private final String simpleName;
        ActivityStatus(String simpleName) {
            this.simpleName = simpleName;
        }

        @Override
        public String toString() {
            return simpleName;
        }
    }

    public Activity() {}

    public Activity(String name, String description, LocalDate startDate, LocalDate endDate, double priority) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.assignee = null;
        this.priority = priority;
        this.status = ActivityStatus.TODO;
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
    public ActivityStatus getStatus() {return this.status; }

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
    public void setActivityStatus(ActivityStatus status){
        this.status = status;
    }

    @Override
    public String toString() {
        return "ID: " + this.id +  " " + this.name +
         " : " + this.description + " Start: " + this.startDate + " End: " + this.endDate +
          "priority: " + this.priority + " Completion Status: " + this.status ;
    }
}