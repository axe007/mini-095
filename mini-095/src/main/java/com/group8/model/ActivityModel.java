package com.group8.model;

import com.group8.entity.Activity;
import com.group8.entity.User;
import com.group8.entity.UserStory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class ActivityModel {

    // 1. create task
    // 2. create user story

    // ActivityModel.createTask()
    private static final ArrayList<Activity> activities = new ArrayList<Activity>();

    public void createActivity (String activityType, String name, String content, LocalDate startDate, LocalDate endDate, 
        ArrayList<User> teamMembers, String priority, String id, double storyPoints, String acceptanceCriteria) {

        
        
        if(activityType.equals("UserStory")) {
            Activity activity = new UserStory( name, content, startDate, endDate, 
            teamMembers, priority, id, storyPoints, acceptanceCriteria);

            activities.add(activity);
            
            for (Activity a : activities) {
                System.out.println(a);
            }
            

        } else if(activityType.equals("Bug")) {
            //TODO: Under construction, Bug class not created yet


        } else if (activityType.equals("Task")){
            //TODO: Under construction, Task class not created yet

        } else {
            System.out.println("Oh no, something went wrong... :'( ");
        }

        //create the object here
        //add to arraylist      

    }

    public ArrayList<Activity> getActivities() {
        return activities; 
    }

    public String getActivityInfo(String activityId) {
        String activityInfo = null;

        for (Activity activity : activities) {
            if (activity.getId().equals(activityId)) {
                activityInfo = activity.toString();
            }
        }
        return activityInfo;
    }
    
}
// import Activity 
// public class userStory 
/// UserStory userStory = new UserStory.... 
// ActivityModel.getActivities().add(userStory); =  activities.add(activity);