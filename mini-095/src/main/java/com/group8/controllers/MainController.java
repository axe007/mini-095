package com.group8.controllers;


import com.group8.helper.Helper;
import com.group8.controllers.UserController;

public class MainController {

    private boolean session = true;
    private final String EOL = System.lineSeparator();

    public void startApplication() {

        mainMenu();

    }

    public void mainMenu() {
        try {
            do {
                System.out.println(EOL + " ---------------------------------------------------");
                System.out.println("| PMS - Type one of the options below:              |");
                System.out.println("|---------------------------------------------------|");
                System.out.println("| 1. Create an user                                 |");
                System.out.println("| 2. Print user information                         |");
                System.out.println("|---------------------------------------------------|");
                System.out.println("| 6. Create project                                 |");
                System.out.println("| 7. Create an activity                             |");
                System.out.println("| 8. Create a sprint                                |");

                System.out.println("| 13. Return to Main Menu                           |");
                System.out.println(" ---------------------------------------------------");
                System.out.println(EOL);
                System.out.println("Enter your option: ");
                Helper helper = new Helper();
                String userInput = helper.getMenuInput(); // Calling Helper method

                UserController userController = new UserController();

                switch (userInput.toLowerCase()) {
                    case "1":
                        userController.createUser();
                        break;
                    case "2":
                        userController.getUserInfo();
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
}