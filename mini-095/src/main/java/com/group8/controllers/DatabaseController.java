package com.group8.controllers;

import com.group8.model.Developer;
import com.group8.model.Manager;
import com.group8.model.*;
import com.group8.model.User;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.*;
import com.mongodb.client.MongoCollection;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static com.mongodb.client.model.Filters.*;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import static org.bson.codecs.pojo.Conventions.ANNOTATION_CONVENTION;
import static org.bson.codecs.pojo.Conventions.CLASS_AND_PROPERTY_CONVENTION;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.ClassModel;
import org.bson.codecs.pojo.Convention;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.Arrays;


import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/*import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;
import static java.util.Arrays.asList;*/

public class DatabaseController {

    String dbUser = "MongoAdmin";
    char[] dbPassword = "Mini095GU".toCharArray();    // the password as a character array
    String dbName = "mini95";
    String authdbName = "admin";    // the name of the database in which the user is defined
    String dbServer = "mongodb.altansukh.com";
    int dbPort = 27017;

    public MongoClient dbConnect() {

        MongoCredential credential = MongoCredential.createCredential(dbUser, authdbName, dbPassword);

        MongoClient mongoClient = MongoClients.create(
                MongoClientSettings.builder()
                        .applyToClusterSettings(builder ->
                                builder.hosts(Arrays.asList(new ServerAddress(dbServer, 27017))))
                        .credential(credential)
                        .build());

        return mongoClient;
    }

    public MongoCollection<User> getUserCollection() {

        MongoDatabase database = dbConnect().getDatabase(dbName);
        MongoCollection<User> collection = database.getCollection("users", User.class).withCodecRegistry(createCodecRegistry("Users"));
        return collection;

    }

    public MongoCollection<Project> getProjectCollection() {

        MongoDatabase database = dbConnect().getDatabase(dbName);
        MongoCollection<Project> collection = database.getCollection("projects",Project.class).withCodecRegistry(createCodecRegistry("Projects"));
        return collection;

    }

    public CodecRegistry createCodecRegistry(String classType) {

        PojoCodecProvider pojoCodecProvider = null;
        CodecRegistry pojoCodecRegistry;

        if (classType.equals("Users")) {
            ClassModel<User> userModel = ClassModel.builder(User.class).enableDiscriminator(true).build();
            ClassModel<Developer> developerUserModel = ClassModel.builder(Developer.class).enableDiscriminator(true).build();
            ClassModel<Manager> managerUserModel = ClassModel.builder(Manager.class).enableDiscriminator(true).build();
            pojoCodecProvider = PojoCodecProvider.builder().conventions(List.of(ANNOTATION_CONVENTION)).register(userModel, developerUserModel, managerUserModel).build();

        } else if (classType.equals("Projects")){
            ClassModel<Project> projectModel = ClassModel.builder(Project.class).enableDiscriminator(true).build();
            pojoCodecProvider = PojoCodecProvider.builder().conventions(List.of(ANNOTATION_CONVENTION)).register(projectModel).build();
        }
        pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

        return pojoCodecRegistry;
    }

}