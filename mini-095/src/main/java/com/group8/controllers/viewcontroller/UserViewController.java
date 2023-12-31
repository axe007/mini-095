package com.group8.controllers.viewcontroller;

import com.group8.controllers.*;
import com.group8.helper.UIHelper;
import com.group8.model.Session;
import com.group8.model.User;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;

import org.bson.types.ObjectId;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import java.util.ResourceBundle;

public class UserViewController implements Initializable {

    private static UserController userController = new UserController();
    private static UIHelper uiHelper = new UIHelper();
    private static ArrayList<User> userList = new ArrayList<>();
    public static BooleanProperty isUpdated = new SimpleBooleanProperty();

    @FXML
    private StackPane userView;
    @FXML
    private GridPane projectBreadcrumb;
    @FXML
    private TableView<User> tblUsers = new TableView<User>();
    @FXML
    private TableColumn<User, ObjectId> tblClmUserId;
    @FXML
    private TableColumn<User, String> tblClmUserName;
    @FXML
    private TableColumn<User, String> tblClmUserFullname;
    @FXML
    private TableColumn<User, String> tblClmUserRole;
    @FXML
    private TableColumn<User, String> tblClmUserEmailAddress;

    @FXML
    private Button userNewButton;
    @FXML
    private Button userModifyButton;
    @FXML
    private Button userAssignButton;
    @FXML
    private Button userListButton;
    @FXML
    private Button userDeleteButton;
    @FXML
    private TextField activitySearch;

    @FXML
    private void handleUserButtons(ActionEvent event) throws IOException {
        // clear all text field
        if (event.getSource() == userNewButton) {
            Session.setWindowMode("new");
            uiHelper.loadWindow("UserAddView", userNewButton, "Create new user");

        } else if (event.getSource() == userModifyButton) {
            // Modify user details
            User user = tblUsers.getSelectionModel().getSelectedItem();
            if (user == null) {
                uiHelper.alertDialogGenerator(userView, "error", "Modify user",
                        "No user exist or no user selected.\nPlease select an user and try again.");
            } else {
                Session.setSetOpenItem(user);
                Session.setWindowMode("edit");
                uiHelper.loadWindow("UserAddView", userModifyButton, "Edit user details");
            }
        } else if (event.getSource() == userAssignButton) {
            // List all projects window
            ObjectId projectId = Session.getOpenProjectId();
            if (projectId == null || projectId.equals(null)) {
                uiHelper.alertDialogGenerator(userView, "error", "Assign user",
                        "No project has been opened.\nPlease open a project in Projects window.");
            } else {
                uiHelper.loadWindow("UserAssignView", userAssignButton, "Assign users to project");
            }

        } else if (event.getSource() == userListButton) {
            // List all projects window
            // userController.getUser();

        } else if (event.getSource() == userDeleteButton) {
            // Archive project window
        }
    }

    public void loadUserData() {

        // getting the full list of books from file
        userList = userController.getUserList();
        ObservableList<User> viewUsers = (ObservableList<User>) FXCollections.observableArrayList(userList);

        tblClmUserId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblClmUserName.setCellValueFactory(new PropertyValueFactory<>("username"));
        tblClmUserFullname.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        tblClmUserRole.setCellValueFactory(new PropertyValueFactory<>("userRole"));
        tblClmUserEmailAddress.setCellValueFactory(new PropertyValueFactory<>("emailAddress"));

        tblUsers.setItems(viewUsers);

        uiHelper.loadProjectBreadcrumbs(projectBreadcrumb);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            // TODO
            // initCol();
            loadUserData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        isUpdated.addListener((observable, oldValue, newValue) -> {
            // Only if completed
            if (newValue == true) {
                loadUserData();
                isUpdated.setValue(false);
            }

        });

    }

    @FXML
    private void userSearchOnKeyReleased(KeyEvent event) {
        /*
         * tblActivities.getItems().clear(); activityController.activityName =
         * activitiesSearch.getText();
         */
    }
}