package com.group8.helper;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public double getDouble() {
        double userInput = input.nextDouble();
        input.nextLine();

        return userInput;
    }
    public long getLong() {
        long userInput = input.nextLong();
        input.nextLine();

        return userInput;
    }
    public LocalDateTime localDateExceptionHandler() {
        System.out.print("Please input in the following format: yyyy-MM-dd hh:mm ");
        String s = "";

        LocalDateTime dateTime = null;
        try {
            s = getString();
            DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            dateTime = LocalDateTime.from(f.parse(s));
        } catch (DateTimeException e) {
            System.out.println(e.getMessage());
        }

        return dateTime;
    }


    public void closeScanner() {
        input.close();
    }
}