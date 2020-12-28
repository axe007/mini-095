package com.group8.model;

// import com.group8.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import org.bson.types.ObjectId;

public abstract class Activity {

    private String description;
    private String name; 
    private LocalDate startDate;
    private LocalDate endDate; 
    private ArrayList<ObjectId> developerTeam;
    private String priority;
    private boolean completion; 
    private ObjectId id;
    // private ObjectId projectId; 
    private String type; // "task", "bug", "user story"

    public Activity() {

    }
    // public Activity (ObjectId projectId, String name, String description, LocalDate startDate, LocalDate endDate, String priority, String type) {
    //     this.projectId = projectId;
    //     this.name = name; 
    //     this.description = description;
    //     this.startDate = startDate;
    //     this.endDate = endDate;
    //     this.developerTeam =new ArrayList<>();
    //     this.priority = priority;
    //     this.completion = false;
    //     this.type = type; 
    // }
    private String projectName; 
    public Activity (String projectName, String name, String description, LocalDate startDate, LocalDate endDate, String priority, String type) {
        this.projectName = projectName;
        this.name = name; 
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.developerTeam =new ArrayList<>();
        this.priority = priority;
        this.completion = false;
        this.type = type; 
    }
    public ObjectId getId() {
        return id;
    }
    public String getActivityType() {
        return type; 
    }
    public void setActivityType(String type) {
        this.type = type; 
    }
    // public ObjectId getProjectId() {
    //     return projectId; 
    // }
    // public void setProjectId(ObjectId projectId) {
    //     this.projectId = projectId;
    // }
    public String getProjectName() {
        return projectName; 
    }
    public void setProjectName(String projectName) {
        this.projectName = projectName; 
    }
    public void setId(ObjectId id) {
        this.id = id;
    }
    public ArrayList<ObjectId> getDeveloperTeam() {
        return developerTeam;
    }

    public void setDeveloperTeam(ArrayList<ObjectId> developerTeam) {
        this.developerTeam = developerTeam;
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

    public String getDescription() {
        return this.description; 
    }
    public void setDescription(String description) {
        this.description = description; 
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
    public boolean getCompletion() {
        return this.completion;
    }
    
    public void setCompletion (boolean completion){
        this.completion =completion;
    }

    @Override
    public String toString() {
        return "ID: " + this.id +  " " + this.name +
         " : " + this.description + " Start: " + this.startDate + " End: " + this.endDate +
          "priority: " + this.priority + " Completion Status: " + this.completion ;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
