package com.group8.model;
import com.group8.model.User;
import org.bson.types.ObjectId;

import java.util.*;
import java.time.LocalDate;

public class Task extends Activity {

    private ActivityStatus status;
    private Double estimatedHours;

    public Task() {}

    public Task(String name, String description, LocalDate startDate, LocalDate endDate, Double estimatedHours, double priority) {
        super(name, description, startDate, endDate, priority);
        this.status = ActivityStatus.TODO;
        this.estimatedHours = estimatedHours;
    }

    public double getEstimatedHours() { return this.estimatedHours; }
    public ActivityStatus getActivityStatus() { return this.status; }

    public void setEstimatedHours(double estimatedHours) { this.estimatedHours = estimatedHours; }
    public void setActivityStatus(ActivityStatus status) { this.status = status; }

    @Override
    public String toString() {
        return "ID: " + this.getId() +  " " + this.getName() +
        " : " + this.getDescription() + " Start: " + this.getStartDate() + " End: " + this.getEndDate() +
         "priority: " + this.getPriority() + " Current Status: " + this.getStatus();
    }
}
