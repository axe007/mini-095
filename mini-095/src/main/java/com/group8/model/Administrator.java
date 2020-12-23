package com.group8.model;

import com.group8.model.User;

public class Administrator extends User {

    public Administrator(String username, String password, String fullname, String emailAddress, String userRole) {
        super(username, password, fullname, emailAddress, userRole);
    }

}