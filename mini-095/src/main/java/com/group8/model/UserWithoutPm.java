package com.group8.model;

import java.util.UUID;

public class UserWithoutPm {


    private UUID id;
    private UserType userType;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;

    public UserWithoutPm(UUID id, UserType userType, String password, String firstName,
            String lastName, String phoneNumber, String email) {

        this.id = id;
        this.userType = userType;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        //TODO: add type check and throw exceptions here later.

    }
    public UserWithoutPm(UserType userType, String password, String firstName, String lastName,
            String phoneNumber, String email) {
        this.userType = userType;
        this.id = UUID.randomUUID();
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;

        //TODO: add type check and throw exceptions here later.

    }
    



    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }





    



}
