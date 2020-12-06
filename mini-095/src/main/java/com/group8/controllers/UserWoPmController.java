package com.group8.controllers;

import java.util.ArrayList;

import java.util.List;

import com.group8.model.UserWithoutPm;

public class UserWoPmController {
    public static List<UserWithoutPm> userList = new ArrayList<>();

    public static void addUserToList(UserWithoutPm user) {
        userList.add(user);
    }

    public static void deletUserFromList(UserWithoutPm user) {
        userList.remove(user);
    }

    public static void updateUserToList(UserWithoutPm oldUser, UserWithoutPm newUser) {
        if (userList.indexOf(oldUser) >= 0) {
            userList.set(userList.indexOf(oldUser), newUser);

        }
    }

}
