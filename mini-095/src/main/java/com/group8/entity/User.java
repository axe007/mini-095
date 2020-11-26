package com.group8.entity;

import java.util.UUID;

public class User {
    private UUID uuid;
    private String username;
    private String password;


    public User(UUID uuid, String username, String password) {
        this.uuid = uuid;
        this.username = username;
        this.password = password;

    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
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

    @Override
    public String toString() {
        String userInfo = "ID: " + getUuid() + ", Username: " + getUsername() + ", Password: " + getPassword();
        return userInfo;
    }
}