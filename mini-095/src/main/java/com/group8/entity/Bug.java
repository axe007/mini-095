package com.group8.entity;

import java.time.LocalDate;
import java.util.ArrayList;

public class Bug extends Activity {

    public Bug(String name, String content, LocalDate startDate, LocalDate endDate, 
    ArrayList<User> teamMembers, String priority, String id) {

        super(name, content, startDate, endDate, teamMembers, priority, id);

    }
    
    @Override
    public String toString() {
        return "ID: " + this.getId() +  " " + this.getName() +
        " : " + this.getContent() + " Start: " + this.getStartDate() + " End: " + this.getEndDate() +
         "priority: " + this.getPriority() + " Completion Status: " + this.getCompletion() ;
    }
}
