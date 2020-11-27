package com.group8.model;

import com.group8.entity.User;

import java.util.ArrayList;
import java.util.UUID;

public class UserModel {

    private static final ArrayList<User> users = new ArrayList<>();

    public boolean createUser(UUID uuid, String username, String password) {
        boolean result = false;

        User user = new User(uuid, username, password);
        if (user instanceof User) {

            users.add(user);

            result = true;
        }
        return result;
    }

    public String getUserInfo(String username) {
        String userInfo = null;

        for (User user : users) {
            if (user.getUsername().equals(username)) {
                userInfo = user.toString();
            }
        }
        return userInfo;
    }
}