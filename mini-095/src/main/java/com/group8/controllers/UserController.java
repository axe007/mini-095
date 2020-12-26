package com.group8.controllers;

import com.group8.model.Developer;
import com.group8.model.Manager;
import com.group8.model.Session;
import com.group8.model.User;
import com.group8.helper.Helper;
import com.mongodb.BasicDBObject;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

public class UserController {

    private static String EOL = System.lineSeparator();
    private static DatabaseController mongoDb = new DatabaseController();

    public void createUser(String username, String password, String fullname, String emailAddress, String userRole) {
        User newUser;

        if (userRole.equals("Developer")) {
            newUser = new Developer(username, password, fullname, emailAddress, userRole);
            // users.add(developer);
        } else {
            newUser = new Manager(username, password, fullname, emailAddress, userRole);
            // users.add(manager);
        }

        mongoDb.getUserCollection().insertOne(newUser);
        System.out.println("New user created successfully!");
    }

    public void modifyUser(String username, String password, String fullname, String emailAddress, String userRole) {
        User user = (User) Session.getOpenItem();
        ObjectId id = user.getId();

        mongoDb.getUserCollection().updateOne(eq("_id", id),
                combine(set("username", username), set("password", password), set("fullname", fullname),
                        set("userRole", userRole), set("emailAddress", emailAddress)));
        System.out.println("User details updated!");
    }

    public boolean authenticateUser(String username, String password) {
        boolean result = false;

        BasicDBObject query = new BasicDBObject();
        query.put("username", username);
        query.put("password", password);
        User user = mongoDb.getUserCollection().find(query).first();

        if (user != null && user instanceof User) {
            result = true;
        }
        return result;
    }

    public List<User> getUserList() {
        List<User> users = mongoDb.getUserCollection().find().into(new ArrayList<User>());
        return users;
    }

    // public String getUserDetail(String findField, String findValue, String
    // returnField) {
    // String returnValue = null;
    // User user =
    // mongoDb.getUserCollection().withCodecRegistry(mongoDb.createCodecRegistry("Users")).find(eq(findField,
    // findValue)).first();
    // switch (returnField) {
    // case "id" -> returnValue = String.valueOf(user.getId());
    // case "username" -> returnValue = user.getUsername();
    // case "password" -> returnValue = user.getPassword();
    // case "fullname" -> returnValue = user.getFullname();
    // case "emailAddress" -> returnValue = user.getEmailAddress();
    // case "userRole" -> returnValue = user.getUserRole();
    // }
    // return returnValue;
    // }

    // public String getUserDetail(ObjectId findValue, String returnField) {
    // String returnValue = null;
    // User user =
    // mongoDb.getUserCollection().withCodecRegistry(mongoDb.createCodecRegistry("Users")).find(eq("_id",
    // findValue)).first();
    // switch (returnField) {
    // case "username" -> returnValue = user.getUsername();
    // case "password" -> returnValue = user.getPassword();
    // case "fullname" -> returnValue = user.getFullname();
    // case "emailAddress" -> returnValue = user.getEmailAddress();
    // case "userRole" -> returnValue = user.getUserRole();
    // }
    // return returnValue;
    // }

    public ArrayList<String> getUserDetailList(String userAttribute) {
        ArrayList<String> userDetailList = new ArrayList<>();
        List<User> users = getUserList();
        String returnValue;
        for (User user : users) {
            switch (userAttribute) {
                case "username" -> returnValue = user.getUsername();
                case "password" -> returnValue = user.getPassword();
                case "fullname" -> returnValue = user.getFullname();
                case "emailAddress" -> returnValue = user.getEmailAddress();
                case "userRole" -> returnValue = user.getUserRole();
                default -> throw new IllegalStateException("Unexpected value: " + userAttribute);
            }
            userDetailList.add(returnValue);
        }
        return userDetailList;
    }

    public String getUserDetail(String findField, String findValue, String returnField) {
        String returnValue = null;
        User user = mongoDb.getUserCollection().withCodecRegistry(mongoDb.createCodecRegistry("Users")).find(eq(findField, findValue)).first();
        switch (returnField) {
            case "id" -> returnValue = String.valueOf(user.getId());
            case "username" -> returnValue = user.getUsername();
            case "password" -> returnValue = user.getPassword();
            case "fullname" -> returnValue = user.getFullname();
            case "emailAddress" -> returnValue = user.getEmailAddress();
            case "userRole" -> returnValue = user.getUserRole();
        }
        return returnValue;
    }

    public String getUserDetail(ObjectId findValue, String returnField) {
        String returnValue = null;
        User user = mongoDb.getUserCollection().withCodecRegistry(mongoDb.createCodecRegistry("Users")).find(eq("_id", findValue)).first();
        switch (returnField) {
            case "username" -> returnValue = user.getUsername();
            case "password" -> returnValue = user.getPassword();
            case "fullname" -> returnValue = user.getFullname();
            case "emailAddress" -> returnValue = user.getEmailAddress();
            case "userRole" -> returnValue = user.getUserRole();
        }
        return returnValue;
    }

    public ObjectId getUserId(String findField, String findValue) {
        User user = mongoDb.getUserCollection().withCodecRegistry(mongoDb.createCodecRegistry("Users"))
                .find(eq(findField, findValue)).first();
        ObjectId userId = user.getId();
        return userId;
    }

    public void getUserInfo() {
        System.out.print("Enter username to display: ");
        Helper helper = new Helper();
        String username = helper.getString();
    }
}