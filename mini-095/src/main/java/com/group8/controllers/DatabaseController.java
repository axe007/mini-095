package com.group8.controllers;

import com.group8.model.Developer;
import com.group8.model.Manager;
import com.group8.model.*;
import com.group8.model.User;
import com.group8.model.Activity;
import com.group8.model.Bug;
import com.group8.model.Task;
import com.group8.model.UserStory;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.*;
import com.mongodb.client.MongoCollection;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import static org.bson.codecs.pojo.Conventions.ANNOTATION_CONVENTION;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.ClassModel;
import org.bson.codecs.pojo.PojoCodecProvider;
import java.util.Arrays;
import java.util.List;

public class DatabaseController {

        public static String dbUser = "MongoAdmin";
        public static char[] dbPassword = "Mini095GU".toCharArray(); // the password as a character array
        public static String dbName = "mini95";
        public static String authdbName = "admin"; // the name of the database in which the user is defined
        public static String dbServer = "mongodb.altansukh.com";
        public static String dbLocalServer = "localhost";
        public static int dbPort = 27017;

        public MongoClient dbConnect() {
                boolean localDb = Session.isLocalDb();
                MongoClient mongoClient;
/**/
                if (localDb) {
                        mongoClient = MongoClients.create(MongoClientSettings.builder()
                                        .applyToClusterSettings(builder -> builder
                                                        .hosts(Arrays.asList(new ServerAddress(dbLocalServer, dbPort))))
                                        .build());
                } else {
                        MongoCredential credential = MongoCredential.createCredential(dbUser, authdbName, dbPassword);
                        mongoClient = MongoClients.create(MongoClientSettings.builder().applyToClusterSettings(
                                        builder -> builder.hosts(Arrays.asList(new ServerAddress(dbServer, dbPort))))
                                        .credential(credential).build());
                }
                return mongoClient;
        }

        public MongoCollection<User> getUserCollection() {
                MongoDatabase database = dbConnect().getDatabase(dbName);
                MongoCollection<User> collection = database.getCollection("users", User.class)
                                .withCodecRegistry(createCodecRegistry("Users"));
                return collection;
        }

        public MongoCollection<Project> getProjectCollection() {
                MongoDatabase database = dbConnect().getDatabase(dbName);
                MongoCollection<Project> collection = database.getCollection("projects", Project.class)
                                .withCodecRegistry(createCodecRegistry("Projects"));
                return collection;
        }

        public MongoCollection<Sprint> getSprintCollection() {
                MongoDatabase database = dbConnect().getDatabase(dbName);
                MongoCollection<Sprint> collection = database.getCollection("sprints", Sprint.class)
                                .withCodecRegistry(createCodecRegistry("Sprints"));
                return collection;
        }

        public MongoCollection<Activity> getActivityCollection() {
                MongoDatabase database = dbConnect().getDatabase(dbName);
                MongoCollection<Activity> collection = database.getCollection("activities", Activity.class)
                                .withCodecRegistry(createCodecRegistry("Activities"));
                return collection;
        }

        public MongoCollection<Note> getNoteCollection() {
                MongoDatabase database = dbConnect().getDatabase(dbName);
                MongoCollection<Note> collection = database.getCollection("notes", Note.class)
                                .withCodecRegistry(createCodecRegistry("Notes"));
                return collection;
        }

        public MongoCollection<TimeLog> getTimeLogCollection() {
                MongoDatabase database = dbConnect().getDatabase(dbName);
                MongoCollection<TimeLog> collection = database.getCollection("timelogs", TimeLog.class)
                                .withCodecRegistry(createCodecRegistry("TimeLogs"));
                return collection;
        }

        public CodecRegistry createCodecRegistry(String classType) {
                PojoCodecProvider pojoCodecProvider = null;
                CodecRegistry pojoCodecRegistry;

                if (classType.equals("Users")) {
                        ClassModel<User> userModel = ClassModel.builder(User.class).enableDiscriminator(true).build();
                        ClassModel<Developer> developerUserModel = ClassModel.builder(Developer.class)
                                        .enableDiscriminator(true).build();
                        ClassModel<Manager> managerUserModel = ClassModel.builder(Manager.class)
                                        .enableDiscriminator(true).build();
                        pojoCodecProvider = PojoCodecProvider.builder().conventions(List.of(ANNOTATION_CONVENTION))
                                        .register(userModel, developerUserModel, managerUserModel).build();

                } else if (classType.equals("Projects")) {
                        ClassModel<Project> projectModel = ClassModel.builder(Project.class).enableDiscriminator(true)
                                        .build();
                        pojoCodecProvider = PojoCodecProvider.builder().conventions(List.of(ANNOTATION_CONVENTION))
                                        .register(projectModel).build();

                } else if (classType.equals("Sprints")) {
                        ClassModel<Sprint> sprintModel = ClassModel.builder(Sprint.class).enableDiscriminator(true)
                                        .build();
                        pojoCodecProvider = PojoCodecProvider.builder().conventions(List.of(ANNOTATION_CONVENTION))
                                        .register(sprintModel).build();

                } else if (classType.equals("Activities")) {
                        ClassModel<Activity> activityModel = ClassModel.builder(Activity.class)
                                        .enableDiscriminator(true).build();
                        ClassModel<Task> taskModel = ClassModel.builder(Task.class).enableDiscriminator(true).build();
                        ClassModel<Bug> bugModel = ClassModel.builder(Bug.class).enableDiscriminator(true).build();
                        ClassModel<UserStory> userStoryModel = ClassModel.builder(UserStory.class)
                                        .enableDiscriminator(true).build();

                        pojoCodecProvider = PojoCodecProvider.builder().conventions(List.of(ANNOTATION_CONVENTION))
                                        .register(activityModel, taskModel, bugModel, userStoryModel).build();

                } else if (classType.equals("TimeLogs")) {
                        ClassModel<TimeLog> timeLogModel = ClassModel.builder(TimeLog.class).enableDiscriminator(true)
                                        .build();
                        pojoCodecProvider = PojoCodecProvider.builder().conventions(List.of(ANNOTATION_CONVENTION))
                                        .register(timeLogModel).build();
                } else if (classType.equals("Notes")) {
                        ClassModel<Note> noteModel = ClassModel.builder(Note.class).enableDiscriminator(true).build();
                        pojoCodecProvider = PojoCodecProvider.builder().conventions(List.of(ANNOTATION_CONVENTION))
                                        .register(noteModel).build();

                }

                pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

                return pojoCodecRegistry;
        }
}