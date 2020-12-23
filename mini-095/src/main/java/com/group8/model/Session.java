package com.group8.model;

import org.bson.types.ObjectId;

public class Session {

    private static Session instance;
    private static ObjectId sessionUserId;
    private static ObjectId openProjectId;
    private static String windowMode;
    private static Object openItem;

    private Session(ObjectId sessionUserId) {
        this.sessionUserId = sessionUserId;
        this.openProjectId = null;
        this.windowMode = null;
    }

    synchronized public static Session getInstance(ObjectId userId) {
        if(instance == null) {
            instance = new Session(userId);
        }
        return instance;
    }

    public static ObjectId getSessionUserId() {
        return sessionUserId;
    }

    public static ObjectId getOpenProjectId() {
        return openProjectId;
    }
    public static String getWindowMode() {
        return windowMode;
    }
    public static Object getOpenItem() {
        return openItem;
    }

    public static void setSessionUserId(ObjectId loggedUsername) {
        sessionUserId = loggedUsername;
    }
    public static void setOpenProjectId(ObjectId projectName) {
        openProjectId = projectName;
    }
    public static void setWindowMode(String windowModeSet) {
        windowMode = windowModeSet;
    }
    public static void setSetOpenItem(Object item) {
        openItem = item;
    }

    public static void logoutSession() {
        instance = null;
    }

    public static void getSomething() {
        // will delete later - altan
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "Logged Username='" + sessionUserId + '\'' +
                ", Open Project=" + openProjectId +
                '}';
    }
}