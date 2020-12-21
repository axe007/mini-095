package com.group8.controllers;

import com.group8.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ApplicationController implements Initializable {

    @FXML
    private SplitPane mainSplitPane;
    @FXML
    private Label mainTitleLabel = new Label("Main Title");
    @FXML
    private StackPane appContent;
    @FXML
    private Button dashboardButton;
    @FXML
    private Button projectButton;
    @FXML
    private Button sprintboardButton;
    @FXML
    private Button activitiesButton;
    @FXML
    private Button usersButton;
    @FXML
    private Button logoutButton;

    // sessionID1, userId, projectId, loginTime-24hours
    // sessionID2, userId, projectId, loginTime-24hours
    // updateSession();


    @FXML
    private void handleSidebarBtn(ActionEvent menuEvent) throws IOException {
        String viewName = "HomeView";
        String viewTitle = "Title";

        // clear all text field
        if (menuEvent.getSource() == dashboardButton) {
            viewName = "HomeView";
            viewTitle = "Welcome";
        } else if (menuEvent.getSource() == projectButton) {
            viewName = "ProjectView";
            viewTitle = "Projects";
        } else if (menuEvent.getSource() == sprintboardButton) {
            viewName = "HomeView";
            viewTitle = "Sprint Board";
        } else if (menuEvent.getSource() == activitiesButton) {
            viewName = "ActivitiesView";
            viewTitle = "Activities";
        } else if (menuEvent.getSource() == usersButton) {
            viewName = "UserView";
            viewTitle = "Users";
        } else if (menuEvent.getSource() == logoutButton) {
            App.setRoot("LoginView");
        }

        FXMLLoader fxmlLoader = new FXMLLoader();
        AnchorPane loadedPane = fxmlLoader.load(App.class.getResource("fxml/content/" + viewName + ".fxml"));
        mainTitleLabel.setText(viewTitle);
        setActiveButton(menuEvent.getSource());

        if (appContent == null){
            appContent = new StackPane();
        }
        appContent.getChildren().clear();
        appContent.getChildren().add(loadedPane);
    }

    @FXML
    public void setActiveButton(Object activeButton) {
        Button[] sidebarButtons = new Button[] {dashboardButton, projectButton, sprintboardButton, activitiesButton, usersButton};

        for (Button button : sidebarButtons) {
            if (button.equals(activeButton)) {
                button.getStyleClass().clear();
                button.getStyleClass().add("sidebar-button-active");
            } else {
                button.getStyleClass().clear();
                button.getStyleClass().add("sidebar-button");
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            mainTitleLabel.setText("Welcome");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}