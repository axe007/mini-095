package com.group8.controllers;

import com.group8.model.*;
import com.group8.helper.Helper;
import com.mongodb.BasicDBObject;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

public class UserController {

    private static DatabaseController mongoDb = new DatabaseController();
    private ActivityController activityController = new ActivityController();

    public void createUser(String username, String password, String fullname, String emailAddress, String userRole) {
        User newUser;

        if (userRole.equals("Developer")) {
            newUser = new Developer(username, password, fullname, emailAddress, userRole);
        } else {
            newUser = new Manager(username, password, fullname, emailAddress, userRole);
        }

        mongoDb.getUserCollection().insertOne(newUser);
    }

    public void modifyUser(String username, String password, String fullname, String emailAddress, String userRole) {
        User user = (User) Session.getOpenItem();
        ObjectId id = user.getId();

        mongoDb.getUserCollection().updateOne(eq("_id", id),
                combine(set("username", username), set("password", password), set("fullname", fullname),
                        set("userRole", userRole), set("emailAddress", emailAddress)));
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

    public ArrayList<User> getUserList() {
        ArrayList<User> users = mongoDb.getUserCollection().find().into(new ArrayList<User>());
        return users;
    }

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
        User user = mongoDb.getUserCollection().withCodecRegistry(mongoDb.createCodecRegistry("Users"))
                .find(eq(findField, findValue)).first();
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
        User user = mongoDb.getUserCollection().withCodecRegistry(mongoDb.createCodecRegistry("Users"))
                .find(eq("_id", findValue)).first();
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

    public void recordTimeLog(ObjectId projectId, ObjectId sprintId, ObjectId activityId, ObjectId userId, double hours, LocalDate createdDate) {
        TimeLog timeLog = new TimeLog(projectId, sprintId, activityId, userId, hours, createdDate);
        mongoDb.getTimeLogCollection().insertOne(timeLog);
    }

    public ArrayList<TimeLog> retrieveTimeLogList(ObjectId objectId, String listType) {
        ArrayList<TimeLog> timeLogs = new ArrayList<>();

        if (listType.equals("project")) {
            timeLogs = mongoDb.getTimeLogCollection().find(eq("projectId", objectId)).into(new ArrayList<TimeLog>());
        } else if (listType.equals("sprint")) {
            timeLogs = mongoDb.getTimeLogCollection().find(eq("sprintId", objectId)).into(new ArrayList<TimeLog>());
        } else if (listType.equals("project")) {
            timeLogs = mongoDb.getTimeLogCollection().find(eq("projectId", objectId)).into(new ArrayList<TimeLog>());
        } else if (listType.equals("activity")) {
            timeLogs = mongoDb.getTimeLogCollection().find(eq("activityId", objectId)).into(new ArrayList<TimeLog>());
        } else if (listType.equals("user")) {
            timeLogs = mongoDb.getTimeLogCollection().find(eq("userId", objectId)).into(new ArrayList<TimeLog>());
        }

        return timeLogs;
    }

    public ArrayList<TimeLog> retrieveTimeLogList() {
        ArrayList<TimeLog> timeLogs = mongoDb.getTimeLogCollection().find().into(new ArrayList<TimeLog>());
        return timeLogs;
    }
}