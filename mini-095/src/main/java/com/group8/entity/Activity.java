package com.group8.entity;

import com.group8.model.User;

import java.time.LocalDate;
import java.util.ArrayList;

public abstract class Activity {

    private String content;
    private String name; 
    private LocalDate startDate;
    private LocalDate endDate; 
    private ArrayList<User> teamMembers;
    private String priority;
    private boolean completion; 
    private String id;

    public Activity (String name, String content, LocalDate startDate, LocalDate endDate, ArrayList<User> teamMembers, String priority, String id) {
        this.name = name; 
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.teamMembers = teamMembers;
        this.priority = priority;
        this.id = id;
        this.completion = false;
    }

    public void setPriority(String priority) {
        this.priority = priority; 
    }

    public String getPriority() {
        return this.priority ; 
    }
        public String getName() {
        return this.name; 
    }
    public void setName(String name) {
        this.name = name; 
    }

    public String getContent() {
        return this.content; 
    }
    public void setContent(String content) {
        this.content = content; 
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate; 
    }

    public LocalDate getStartDate() {
        return startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    public void setId (String id) {
        this.id = id;
    }
    
    public String getId (){
        return this.id;
    }
    
    public boolean getCompletion() {
        return this.completion;
    }
    
    public void setCompletion (boolean completion){
        this.completion =completion;
    }

    @Override
    public String toString() {
        return "ID: " + this.id +  " " + this.name +
         " : " + this.content + " Start: " + this.startDate + " End: " + this.endDate +
          "priority: " + this.priority + " Completion Status: " + this.completion ;
    }
}
