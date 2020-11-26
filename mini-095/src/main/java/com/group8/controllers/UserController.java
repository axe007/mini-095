package com.group8.controllers;

import java.util.UUID;

import com.group8.helper.Helper;
import com.group8.model.UserModel;

public class UserController {

    private static String EOL = System.lineSeparator();

    public static void createUser() {
        try {
            Helper helper = new Helper();
            String username;
            String password;

            UUID uuid = UUID.randomUUID();
            System.out.println("ID: " + uuid);
            System.out.print("Enter user name: ");
            username = helper.getString();

            System.out.print("Enter user password: ");
            password = helper.getString();

            UserModel model = new UserModel();
            boolean result = model.createUser(uuid, username, password);

            if (result) {
                System.out.println("User created successfully!" + EOL);
            }
        } catch (Exception ex) {
            System.out.println("Exception in Helper class: " + ex);
        }
    }

    public void getUserInfo() {

        System.out.print("Enter username to display: ");
        Helper helper = new Helper();
        String username = helper.getString();

        UserModel model = new UserModel();
        String userInfo = model.getUserInfo(username);
        System.out.println(userInfo);
    }
}
