package com.group8.controllers.viewcontroller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.time.LocalDate;

import com.group8.controllers.ActivityController;

public class ActivityAddViewController {
    @FXML
    private StackPane dialogPane;

    @FXML
    private AnchorPane mainContainer;

    @FXML
    private VBox vboxPane;

    @FXML
    private Label windowModeTitle;

    @FXML
    private TextField username;

    @FXML
    private TextField fullname;

    @FXML
    private PasswordField password;

    @FXML
    private TextField emailAddress;

    @FXML
    private RadioButton developer;

    @FXML
    private ToggleGroup priority;

    @FXML
    private RadioButton projectManager;

    @FXML
    private RadioButton scrumMaster;

    @FXML
    private ToggleGroup status;

    @FXML
    private ToggleGroup activityType;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    void handleCancel(ActionEvent event) {
        Stage stage;
        stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void handleSaveUserBtn(ActionEvent event) {
        String Id;
        String name;
        String content;
        LocalDate startDate;
        LocalDate endDate;
        String alertHeading = "Creating new activity";
        String alertContent = "New activity successfully created.\nPlease refresh in Activities view.";

    }

}
