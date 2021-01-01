package com.group8.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.group8.model.*;
import com.group8.model.Activity;
import com.group8.helper.Helper;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class ActivityController {

    private static DatabaseController mongoDb = new DatabaseController();
    private ProjectController projectController = new ProjectController();

    public void createActivity(ObjectId grandId, ObjectId parentId, String activityType, String name, String description, LocalDate startDate, LocalDate endDate, double priority, Double storyPoints, Double estimatedHours) {
        Activity newActivity;
        if (activityType.equals("User story")) {
            newActivity = new UserStory(name, description, startDate, endDate, storyPoints, priority);
        } else if (activityType.equals("Task")) {
            newActivity = new Task(grandId, parentId, name, description, startDate, endDate, estimatedHours, priority);
        } else {
            newActivity = new Bug(grandId, parentId, name, description, startDate, endDate, estimatedHours, priority);
        }
        mongoDb.getActivityCollection().insertOne(newActivity);
    }

    public void modifyActivity(ObjectId grandId, ObjectId parentId, String activityType, String name, String description, LocalDate startDate, LocalDate endDate, double priority, Double storyPoints, Double estimatedHours) {
        Activity activity = (Activity) Session.getOpenItem();
        ObjectId id = activity.getId();

        if (activityType.equals("User story")) {
            mongoDb.getActivityCollection().updateOne(eq("_id", id),
                    combine(set("name", name),set("description", description),set("startDate", startDate),set("endDate", endDate),set("priority", priority),set("storyPoints", storyPoints)));
        } else if (activityType.equals("Task")) {
            mongoDb.getActivityCollection().updateOne(eq("_id", id),
                    combine(set("name", name),set("description", description),set("startDate", startDate),set("endDate", endDate),set("priority", priority),
                            set("parentId", parentId),set("grandId", grandId),set("estimatedHours", estimatedHours)));
        } else {
            mongoDb.getActivityCollection().updateOne(eq("_id", id),
                    combine(set("name", name),set("description", description),set("startDate", startDate),set("endDate", endDate),set("priority", priority),
                            set("parentId", parentId),set("grandId", grandId),set("estimatedHours", estimatedHours)));
        }
        System.out.println("Activity details updated!");
    }

    public ArrayList<Activity> getActivitiesList() {
        ObjectId openProject = Session.getOpenProjectId();
        List<ObjectId> projectActivities = projectController.getProjectList(openProject, "activities");
        ArrayList<Activity> activities = new ArrayList<>();
        MongoCollection activitiesCollection = mongoDb.getActivityCollection().withCodecRegistry(mongoDb.createCodecRegistry("Activities"));

        for (ObjectId objectId : projectActivities) {
            Activity activity = (Activity) activitiesCollection.find(eq("_id", objectId)).first();
            if (activity != null && activity.getId().equals(objectId)) {
                activities.add(activity);
            }
        }
        return activities;
    }



    public void updateActivitiesList(String activityName) {
        ObjectId activityId = getActivityId("name", activityName);
        List<ObjectId> projectActivities = new ArrayList<ObjectId>();
        projectActivities.add(activityId);
        projectController.updateActivityList(projectActivities);
    }

    public String getActivityDetail(String findField, String findValue, String returnField) {
        String returnValue = null;
        Activity activity = mongoDb.getActivityCollection().withCodecRegistry(mongoDb.createCodecRegistry("Activities")).find(eq(findField, findValue)).first();
        switch (returnField) {
            case "id" -> returnValue = String.valueOf(activity.getId());
            case "name" -> returnValue = activity.getName();
            case "description" -> returnValue = activity.getDescription();
        }
        return returnValue;
    }

    public ObjectId getActivityId(String findField, String findValue) {
        Activity activity = mongoDb.getActivityCollection().withCodecRegistry(mongoDb.createCodecRegistry("Activities")).find(eq(findField, findValue)).first();
        ObjectId activityId = activity.getId();
        return activityId;
    }

    public void updateActivityAssignee(List<String> assignedUsers) {
        UserController userController = new UserController();
        List<ObjectId> assignedUserIds = new ArrayList<ObjectId>();
        for (String user : assignedUsers) {
            ObjectId userId = userController.getUserId("username", user);
            assignedUserIds.add(userId);
        }
        Activity activity = (Activity) Session.getOpenItem();
        ObjectId activityId = activity.getId();
        mongoDb.getActivityCollection().updateOne(eq("_id", activityId), set("assigneeList", assignedUserIds));
    }

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