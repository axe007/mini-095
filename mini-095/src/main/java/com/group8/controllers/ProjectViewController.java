package com.group8.controllers;

import com.group8.App;
import com.group8.constants.Constants;
import com.group8.model.UserType;
import com.group8.model.UserWithoutPm;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProjectViewController implements Initializable {

    @FXML
    private SplitPane mainSplitPane;
    @FXML
    private Label mainTitleLabel;

    @FXML
    private Pane topPane;
    @FXML
    private ListView<UserWithoutPm> employeeListView;
    @FXML
    private VBox userInfoVBox;
    @FXML
    private HBox userInfoRoleHBox;
    @FXML
    private RadioButton managerRadioButton;
    @FXML
    private RadioButton developerRadioButton;
    @FXML
    private HBox nameHBox;
    @FXML
    private Label firstNameLabel;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private Label lastNameLabel;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private HBox contactHBox;
    @FXML
    private Label emailLabel;
    @FXML
    private TextField emailTextField;
    @FXML
    private Label phoneLabel;
    @FXML
    private TextField phoneTextField;
    @FXML
    private Button deleteButton;
    @FXML
    private Button clearButton;
    @FXML
    private Button addSaveButton;
    @FXML
    private Label buttonClickLabel;

    @FXML
    private Button logOutButton;

    @FXML
    private void handleLogOutButton(ActionEvent event) throws IOException {
        App.setRoot("LoginView");
    }

    @FXML
    private void handleSidebarBtn(ActionEvent event) throws IOException {
        // clear all text field

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            mainTitleLabel.setText("Projects");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateListView() {
        employeeListView.getItems().clear();
        for (UserWithoutPm user : UserWoPmController.userList) {
            employeeListView.getItems().add(user);
        }
    }

    @FXML
    protected void changeText(String someText) {
        buttonClickLabel.setText(someText + "clicked!");
    }
}