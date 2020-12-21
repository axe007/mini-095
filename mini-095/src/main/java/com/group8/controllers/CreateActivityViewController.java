package com.group8.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateActivityViewController implements Initializable {

    @FXML
    private Text createActivity;
    @FXML
    private RadioButton userStoryRadioButton;
    @FXML
    private RadioButton taskRadioButton;
    @FXML
    private RadioButton bugRadioButton;
    @FXML
    private Label nameLabel;
    @FXML
    private Label startDateLabel;
    @FXML
    private Label priorityLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label endDateLabel;
    @FXML
    private TextField enterNameTextField;
    @FXML
    private TextField enterStartDateTextField;
    @FXML
    private TextField enterPriorityTextField;
    @FXML
    private TextField enterDescriptionTextField;
    @FXML
    private TextField enterEndDateTextField;
    @FXML
    private Label storyPointsLabel;
    @FXML
    private Label acceptanceCriteriaLabel;
    @FXML
    private TextField enterStoryPointsTextField;
    @FXML
    private TextField enterAcceptanceCriteriaTextField;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button clearButton;


    public void clearTextFields () {
        enterNameTextField.clear();
        enterStartDateTextField.clear();
        enterPriorityTextField.clear();
        enterDescriptionTextField.clear();
        enterEndDateTextField.clear();
        enterAcceptanceCriteriaTextField.clear();
        enterStoryPointsTextField.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
    }
}