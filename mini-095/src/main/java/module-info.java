module com.group8  {
    requires jdk.net;
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires de.jensd.fx.glyphs.iconsfivetwofive;
    requires java.logging;
    requires org.mongodb.driver.core;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;

    opens com.group8 to javafx.fxml, org.mongodb.driver.sync.client, org.mongodb.driver.core;
    opens com.group8.controllers to javafx.fxml, org.mongodb.driver.sync.client, org.mongodb.driver.core, org.mongodb.bson;
    opens com.group8.controllers.viewcontroller to javafx.fxml;
    opens com.group8.model to javafx.fxml, javafx.base, org.mongodb.bson;

    exports com.group8;
}