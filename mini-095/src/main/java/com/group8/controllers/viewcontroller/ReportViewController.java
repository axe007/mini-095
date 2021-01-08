package com.group8.controllers.viewcontroller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

/**
 * ReportViewController
 */
public class ReportViewController implements Initializable {
    @FXML
    private StackPane reportStackPane;
    @FXML
    private BorderPane reportBorderPane;
    @FXML
    private TabPane reporTabPane;
    @FXML
    private Button newReportButton;
    @FXML
    private Button editReportButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub

    }

    @FXML
    private void handleNewButton(ActionEvent event) {
    }

    @FXML
    private void handleEditButton(ActionEvent event) {
    }
}