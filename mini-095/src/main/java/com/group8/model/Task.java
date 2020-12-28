package com.group8.model;
import com.group8.model.User;

import java.util.*;
import java.time.LocalDate;
import org.bson.types.ObjectId;

public class Task extends Activity{
    public Task() {
        super();
    }

    // public Task(ObjectId projectId, String name, String description, LocalDate startDate, LocalDate endDate, String priority, String type) {

    //     super(projectId, name, description, startDate, endDate, priority, type);

    // }
    public Task(String projectName, String name, String description, LocalDate startDate, LocalDate endDate, String priority, String type) {

        super(projectName, name, description, startDate, endDate, priority, type);

    }
    @Override
    public String toString() {
        return "ID: " + this.getId() +  " " + this.getName() +
        " : " + this.getDescription() + " Start: " + this.getStartDate() + " End: " + this.getEndDate() +
         "priority: " + this.getPriority() + " Completion Status: " + this.getCompletion() ;
    }
}
