package com.group8.model;

public class Manager extends User {

    public Manager() { }

    public Manager(String username, String password, String fullname, String emailAddress, String userRole) {
        super(username, password, fullname, emailAddress, userRole);
    }

    @Override
    public String toString() {
        return "Manager{"
                + "id='" + super.getId() + "'"
                + ", username='" + super.getUsername() + "'"
                + ", fullname=" + super.getFullname()
                + ", password=" + super.getPassword()
                + ", emailAddress=" + super.getEmailAddress()
                + ", userRole=" + super.getUserRole()
                + "}";
    }
}