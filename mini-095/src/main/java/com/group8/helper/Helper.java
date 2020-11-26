package com.group8.helper;

import java.util.Scanner;

public class Helper {

    private static final Scanner input = new Scanner(System.in); // static scanner

    public String getMenuInput() {
        String menuInput = input.nextLine();
        return menuInput;
    }

    public String getString() {
        String stringInput = null;
        try {
            stringInput = input.nextLine();
        } catch (Exception ex) {
            System.out.println("Exception in Helper class: " + ex);
        }
        return stringInput;
    }

    public int getInt() {
        int userInput = input.nextInt();
        input.nextLine();

        return userInput;
    }





    public void closeScanner() {
        input.close();
    }
}