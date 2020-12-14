package com.group8.helper;

import com.group8.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class Helper {

    private static final Scanner input = new Scanner(System.in); // static scanner

    public String getMenuInput() {
        String menuInput = input.nextLine();
        return menuInput;
    }

    public String getString() {
        String stringInput = null;
        try {
            stringInput = input.nextLine();
        } catch (Exception ex) {
            System.out.println("Exception in Helper class: " + ex);
        }
        return stringInput;
    }

    public int getInt() {
        int userInput = input.nextInt();
        input.nextLine();

        return userInput;
    }

    public double getDouble() {
        double userInput = input.nextDouble();
        input.nextLine();

        return userInput;
    }

    public Object loadWindow(URL fileName, Pane parentPane) {
        Object controller = null;
        try {

            FXMLLoader fxmlLoader = new FXMLLoader();
            AnchorPane loadedPane = fxmlLoader.load(App.class.getResource("../fxml/content/" + fileName + ".fxml"));

            if (parentPane == null){
                parentPane = new StackPane();
            }
            parentPane.getChildren().clear();
            parentPane.getChildren().add(loadedPane);


            /*FXMLLoader loader = new FXMLLoader(loc);
            Parent parent = loader.load();
            controller = loader.getController();
            Stage stage = null;
            if (parentStage != null) {
                stage = parentStage;
            } else {
                stage = new Stage(StageStyle.DECORATED);
            }
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();*/

        } catch (IOException ex) {
            System.out.println("Exception: " + ex);
        }
        return controller;
    }





    public void closeScanner() {
        input.close();
    }
}