package com.group8.controllers;

import com.group8.helper.Helper;
import com.group8.model.UserModel;
import com.group8.controllers.UserController;
import com.group8.entity.*;

public class MainController {

    private boolean session = true;
    private final String EOL = System.lineSeparator();
    private Project project;
    Helper helper;

    public void startApplication() {

        mainMenu();

    }

    public void mainMenu() {
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
                UserModel userModel = new UserModel();

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
                        String assigneeId = helper.getMenuInput(); 
                        activityController.assignUserStory(userStoryId, assigneeId, this.project);
                        break; 
                    case "6": 
                    //assign Task
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