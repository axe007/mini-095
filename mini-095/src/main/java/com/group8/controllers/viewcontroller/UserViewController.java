package com.group8.controllers.viewcontroller;

import com.group8.App;
import com.group8.controllers.UserController;
import com.group8.helper.UIHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserViewController implements Initializable {

    private static UserController userController = new UserController();
    private static UIHelper uiHelper = new UIHelper();

    @FXML
    private Pane userView;
    @FXML
    private Button userNewButton;
    @FXML
    private Button userOpenButton;
    @FXML
    private Button userAssignButton;
    @FXML
    private Button userListButton;
    @FXML
    private Button userDeleteButton;
    @FXML
    private TextField activitySearch;
    @FXML
    private TextArea dbFeedback;

    @FXML
    private void handleUserButtons(ActionEvent event) throws IOException {
        // clear all text field
        if (event.getSource() == userNewButton) {
            uiHelper.loadWindow("UserAddView", userNewButton);


        } else if (event.getSource() == userOpenButton) {
            // Open project window

        } else if (event.getSource() == userAssignButton) {
            // List all projects window

        } else if (event.getSource() == userListButton) {
            // List all projects window

        } else if (event.getSource() == userDeleteButton) {
            // Archive project window
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            // TODO
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void userSearchOnKeyReleased(KeyEvent event) {
        /*tblActivities.getItems().clear();
        activityController.activityName = activitiesSearch.getText();*/
    }

    @FXML
    private void btnRefreshOnAction(ActionEvent event) {
        /*tblActivities.getItems().clear();
        activitiesSearch.clear();*/
    }

    @FXML
    public void actionFeedback(String result) {
        dbFeedback.setText(result);
    }
}