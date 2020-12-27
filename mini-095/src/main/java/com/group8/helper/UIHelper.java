package com.group8.helper;

import com.group8.App;
import com.group8.model.Project;
import com.group8.model.Activity;

import com.group8.model.Session;
import com.group8.model.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.Scanner;

public class UIHelper {
    public void loadWindow(String viewName, Button sourceButton, String stageTitle) throws IOException {
        Stage stage;
        Parent root;
        root = FXMLLoader.load(App.class.getResource("fxml/content/" + viewName + ".fxml"));
        stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle(stageTitle);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        stage.initOwner(sourceButton.getScene().getWindow());
        stage.show();
    }

    public void loadWindow(String viewName, Button sourceButton, Project project) throws IOException {
        Stage stage;
        Parent root;
        root = FXMLLoader.load(App.class.getResource("fxml/content/" + viewName + ".fxml"));
        stage = new Stage();
        stage.setScene(new Scene(root));

        if (Session.getWindowMode().equals("new")) {
            stage.setTitle("Add new project");
        } else if (Session.getWindowMode().equals("edit")) {
            stage.setTitle("Edit project details");
        }

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        stage.initOwner(sourceButton.getScene().getWindow());
        stage.show();
    }
    // public void loadWindow(String viewName, Button sourceButton, Activity activity) throws IOException {
    //     Stage stage;
    //     Parent root;
    //     root = FXMLLoader.load(App.class.getResource("fxml/content/" + viewName + ".fxml"));
    //     stage = new Stage();
    //     stage.setScene(new Scene(root));

    //     if (Session.getWindowMode().equals("new")) {
    //         stage.setTitle("Add new project");
    //     } else if (Session.getWindowMode().equals("edit")) {
    //         stage.setTitle("Edit project details");
    //     }

    //     stage.initModality(Modality.APPLICATION_MODAL);
    //     stage.initStyle(StageStyle.UTILITY);
    //     stage.initOwner(sourceButton.getScene().getWindow());
    //     stage.show();
    // }
    public void loadWindow(String viewName, Button sourceButton, Activity activity) throws IOException {
        Stage stage;
        Parent root;
        root = FXMLLoader.load(App.class.getResource("fxml/content/" + viewName + ".fxml"));
        stage = new Stage();
        stage.setScene(new Scene(root));

        if (Session.getWindowMode().equals("new")) {
            stage.setTitle("Add new activity");
        } else if (Session.getWindowMode().equals("edit")) {
            stage.setTitle("Edit activity details");
        }

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        stage.initOwner(sourceButton.getScene().getWindow());
        stage.show();
    }
    public Optional<ButtonType> alertDialogGenerator(StackPane root, String alertType, String title, String content) {

        Alert alert = null;
        DialogPane dialogPane = null;
        
        Optional<ButtonType> result = null;

        if (alertType.equals("error")) {
            alert = new Alert(Alert.AlertType.ERROR);
            dialogPane = alert.getDialogPane();
            dialogPane.getStyleClass().add("dialog-pane-error");
        } else if (alertType.equals("success")) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            dialogPane = alert.getDialogPane();
            dialogPane.getStyleClass().add("dialog-pane-success");
        } else if (alertType.equals("confirm")) {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            dialogPane = alert.getDialogPane();
            ((Button) dialogPane.lookupButton(ButtonType.OK)).setText("Yes");
            ((Button) dialogPane.lookupButton(ButtonType.CANCEL)).setText("No");
            dialogPane.getStyleClass().add("dialog-pane-success");

        }

        centerButtons(dialogPane);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.initOwner(root.getScene().getWindow());
        styleAlert(alert);
        result = alert.showAndWait();

        return result;
    }

    private void styleAlert(Alert alert) {
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        //LibraryAssistantUtil.setStageIcon(stage);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(App.class.getResource("css/default.css").toExternalForm());
        dialogPane.getStyleClass().add("custom-alert");
    }

    public void centerButtons(DialogPane dialogPane) {
        Region spacer = new Region();
        ButtonBar.setButtonData(spacer, ButtonBar.ButtonData.BIG_GAP);
        HBox.setHgrow(spacer, Priority.ALWAYS);
        dialogPane.applyCss();
        HBox hboxDialogPane = (HBox) dialogPane.lookup(".container");
        hboxDialogPane.getChildren().add(spacer);
    }
}