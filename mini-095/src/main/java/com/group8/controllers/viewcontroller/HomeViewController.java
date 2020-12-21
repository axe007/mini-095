package com.group8.controllers.viewcontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeViewController implements Initializable {

    @FXML
    private SplitPane mainSplitPane;
    @FXML
    private Label mainTitleLabel;
    @FXML
    private Button projectNewButton;
    @FXML
    private Button projectOpenButton;
    @FXML
    private Button projectListButton;
    @FXML
    private Button projectArchiveButton;

    @FXML
    private Pane topPane;
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
    private void handleProjectButtons(ActionEvent event) throws IOException {
        // clear all text field
        if (event.getSource() == projectNewButton) {
            // Create new project window

        } else if (event.getSource() == projectOpenButton) {
            // Open project window

        } else if (event.getSource() == projectListButton) {
            // List all projects window

        } else if (event.getSource() == projectArchiveButton) {
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
    protected void changeText(String someText) {
        buttonClickLabel.setText(someText + "clicked!");
    }
}