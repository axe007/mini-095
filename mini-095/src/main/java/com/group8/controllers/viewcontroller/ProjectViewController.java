package com.group8.controllers.viewcontroller;

import com.group8.controllers.ApplicationController;
import com.group8.controllers.ProjectController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProjectViewController implements Initializable {

    private static ProjectController proController = new ProjectController();

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
    private TextField projectSearch;

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
            ApplicationController appControl = new ApplicationController();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void projectSearchOnKeyReleased(KeyEvent event) {
        /*tblSellView.getItems().clear();
        projectController.projectID = projectSearch.getText();
        sellCartGerway.searchView(sellCart);*/
    }

    @FXML
    private void btnRefreshOnAction(ActionEvent event) {
        /*tblSellView.getItems().clear();
        tfSearch.clear();
        sellCart.carts.clear();*/
    }

    /*private void updateListView() {
        employeeListView.getItems().clear();
        for (UserWithoutPm user : UserWoPmController.userList) {
            employeeListView.getItems().add(user);
        }
    }*/

}