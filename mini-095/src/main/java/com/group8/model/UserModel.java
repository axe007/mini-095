package com.group8.model;

import com.group8.entity.Administrator;
import com.group8.entity.Developer;
import com.group8.entity.Manager;
import com.group8.entity.User;

import java.util.ArrayList;
import java.util.UUID;

public class UserModel {

    public static ArrayList<User> users = new ArrayList<>();
    public static final String adminName = "admin";
    // public static final String adminPass = "admin1234";


    public boolean createUser(String uuid, String username, String password, String userType) {
        boolean result = false;

        if (userType.equals("Developer")) {
            User developer = new Developer(uuid, username, password, userType);
            users.add(developer);
            result = true;
            System.out.println(developer);
        } else if (userType.equals("Manager")) {
            User manager = new Manager(uuid, username, password, userType);
            users.add(manager);
            result=true;
            System.out.println(manager);
        }
//        else{
//            System.out.println("Error");
//        }
        return result;
    }



        // User user = new User(uuid, username, password);
//       Developer developer = new Developer(uuid, username, password);
//        Manager manager = new Manager(uuid, username,password);

//
//        if (user instanceof User) {
//
//            users.add(user);
//
//            result = true;
//        }
//        return result;
//    }




    public String getUserInfo(String username) {
        String userInfo = null;

        for (User user : users) {
            if (user.getUsername().equals(username)) {
                userInfo = user.toString();
            }
        }
        return userInfo;
    }

//
//    public void showAllUsers(){
//        for (User user : users) {
//            System.out.println(users);
//        }
//    }


}