package com.group8.entity;

import java.util.UUID;

public class Manager extends User {
    private String userType = "Manager";

    public Manager(String uuid, String username, String password, String userType) {
        super(uuid, username, password);
        this.userType = userType;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }


    public String toString() {
       return "ID: " + getUuid() +".Type : "+userType+ ", Username: " + getUsername() + ", Password: " + getPassword();

    }
}

