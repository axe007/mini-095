package com.group8.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.group8.model.Activity;
import com.group8.model.Bug;
import com.group8.model.Task;
import com.group8.model.User;
// import com.group8.model.UserStory;
import com.group8.helper.Helper;
import org.bson.types.ObjectId;

public class ActivityController {
    private static DatabaseController mongoDb = new DatabaseController();

    public boolean createActivity(String projectName, String name, String description, LocalDate startDate, LocalDate endDate, String priority, String type) {
        Activity newActivity;

        if (type.equals("task")) {
            // newActivity = new Task(projectId, name, description, startDate, endDate, priority, type);
            newActivity = new Task(projectName, name, description, startDate, endDate, priority, type);

            // users.add(developer);
        } else {
            newActivity = new Bug(projectName, name, description, startDate, endDate, priority, type);
            // newActivity = new Bug(projectId, name, description, startDate, endDate, priority, type);
            // users.add(manager);
        }

        // if (type.equals("task")) {
        //     newActivity = new Task(projectId, name, description, startDate, endDate, priority, type);
        //     // users.add(developer);
        // } else if (type.equals("bug")){
        //     newActivity = new Bug(projectId, name, description, startDate, endDate, priority, type);
        //     // users.add(manager);
        // } else {
        //     // newActivity = new UserStory( projectId,  name,  description,  startDate,  endDate,  priority,type, acceptanceCriteria[0]);  
        // }

        mongoDb.getActivityCollection().insertOne(newActivity);
        System.out.println("New activity created successfully!");

        return true;
    }
    public List<Activity> getActivityList() {
        List<Activity> activities = mongoDb.getActivityCollection().find().into(new ArrayList<Activity>());
        return activities;
    }

    // public void createActivity(String activityType) {

    //     // General Activity attributes
    //     Helper helper = new Helper();
    //     String content;
    //     String name;
    //     LocalDate startDate;
    //     LocalDate endDate;
    //     ArrayList<User> teamMembers = new ArrayList<>();
    //     String priority;
    //     String id = "1"; // TODO: fix

    //     // User Story attributes
    //     double storyPoints;
    //     String acceptanceCriteria;

    //     // Task
    //     // TODO: add task attributes here

    //     // Bug
    //     // TODO: add bug attributes here

    //     // 1. ask for generic input
    //     System.out.println("Enter name");
    //     name = helper.getString();

    //     System.out.println("Enter content");
    //     content = helper.getString();

    //     System.out.println("Enter start date");
    //     startDate = null; // TODO:

    //     System.out.println("Enter end date");
    //     endDate = null;

    //     System.out.println("Enter team members");
    //     teamMembers.add(null); // TODO: Needs to be fixed, should be able to choose from a list or something
    //                            // similar

    //     System.out.println("Enter priority");
    //     priority = helper.getString();

    //     if (activityType.equals("UserStory")) {
    //         System.out.println("Enter team story points");
    //         storyPoints = helper.getDouble();

    //         System.out.println("Enter acceptance criteria");
    //         acceptanceCriteria = helper.getString();

    //     } else if (activityType.equals("Bug")) {
    //         // TODO: Under construction, Bug class not created yet

    //         // activityModel.createActivity(activityType, name, content, startDate, endDate,
    //         // teamMembers, priority, id, null, null);

    //     } else if (activityType.equals("Task")) {
    //         // TODO: Under construction, Task class not created yet

    //     } else {
    //         System.out.println("Oh no, something went wrong... :'( ");
    //     }

    //     // 2. ask for specific input
    //     // 3. call the method
    // }
    // public void logTime(Developer dev, String taskId, String projectId) {
    //     Map<String, Task> newMap = new HashMap<String, Task>(); 
    //     if (dev.getProjectTaskMap().containsKey(dev.getProjectMap().get(projectId))) {
    //         newMap = dev.getProjectTaskMap().get(dev.getProjectMap().get(projectId)); 
    //     }
    //     if (newMap.containsKey(taskId)) {
    //         Task newTask = newMap.get(taskId); 
    //         long hours = helper.getLong(); 
    //         TimeTracker newTime = new TimeTracker(hours, null); 
    //         newTime.setStartDateTime(newTime.getStartDateTime(hours)); 
    //         newTask.getTimeTrackingMap().put(dev, newTime); 
    //         System.out.println("Press 1 to modify your start time"); 
    //         String userInput = helper.getMenuInput(); // Calling Helper method
    //         if (userInput.equals("1")) {
    //             newTime.setStartDateTime();
    //         }
            
    //     }
    // public void assignActivity(String activityType, String activityId, String assigneeId, Project project) {
        
    //     if (project.getActivities().containsKey(activityId)) {
    //         if (activityType.equals("UserStory")) {
    //             UserStory newUserStory = (UserStory) project.getActivities().get(activityId);
    //             newUserStory.addMember(project.getDeveloperTeam().get(assigneeId));
    //         } else {
    //             Task newTask = (Task) project.getActivities().get(activityId);
    //             newTask.addMember(project.getDeveloperTeam().get(assigneeId));
    //         }
    //     }
    // }

    // public void changeActivityStatus(String activityId, Project project) {
    //     System.out.println("Please choose your option"); 
    //     System.out.println("1. To-do"); 
    //     System.out.println("2. In progress"); 
    //     System.out.println("3. Review"); 
    //     System.out.println("4. Done"); 

    //     String input = helper.getString(); 
    //     switch (input) {
    //         case "1":
    //         project.getActivities().get(activityId).setStatus(ActivityStatus.TODO);
    //         case "2":
    //         project.getActivities().get(activityId).setStatus(ActivityStatus.INPROGRESS);
    //         case "3":
    //         project.getActivities().get(activityId).setStatus(ActivityStatus.REVIEW);
    //         case "4":
    //         project.getActivities().get(activityId).setStatus(ActivityStatus.DONE);

    //     }
    // }

}
