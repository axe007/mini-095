package com.group8;

import com.group8.controllers.MainController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class App extends Application {
    public static void main(String[] args) {
        // MainController mainController = new MainController();
        // mainController.startApplication();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO Auto-generated method stub

        FXMLLoader listLoader = new FXMLLoader(getClass().getResource("/examples/mvp/list/list.fxml"));
    }
}
