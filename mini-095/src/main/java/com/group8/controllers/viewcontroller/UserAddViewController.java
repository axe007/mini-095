package com.group8.controllers.viewcontroller;

import com.group8.App;
import com.group8.controllers.ApplicationController;
import com.group8.controllers.UserController;
import com.group8.helper.UIHelper;
import com.group8.model.Activity;
import com.group8.model.Session;
import com.group8.model.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserAddViewController implements Initializable {

    private static UserController userController = new UserController();
    private static UIHelper uiHelper = new UIHelper();
    private static ArrayList<User> userList = new ArrayList<>();

    @FXML
    private StackPane dialogPane;
    @FXML
    private StackPane userView;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label windowModeTitle;
    @FXML
    private VBox vboxPane;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField username;
    @FXML
    private TextField fullname;
    @FXML
    private PasswordField password;
    @FXML
    private TextField emailAddress;
    @FXML
    private ToggleGroup userRoleToggle;
    @FXML
    private Toggle developer;
    @FXML
    private Toggle projectManager;
    @FXML
    private Toggle scrumMaster;

    @FXML
    private void handleSaveUserBtn(ActionEvent event) throws IOException {
        // clear all text field
        String username;
        String fullname;
        String password;
        String emailAddress;
        String userRole;
        String alertHeading = "Creating new user";
        String alertContent = "New user successfully created.\nPlease refresh in Users view.";

        username = this.username.getText();
        fullname = this.fullname.getText();
        password = this.password.getText();
        emailAddress = this.emailAddress.getText();

        // Validate user name
        User oldUser = (User) Session.getOpenItem();
        String userOldName = "";
        ArrayList<String> userNames = new ArrayList<>();

        if (oldUser != null) {
            userOldName = oldUser.getUsername();
        }
        // Building existing username list
        for (User user : userList) {
            if (Session.getWindowMode().equals("new")) {
                userNames.add(user.getUsername());
            } else if (Session.getWindowMode().equals("edit") && !user.getUsername().equals(userOldName)) {
                userNames.add(user.getUsername());
            }
        }

        // Validate entered username
        for (String userName : userNames) {
            if (username.equals(userName)) {
                uiHelper.alertDialogGenerator(dialogPane,"error", alertHeading, "Duplicate username.\nPlease enter a different username and try again.");
                this.username.getStyleClass().add("textfield-error-highlight");
                this.username.requestFocus();
                return;
            }
        }

        if (!uiHelper.validateEmailAddress(emailAddress)) {
            uiHelper.alertDialogGenerator(dialogPane,"error", alertHeading, "Invalid email address.\nPlease check email address and try again.");
            this.emailAddress.getStyleClass().add("textfield-error-highlight");
            this.emailAddress.requestFocus();
            return;
        }

        RadioButton selectedRadioButton = (RadioButton) userRoleToggle.getSelectedToggle();
        userRole = selectedRadioButton.getText();

        UIHelper uiHelper = new UIHelper();
        boolean validation = true;

        if (Session.getWindowMode().equals("new")) {

        } else if (Session.getWindowMode().equals("edit")) {
            alertHeading = "Edit user details";
        }

        if (username.equals("") || fullname.equals("") || password.equals("") || emailAddress.equals("")) {
            uiHelper.alertDialogGenerator(dialogPane,"error", alertHeading, "No fields can be empty.\nPlease check user details and try again.");
            return;
        } else {
            if (Session.getWindowMode().equals("new")) {
                userController.createUser(username, password, fullname, emailAddress, userRole);
            } else if (Session.getWindowMode().equals("edit")) {
                userController.modifyUser(username, password, fullname, emailAddress, userRole);
                alertHeading = "Edit user details";
                alertContent = "User details successfully updated.\nPlease refresh in Users view.";
            }
            Optional<ButtonType> result = uiHelper.alertDialogGenerator(dialogPane,"success", alertHeading, alertContent);
            if (result.get() == ButtonType.OK) {
                Stage stage = (Stage) saveButton.getScene().getWindow();
                stage.close();
            }
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) throws IOException {
        // TODO
        Stage stage;
        stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            userList.clear();
            userList = userController.getUserList();

            if (Session.getWindowMode().equals("new")) {
                windowModeTitle.setText("Enter new user details:");
            } else if (Session.getWindowMode().equals("edit")) {
                windowModeTitle.setText("Edit user details:");

                User user = (User) Session.getOpenItem();
                username.setText(user.getUsername());
                password.setText(user.getPassword());
                fullname.setText(user.getFullname());
                emailAddress.setText(user.getEmailAddress());

                if (user.getUserRole().equals("Developer")) {
                    developer.setSelected(true);
                } else if (user.getUserRole().equals("Project Manager")) {
                    projectManager.setSelected(true);
                } else if (user.getUserRole().equals("Scrum Master")) {
                    scrumMaster.setSelected(true);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}