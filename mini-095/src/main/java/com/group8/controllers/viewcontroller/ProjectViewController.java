package com.group8.controllers.viewcontroller;

import com.group8.controllers.ApplicationController;
import com.group8.controllers.ProjectController;
import com.group8.helper.UIHelper;
import com.group8.model.Project;
import com.group8.model.Session;
import com.group8.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import org.bson.types.ObjectId;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProjectViewController implements Initializable {

    private static ProjectController proController = new ProjectController();
    private static UIHelper uiHelper = new UIHelper();
    private List<Project> projectList = new ArrayList<>();

    @FXML
    private StackPane projectView;
    @FXML
    private Button projectNewButton;
    @FXML
    private Button projectModifyButton;
    @FXML
    private Button projectOpenButton;
    @FXML
    private Button projectListButton;
    @FXML
    private Button projectArchiveButton;
    @FXML
    private TextField projectSearch;
    @FXML
    private TableView<Project> tblProjects;
    @FXML
    private TableColumn<Project, ObjectId> tblClmProjectId;
    @FXML
    private TableColumn<Project, String> tblClmProjectName;
    @FXML
    private TableColumn<Project, String> tblClmProjectStartDate;
    @FXML
    private TableColumn<Project, String> tblClmProjectEndDate;
    @FXML
    private TableColumn<Project, String> tblClmProjectType;
    @FXML
    private TableColumn<Project, String> tblClmProjectStatus;

    @FXML
    private void handleProjectButtons(ActionEvent event) throws IOException {
        // clear all text field
        if (event.getSource() == projectNewButton) {
            Session.setWindowMode("new");
            uiHelper.loadWindow("ProjectAddView", projectNewButton, (Project) null);

        } else if (event.getSource() == projectModifyButton) {
            // Modify project details
            Project project = tblProjects.getSelectionModel().getSelectedItem();
            Session.setSetOpenItem(project);
            if (project == null) {
                uiHelper.alertDialogGenerator(projectView,"error", "Modify project", "No project exist or no project selected.\nPlease select a project and try again.");
            } else {
                Session.setWindowMode("edit");
                uiHelper.loadWindow("ProjectAddView", projectModifyButton, project);
            }

        } else if (event.getSource() == projectOpenButton) {
            // Open project window
            Project project = tblProjects.getSelectionModel().getSelectedItem();
            if (project == null) {
                uiHelper.alertDialogGenerator(projectView,"error", "Open project", "No project exist or no project selected.\nPlease select a project and try again.");
            } else {
                boolean success = proController.openProject(project.getId());
                if (success) {
                    uiHelper.alertDialogGenerator(projectView, "success", "Open project", "Successfully opened project:\n" + project.getName());
                    System.out.println(Session.getOpenProjectId());
                }
            }

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
            loadProjectData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadProjectData() {

        //getting the full list of books from file
        List<Project> projectList = proController.getProjectList();
        ObservableList<Project> viewProjects = (ObservableList<Project>) FXCollections.observableArrayList(projectList);

        tblClmProjectId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblClmProjectName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tblClmProjectStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        tblClmProjectEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        tblClmProjectType.setCellValueFactory(new PropertyValueFactory<>("type"));
        tblClmProjectStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        tblProjects.setItems(viewProjects);
    }

    @FXML
    private void projectSearchOnKeyReleased(KeyEvent event) {
        /*tblSellView.getItems().clear();
        projectController.projectID = projectSearch.getText();
        sellCartGerway.searchView(sellCart);*/
    }

    @FXML
    private void btnRefreshOnAction(ActionEvent event) {
        loadProjectData();
    }

    /*private void updateListView() {
        employeeListView.getItems().clear();
        for (UserWithoutPm user : UserWoPmController.userList) {
            employeeListView.getItems().add(user);
        }
    }*/

}