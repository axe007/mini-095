package com.group8.entity;

import java.util.UUID;

public class Administrator extends User {
    private String userType = "Administrator";

    public Administrator(UUID uuid, String username, String password, String userType) {
        super(uuid, username, password);
        this.userType = userType;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
