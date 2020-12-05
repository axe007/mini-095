package com.group8.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Activity {

    private String content;
    private String name; 
    private LocalDate startDate;
    private LocalDate endDate; 
    private Map<String, User> teamMembers;
    private String priority;
    private boolean completion; 
    private String id; 
    private Map<User, List<TimeTracker>> timeTrackingMap; 
    private ActivityStatus status; 
    public Activity (String name, String content, LocalDate startDate, LocalDate endDate, String priority, String id) {
        this.name = name; 
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.teamMembers = new HashMap<String, User>();
        this.priority = priority;
        this.id = id;
        this.completion = false;
        this.status = ActivityStatus.TODO; 
        this.timeTrackingMap = new HashMap<User, List<TimeTracker>>(); 
    }

    public  Map<User, List<TimeTracker>> getTimeTrackingMap() {
        return this.timeTrackingMap; 
    }
    public void setPriority(String priority) {
        this.priority = priority; 
    }
    public Map<String, User> getTeamMembers() {
        return this.teamMembers; 
    }
    public void addMember(User member) {
        this.teamMembers.put(member.getUuid(), member); 
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

    public ActivityStatus getStatus() {
        return status;
    }

    public void setStatus(ActivityStatus status) {
		this.status = status;
	}

}
