package com.group8.model;

import java.util.UUID;

public class Manager extends User {

    public Manager(String username, String password, String fullname, String emailAddress, String userRole) {
        super(username, password, fullname, emailAddress, userRole);
    }

    @Override
    public String toString() {
       return "Username: " + getUsername() + ", Password: " + getPassword();
    }
}