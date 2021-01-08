package com.group8.controllers.viewcontroller;

import java.net.URL;
import java.util.ResourceBundle;

import com.group8.helper.UIHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class ReportViewController implements Initializable {
    @FXML
    private StackPane reportStackPane;
    @FXML
    private BorderPane reportBorderPane;
    @FXML
    private TabPane reportTabPane;
    @FXML
    private GridPane projectBreadcrumb;


    private static UIHelper uiHelper = new UIHelper();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        try {
            uiHelper.loadProjectBreadcrumbs(projectBreadcrumb);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}