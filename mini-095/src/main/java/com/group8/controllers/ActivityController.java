package com.group8.controllers;

import com.group8.helper.Helper;

public class ActivityController {
    
    public void createActivity (){
        //1. ask what type of activity 
        int choice;
        Helper helper = new Helper();
        System.out.println("Would you like to create a Task (1), Bug(2) or User Story(3)? ");
        choice = helper.getInt();
        /*
        switch(choice) {
            case 1: 
                createTask();
                break;
            case 2:
                createBug()
                break;
            case 3:
                createUserStory();
                break;
            default:
                break;
        }
        
        // add to activities (list)
        //2. create that
    }
    
    public void createTask(){

    }

    public void createBug(){
        */
    }
}
