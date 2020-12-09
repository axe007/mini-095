package com.group8.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.group8.App;
import com.group8.constants.Constants;
import com.group8.model.UserType;
import com.group8.model.UserWithoutPm;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.scene.layout.HBox;

public class AdminViewController implements Initializable {
    UserWithoutPm currentUser;
    int seletectIndex = -1;
    @FXML
    private VBox adminView;
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
    private Button logOutButton;

    @FXML
    private void handleManagerRadioButtonAction(ActionEvent event) throws IOException {
        seleteRole(true);
    }

    @FXML
    private void handleDeveloperRadioButtonAction(ActionEvent event) throws IOException {
        seleteRole(false);
    }

    @FXML
    private void handleLogOutButtonAction(ActionEvent event) throws IOException {
        App.setRoot("LoginView");
    }

    @FXML
    private void handleDeleteButtonAction(ActionEvent event) throws IOException {
        // Delete selected User
        if (currentUser != null) {
            UserWoPmController.userList.remove(currentUser);
            currentUser = null;
        }

    }

    @FXML
    private void handleClearButtonAction(ActionEvent event) throws IOException {
        // clear all text field
        clearUserInfo();
    }

    private void clearUserInfo() {
        firstNameTextField.clear();
        lastNameTextField.clear();
        phoneTextField.clear();
        emailTextField.clear();
    }

    @FXML
    private void handleAddSaveButtonAction(ActionEvent event) throws IOException {
        // Save or add current user to list
        switch (addSaveButton.getText()) {
            case Constants.ADD:
                UserWithoutPm newUser = createUser();
                UserWoPmController.addUserToList(newUser);
                updateListView();
                break;
            case Constants.SAVE:
                if (currentUser != null) {
                    newUser = createUser();
                    UserWoPmController.updateUserToList(currentUser, newUser);
                    updateListView();
                }
                break;

            default:
                break;
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        employeeListView.setCellFactory(new Callback<ListView<UserWithoutPm>, ListCell<UserWithoutPm>>() {
            @Override
            public ListCell<UserWithoutPm> call(ListView<UserWithoutPm> list) {
                return new UserCell();
            }

        });

        for (UserWithoutPm user : UserWoPmController.userList) {
            employeeListView.getItems().add(user);
        }
        employeeListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<UserWithoutPm>() {
            public void changed(ObservableValue<? extends UserWithoutPm> ov, UserWithoutPm old_val,
                    UserWithoutPm new_val) {
                currentUser = new_val;
                seletectIndex = employeeListView.getSelectionModel().getSelectedIndex();
                if (seletectIndex > 0) {
                    addSaveButton.setText(Constants.SAVE);
                } else {
                    addSaveButton.setText(Constants.ADD);
                }
                updateUserInfo();
            }
        });
    }

    static class UserCell extends ListCell<UserWithoutPm> {
        @Override
        public void updateItem(UserWithoutPm user, boolean empty) {
            super.updateItem(user, empty);
            // HBox cellHBox = new HBox(8);
            // Label fisrtNameCellLabel = new Label(user.getFirstName());
            // Label lastNameCellLabel = new Label(user.getLastName());
            // Label roleCellLabe = new Label(user.getUserType().toString());

            // cellHBox.getChildren().add(fisrtNameCellLabel);
            // cellHBox.getChildren().add(lastNameCellLabel);
            // cellHBox.getChildren().add(roleCellLabe);

            if (user != null) {
                setText(user.toString());
            }
        }
    }

    private void updateUserInfo() {

        if (currentUser != null) {
            firstNameTextField.setText(currentUser.getFirstName());
            lastNameTextField.setText(currentUser.getLastName());
            emailTextField.setText(currentUser.getEmail());
            phoneTextField.setText(currentUser.getPhoneNumber());
            switch (currentUser.getUserType()) {
                case Manager:
                    seleteRole(true);
                    break;

                default:
                    seleteRole(false);
                    break;
            }
        }

    }

    private void seleteRole(boolean isManager) {
        if (isManager) {
            developerRadioButton.setSelected(false);
            managerRadioButton.setSelected(true);
        } else {
            developerRadioButton.setSelected(true);
            managerRadioButton.setSelected(false);
        }
    }

    private UserWithoutPm createUser() {
        UserType type;
        String firstName;
        String lastName;
        String phoneNumber;
        String email;
        if (managerRadioButton.isSelected()) {
            type = UserType.Manager;
        } else {
            type = UserType.Developer;
        }
        firstName = firstNameTextField.getText();
        lastName = lastNameTextField.getText();
        phoneNumber = phoneTextField.getText();
        email = emailTextField.getText();

        return new UserWithoutPm(type, Constants.DEFAULT_USER_PASSWORD, firstName, lastName, phoneNumber, email);
    }

    private void updateListView() {
        employeeListView.getItems().clear();
        for (UserWithoutPm user : UserWoPmController.userList) {
            employeeListView.getItems().add(user);
        }
    }
}