package com.group8.controllers;

import com.group8.helper.Helper;
import com.group8.model.UserModel;

import java.util.HashMap;
import java.util.Map;

import com.group8.controllers.UserController;
import com.group8.entity.*;

public class MainController {

    private boolean session = true;
    private final String EOL = System.lineSeparator();
    private Project project;
    Helper helper  = new Helper();

    public void startApplication() {

        mainMenu();

    }
    public void mainMenu(){
        System.out.println("Main menu: ");
        System.out.println();
        System.out.println("Please specify your role by entering one of the options given:");
        System.out.println("1. Enter 'A' for Admin");
        System.out.println("2. Enter 'M' for Manager");
        System.out.println("3. Enter 'D' for Developer");

        // String choice = InputClass.inputString("Type one of the options above");
        // if (choice.equalsIgnoreCase("M")) {
        //     if (ManagerController.login()) {
        //         rentalSystem.switchController("manager");
        //     }
        // } else if (choice.equalsIgnoreCase("E")) {
        //     Employee employee = Employee.login(); 
        //     if (employee!= null) {
        //         rentalSystem.switchController("employee", employee);
        //     }
        // } else if (choice.equalsIgnoreCase("C")) {
        //     Customer customer = Customer.login();
        //     if (customer != null) {
        //         rentalSystem.switchController("customer", customer);
        //     }
        // } else if (choice.equalsIgnoreCase("X")) {
        //     System.out.println("Exiting the system");
        //     return false;
        // } else {
        //     System.out.println("Please choose an option from the menu!");
        // }

        // return true;
    }
    public void adminMenu() {
        try {
            do {
                System.out.println(EOL + " ---------------------------------------------------");
                System.out.println("| PMS - Type one of the options below:              |");
                System.out.println("|---------------------------------------------------|");
                System.out.println("| 1. Create a manager                               |");
                System.out.println("| 2. Create a developer                             |");
                System.out.println("| 3. Print user information                         |");
                System.out.println("| 4. Show all users                                 |");
                System.out.println("|---------------------------------------------------|");
                System.out.println("| 6. Create project                                 |");
                System.out.println("| 7. Create an activity                             |");
                System.out.println("| 8. Create a sprint                                |");

                System.out.println("| 13. Return to Main Menu                           |");
                System.out.println(" ---------------------------------------------------");
                System.out.println(EOL);
                System.out.println("Enter your option: ");
                String userInput = helper.getMenuInput(); // Calling Helper method

                UserController userController = new UserController();
                // UserModel userModel = new UserModel();

                switch (userInput.toLowerCase()) {
                    case "1":
                        userController.createUser("Manager");
                        break;
                    case "2":
                        userController.createUser("Developer");
                        break;
                    case "3":
                        userController.getUserInfo();
                        break;
//                    case "4":
//                        userModel.showAllUsers();
//                        break;

                    case "7":
                        activityMenu();
                        break;

                    /*case "2" -> userController.deleteEmployee();
                    case "3" -> userController.displayEmployees(false);
                    case "4" -> userController.calculateSalary();
                    case "5" -> dartController.viewRentalTotalProfit();
                    case "6" -> dartController.viewRentalProfitable();
                    case "7" -> dartController.viewRentalFrequency();
                    case "8" -> dartController.viewRentalBestCustomer();
                    case "9" -> dartController.loadProductData();
                    case "10" -> dartController.loadRentalData();
                    case "11" -> dartController.saveProductData();
                    case "12" -> dartController.saveRentalData();*/

                    case "13":
                        mainMenu();
                        break;

                    default:
                        System.out.println("No match in mainMenu options");
                }
            } while (session);
        } catch (Exception e) {
            System.out.println("Exception in mainMenu");
        }
    }

    public void developerMenu() {
//         hi, I think our mainmenu is currently just a system administrator's menu. I think we should have a basic structure for each menu corresponding to each role: admin, manager, and developer.  
// So erm my task is to create time tracking for activity so I think we may have a logTime(Developer dev) method 
// Developer dev = new Developer(sth sth);
// // I'm assumming dev has a hashmap like Map<Project, Map<String, Task>> (a list of projects he's in, with each project, there's a list of tasks he has to do) and another Project map Map <String, Project>
        /*
            // choose one project to view/join
            viewProject(this.dev); 

            viewProject(Developer dev, String projectId) {
                if (dev.getProjectMap.containsKey(projectId)) {
                    String userInput = helper.getMenuInput(); // Calling Helper method
                    switch (userInput.toLowerCase()) {
                        case "1": 
                        // view list of tasks
                        break; 
                        case "2": 
                        // view list of tasks assigned
                        
                        break; 
                        case "3":
                        // log time for tasks assigned
                        String taskId = helper.getString();  
                        logTime(dev, taskId); 
                        break; 
                    }
                }

            } 
            logTime(Developer dev, String taskId, String projectId) {
                    Map<String, Task> newMap = new HashMap<String, Task>(); 
                    if (dev.getProjectTaskMap().containsKey(dev.getProjectMap().get(projectId))) {
                        newMap = dev.getProjectTaskMap().containsKey(dev.getProjectMap().get(projectId)); 
                    }
                    if (newMap.containsKey(taskId)) {
                        Task newTask = newMap.get(taskId); 
                        long hours = helper.getLong(); 
                        TimeTracker newTime = new TimeTracker(hours, null); 
                        newTime.getStartDateTime(hours); 
                        newTask.getTimeTrackingMap().put(dev, newTime); 
                        System.out.println("Press 1 to modify your start time"); 
                        String userInput = helper.getMenuInput(); // Calling Helper method
                        if (userInput.equals("1")) {
                            newTime.setStartDateTime();
                        }
                    }
            } */
    }

    public void activityMenu() {

        ActivityController activityController = new ActivityController();
        // ProjectController projectController = new ProjectController(this.project); 
        try {
            do {
                System.out.println(EOL + " ---------------------------------------------------");
                System.out.println("| PMS - Type one of the options below:              |");
                System.out.println("|---------------------------------------------------|");
                System.out.println("| 1. Create a user story                            |");
                System.out.println("| 2. Create a bug                                   |");
                System.out.println("| 3. Create a task                                  |");
                System.out.println("| 4. Return to Main Menu                           |");
                System.out.println(" ---------------------------------------------------");
                System.out.println(EOL);
                System.out.println("Enter your option: ");
                Helper helper = new Helper();
                String userInput = helper.getMenuInput(); // Calling Helper method

                switch (userInput.toLowerCase()) {
                    case "1":
                        activityController.createActivity("UserStory", this.project);
                        break;
                    case "2":
                        activityController.createActivity("Bug", this.project);
                        break;

                    case "3":
                        activityController.createActivity("Task", this.project);

                    case "4":
                        mainMenu();
                        break;
                    case "5":
                    // assign UserStory
                        String userStoryId = helper.getMenuInput(); 
                        String userAssigneeId = helper.getMenuInput(); 
                        activityController.assignActivity("UserStory", userStoryId, userAssigneeId, this.project);
                        break; 
                    case "6": 
                    //assign Task
                        String taskId = helper.getMenuInput(); 
                        String taskAssigneeId = helper.getMenuInput(); 
                        activityController.assignActivity("Task",  taskId, taskAssigneeId, this.project);
                        break;
                    case "7":
                        break; 
                    default:
                        System.out.println("No match in mainMenu options");
                }
            } while (session);
        } catch (Exception e) {
            System.out.println("Exception in mainMenu");
        }
    }
}