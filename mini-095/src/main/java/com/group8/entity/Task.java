package com.group8.entity;
import java.util.*;
import java.time.LocalDate;

public class Task extends Activity{
    public Task(String name, String content, LocalDate startDate, LocalDate endDate, 
    Map<String, User> teamMembers, String priority, String id) {

        super(name, content, startDate, endDate, teamMembers, priority, id);
        

    }

    @Override
    public String toString() {
        return "ID: " + this.getId() +  " " + this.getName() +
        " : " + this.getContent() + " Start: " + this.getStartDate() + " End: " + this.getEndDate() +
         "priority: " + this.getPriority() + " Completion Status: " + this.getCompletion() ;
    }
}
