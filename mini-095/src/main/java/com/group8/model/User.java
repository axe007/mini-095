package com.group8.model;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

public class User {

    private ObjectId id;
    private String username;
    private String password;
    private String fullname;
    private String emailAddress;
    private String userRole; // "Developer", "Project Manager", "Scrum master"

    // Constructors
    public User() { }

    public User(String username, String password, String fullname, String emailAddress, String userRole) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.emailAddress = emailAddress;
        this.userRole = userRole;
    }

    // Getters
    public ObjectId getId() { return id; }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getFullname() { return fullname; }
    public String getEmailAddress() { return emailAddress; }
    public String getUserRole() { return userRole; }

    // Setters
    public void setId(ObjectId id) { this.id = id; }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setFullname(String fullname) { this.fullname = fullname; }
    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }
    public void setUserRole(String userRole) { this.userRole = userRole; }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getUsername() != null ? getUsername().hashCode() : 0);
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        result = 31 * result + (getFullname() != null ? getFullname().hashCode() : 0);
        result = 31 * result + (getEmailAddress() != null ? getEmailAddress().hashCode() : 0);
        result = 31 * result + (getUserRole() != null ? getUserRole().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{"
                + "id='" + getId() + "'"
                + ", username='" + username + "'"
                + ", fullname=" + fullname
                + ", password=" + password
                + ", emailAddress=" + emailAddress
                + ", userRole=" + userRole
                + "}";
    }
}