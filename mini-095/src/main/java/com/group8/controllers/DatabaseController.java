package com.group8.controllers;

import com.group8.model.Developer;
import com.group8.model.Manager;
import com.group8.model.User;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import com.mongodb.diagnostics.logging.Logger;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.ClassModel;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.Document;

import java.util.Arrays;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class DatabaseController {

    String dbUser = "MongoAdmin";
    char[] dbPassword = "Mini095GU".toCharArray();    // the password as a character array
    String dbName = "mini95";
    String authdbName = "admin";    // the name of the database in which the user is defined
    String dbServer = "mongodb.altansukh.com";
    int dbPort = 27017;

    public MongoDatabase dbConnect() {
        //MongoCredential credential = MongoCredential.createPlainCredential(dbUser, authdbName, dbPassword);

        /*Logger mongoLogger = Logger.getLogger( "com.mongodb" );
        mongoLogger.setLevel(Level.OFF);*/

        /*MongoClient mongoClient = MongoClients.create(
                MongoClientSettings.builder()
                        .applyToClusterSettings(builder ->
                                builder.hosts(Arrays.asList(new ServerAddress(dbServer, 27017))))
                        .credential(credential)
                        mongodb://MongoAdmin@mongodb.altansukh.com:27017/?authSource=admin

                        .build());*/

        MongoClient mongoClient = MongoClients.create(
                MongoClientSettings.builder()
                        .applyToClusterSettings(builder ->
                                builder.hosts(Arrays.asList(new ServerAddress("mongodb.altansukh.com", 27017))))
                        .build());

        MongoDatabase mongoDb = mongoClient.getDatabase(dbName);

        return mongoDb;
    }

    public MongoCollection getUserCollection(String collectionName) {

        ClassModel<User> userModel = ClassModel.builder(User.class).enableDiscriminator(true).build();
        ClassModel<Developer> developerUserModel = ClassModel.builder(Developer.class).enableDiscriminator(true).build();
        ClassModel<Manager> managerUserModel = ClassModel.builder(Manager.class).enableDiscriminator(true).build();

        PojoCodecProvider pojoCodecProvider = PojoCodecProvider.builder().register(userModel, developerUserModel, managerUserModel).build();

        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

        MongoCollection<User> collection = dbConnect().getCollection("users", User.class).withCodecRegistry(pojoCodecRegistry);

        return collection;
    }
}