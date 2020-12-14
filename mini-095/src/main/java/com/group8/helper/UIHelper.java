package com.group8.helper;

import com.group8.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class UIHelper {
    public void loadWindow(String viewName, Button sourceButton) throws IOException {
        Stage stage;
        Parent root;
        root = FXMLLoader.load(App.class.getResource("fxml/content/" + viewName + ".fxml"));
        stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Add new user");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        stage.initOwner(sourceButton.getScene().getWindow());
        stage.show();
    }
}