package com.group8.controllers.viewcontroller;

import com.group8.App;
import com.group8.controllers.*;
import com.group8.helper.UIHelper;
import com.group8.model.Session;
import com.group8.model.User;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserViewController implements Initializable {

    private static UserController userController = new UserController();
    private static UIHelper uiHelper = new UIHelper();
    private List<User> userList = new ArrayList<>();

    @FXML
    private StackPane userView;
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
    private TextArea dbFeedback;

    @FXML
    private void handleUserButtons(ActionEvent event) throws IOException {
        // clear all text field
        if (event.getSource() == userNewButton) {
            Session.setWindowMode("new");
            uiHelper.loadWindow("UserAddView", userNewButton, "Create new user");

        } else if (event.getSource() == userModifyButton) {
            // Modify user details
            User user = tblUsers.getSelectionModel().getSelectedItem();
            Session.setSetOpenItem(user);
            if (user == null) {
                uiHelper.alertDialogGenerator(userView,"error", "Modify user", "No user exist or no user selected.\nPlease select an user and try again.");
            } else {
                Session.setWindowMode("edit");
                uiHelper.loadWindow("UserAddView", userModifyButton, "Edit user details");
            }
        } else if (event.getSource() == userAssignButton) {
            // List all projects window
            ObjectId projectId = Session.getOpenProjectId();
            if (projectId == null || projectId.equals(null)) {
                uiHelper.alertDialogGenerator(userView,"error", "Assign user", "No project has been opened.\nPlease open a project in Projects window.");
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

        //getting the full list of books from file
        List<User> userList = userController.getUserList();
        ObservableList<User> viewUsers = (ObservableList<User>) FXCollections.observableArrayList(userList);

        tblClmUserId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblClmUserName.setCellValueFactory(new PropertyValueFactory<>("username"));
        tblClmUserFullname.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        tblClmUserRole.setCellValueFactory(new PropertyValueFactory<>("userRole"));
        tblClmUserEmailAddress.setCellValueFactory(new PropertyValueFactory<>("emailAddress"));

        tblUsers.setItems(viewUsers);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            // TODO
            //initCol();
            loadUserData();
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
        loadUserData();
    }

    @FXML
    public void actionFeedback(String result) {
        //dbFeedback.setText(result);
        //System.out.println(result);
    }
}