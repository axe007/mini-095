package com.group8.controllers;

import com.group8.entity.User;

public abstract class ViewController {
    public static User currentUser;
    
    public abstract void initialize();

    public abstract void onClose();





}