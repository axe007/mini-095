package com.group8.controllers;

import java.util.UUID;

import com.group8.model.Developer;
import com.group8.model.Manager;
import com.group8.model.User;
import com.group8.helper.Helper;
import com.group8.controllers.DatabaseController;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.ClassModel;
import org.bson.codecs.pojo.PojoCodecProvider;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class UserController {

    private static String EOL = System.lineSeparator();
    private static DatabaseController mongoDb = new DatabaseController();

    public boolean createUser(String username, String password, String fullname, String emailAddress, String userRole) {
        boolean result = false;
        User newUser;

        if (userRole.equals("Developer")) {
            newUser = new Developer(username, password, fullname, emailAddress, userRole);
            // users.add(developer);
        } else {
            newUser = new Manager(username, password, fullname, emailAddress, userRole);
            // users.add(manager);
        }

        mongoDb.getUserCollection("users").insertOne(newUser);
        result = true;

        return result;
    }

    public void getUserInfo() {

        System.out.print("Enter username to display: ");
        Helper helper = new Helper();
        String username = helper.getString();
    }
}
