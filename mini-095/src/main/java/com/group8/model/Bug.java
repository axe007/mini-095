package com.group8.model;


import java.time.LocalDate;
import java.util.ArrayList;
import org.bson.types.ObjectId;

public class Bug extends Activity {
    public Bug() {
        super();
    }

    public Bug(ObjectId projectId, String name, String description, LocalDate startDate, LocalDate endDate, String priority, String type) {

        super(projectId, name, description, startDate, endDate, priority, type);

    }
    
    @Override
    public String toString() {
        return "ID: " + this.getId() +  " " + this.getName() +
        " : " + this.getDescription() + " Start: " + this.getStartDate() + " End: " + this.getEndDate() +
         "priority: " + this.getPriority() + " Completion Status: " + this.getCompletion() ;
    }
}
