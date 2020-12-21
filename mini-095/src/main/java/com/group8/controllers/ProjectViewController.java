package com.group8.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class ProjectViewController {

    @FXML
    private Button closeButton;
    @FXML
    private Button newButton;
    @FXML
    private Button chooseButton;
    @FXML
    private ListView projectListView;

    @FXML
    private void handleCloseButtonAction(ActionEvent event) throws IOException {
        // close selected project if is manager and is in the project

    }

    @FXML
    private void handleNewButtonAction(ActionEvent event) throws IOException {
        // create new project
    }

    @FXML
    private void handleChooseButtonAction(ActionEvent event) throws IOException {
        // change the userView's current project if is the member of development.
    }

}
