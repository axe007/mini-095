package com.group8.model;

import java.util.UUID;

public class Developer extends User {

    public Developer(String username, String password, String fullname, String emailAddress, String userRole) {
        super(username, password, fullname, emailAddress, userRole);
    }

    public String toString() {
        return "Username: " + getUsername() + ", Password: " + getPassword();

    }
}