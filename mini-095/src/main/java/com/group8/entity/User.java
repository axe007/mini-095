package com.group8.entity;

import java.util.UUID;

public  abstract class User {
    private String uuid;
    private String username;
    private String password;


    public User(String uuid, String username, String password) {
        this.uuid = uuid;
        this.username = username;
        this.password = password;

    }
    public String getUuid() {
        uuid = UUID.randomUUID().toString();
        return uuid;
    }

    // public UUID getUuid() {
    //     return uuid;
    // }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //  @Override
    // public String toString() {
    //     String userInfo = "ID: " + getUuid() + ", Username: " + getUsername() + ", Password: " + getPassword();
    //     return userInfo;
    // }   
}