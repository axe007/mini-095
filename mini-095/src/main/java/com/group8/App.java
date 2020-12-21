package com.group8;

import java.io.IOException;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class App extends Application {
    private static Scene scene;
    private static String appTitle = "mini-095 Project Management Software";

    public static void main(String[] args) {
        // MainController mainController = new MainController();
        // mainController.startApplication();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Parent root = FXMLLoader.load(getClass().getResource("fxml_example.fxml"));
        scene = new Scene(loadFXML("LoginView"));
        scene.getStylesheets().add(getClass().getResource("css/default.css").toExternalForm());

        primaryStage.getIcons().add(new Image(App.class.getResourceAsStream("images/app-icon.png")));
        primaryStage.setScene(scene);
        primaryStage.setTitle(appTitle);

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getBounds();

        // set Stage boundaries to visible bounds of the main screen
        primaryStage.setWidth((primaryScreenBounds.getWidth()) * 0.8);
        primaryStage.setHeight((primaryScreenBounds.getHeight()) * 0.8);
        primaryStage.setX((primaryScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((primaryScreenBounds.getHeight() - primaryStage.getHeight()) / 4);

        primaryStage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("fxml/" + fxml + ".fxml"));
        Parent root = fxmlLoader.load();
        return root;
    }
}