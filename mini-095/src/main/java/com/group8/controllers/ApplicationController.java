package com.group8.controllers;

import com.group8.App;
import com.group8.helper.UIHelper;
import com.group8.model.Session;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ApplicationController implements Initializable {

    private static UIHelper uiHelper = new UIHelper();

    // MARK: UI Entry Point
    @FXML
    private SplitPane mainSplitPane;
    @FXML
    private Label mainTitleLabel = new Label("Main Title");
    @FXML
    private Label sessionUsername = new Label("John Doe");
    @FXML
    public StackPane appContent;
    @FXML
    private Button dashboardButton;
    @FXML
    private Button projectButton;
    @FXML
    private Button sprintboardButton;
    @FXML
    private Button activitiesButton;
    @FXML
    private Button reportsButton;
    @FXML
    private Button usersButton;
    @FXML
    private Button logoutButton;

    @FXML
    public void handleSidebarBtn(ActionEvent menuEvent) throws IOException {
        String viewName = "HomeView";
        String viewTitle = "Title";
        ObjectId projectId = Session.getOpenProjectId();

        // clear all text field
        if (menuEvent.getSource() == dashboardButton) {
            viewName = "HomeView";
            viewTitle = "Welcome";
        } else if (menuEvent.getSource() == projectButton) {
            viewName = "ProjectView";
            viewTitle = "Projects";
        } else if (projectId == null && (menuEvent.getSource() == sprintboardButton || menuEvent.getSource() == activitiesButton || menuEvent.getSource() == reportsButton)) {
            uiHelper.alertDialogGenerator(appContent,"error", "No project open", "No project has been opened.\nPlease open a project in Projects window.");
            return;
        } else if (projectId != null && menuEvent.getSource() == sprintboardButton) {
            viewName = "ScrumboardView";
            viewTitle = "Scrum Board";
        } else if (projectId != null && menuEvent.getSource() == activitiesButton) {
            viewName = "ActivitiesView";
            viewTitle = "Activities";
        } else if (projectId != null && menuEvent.getSource() == reportsButton) {
            viewName = "GanttChartView";
            viewTitle = "Gantt Chart";
        } else if (menuEvent.getSource() == usersButton) {
            viewName = "UserView";
            viewTitle = "Users";
        } else if (menuEvent.getSource() == logoutButton) {
            Session.logoutSession();

            App app = new App();
            app.setRoot("LoginView");
        }

        Object activeButton = menuEvent.getSource();
        changeView(viewName, viewTitle, activeButton);
    }

    public void changeView(String viewName, String viewTitle, Object activeButton) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        StackPane loadedPane = fxmlLoader.load(App.class.getResource("fxml/content/" + viewName + ".fxml"));
        mainTitleLabel.setText(viewTitle);
        setActiveButton(activeButton);

        if (appContent == null) {
            appContent = new StackPane();
        }
        appContent.getChildren().clear();
        appContent.getChildren().add(loadedPane);
    }

    @FXML
    public void setActiveButton(Object activeButton) {
        Button[] sidebarButtons = new Button[] { dashboardButton, projectButton, sprintboardButton, activitiesButton,
                usersButton };

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
            // Set main title label
            mainTitleLabel.setText("Welcome");
            displayUserFullname();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayUserFullname() {
        // Display logged user fullname
        ObjectId loggedUserId = Session.getSessionUserId();

        String fullname;
        UserController userController = new UserController();
        fullname = userController.getUserDetail(loggedUserId, "fullname");

        sessionUsername.setText(fullname);
    }
}