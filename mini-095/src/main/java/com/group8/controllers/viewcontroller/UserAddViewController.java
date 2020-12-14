package com.group8.controllers.viewcontroller;

import com.group8.controllers.UserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserAddViewController implements Initializable {

    private static UserController userController = new UserController();

    @FXML
    private StackPane dialogPane;
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

        username = this.username.getText();
        fullname = this.fullname.getText();
        password = this.password.getText();
        emailAddress = this.emailAddress.getText();

        RadioButton selectedRadioButton = (RadioButton) userRoleToggle.getSelectedToggle();
        userRole = selectedRadioButton.getText();

        boolean result = userController.createUser(username, fullname, password, emailAddress, userRole);

        if(result) {
            UserViewController uvController = new UserViewController();
            uvController.actionFeedback("User created successfully!");
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
}