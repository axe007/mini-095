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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.bson.types.ObjectId;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
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
    private GridPane projectBreadcrumb;
    @FXML
    private Label openProjectStartDate;
    @FXML
    private Label openProjectEndDate;
    @FXML
    private TableView<Project> tblProjects;
    @FXML
    private TableColumn<Project, ObjectId> tblClmProjectId;
    @FXML
    private TableColumn<Project, String> tblClmProjectName;
    @FXML
    private TableColumn<Project, String> tblClmProjectDescription;
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
            openProject();

        } else if (event.getSource() == projectListButton) {
            // List all projects window

        } else if (event.getSource() == projectArchiveButton) {
            // Archive project window
            // proController.overwriteActivityListDelete(); // DO NOT RUN UNLESS YOU KNOW WHAT IT IS
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            // TODO
            loadProjectData();

            uiHelper.loadProjectBreadcrumbs(projectBreadcrumb);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadProjectData() {

        //getting the full list of books from file
        List<Project> projectList = proController.getProjectList();
        ObservableList<Project> viewProjects = (ObservableList<Project>) FXCollections.observableArrayList(projectList);

        tblClmProjectId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblClmProjectId.setPrefWidth(50.0);
        tblClmProjectName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tblClmProjectName.setPrefWidth(200.0);
        tblClmProjectDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tblClmProjectDescription.setPrefWidth(320.0);
        tblClmProjectStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        tblClmProjectEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        tblClmProjectType.setCellValueFactory(new PropertyValueFactory<>("type"));
        tblClmProjectStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        tblProjects.setItems(viewProjects);
        tblProjects.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tblProjects.setOnMouseClicked((MouseEvent mouseEvent) -> {
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                if(mouseEvent.getClickCount() == 2) {
                    openProject();
                }
            }
        });
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

    private void openProject() {
        Project project = tblProjects.getSelectionModel().getSelectedItem();
        if (project == null) {
            uiHelper.alertDialogGenerator(projectView,"error", "Open project", "No project exist or no project selected.\nPlease select a project and try again.");
        } else {
            boolean success = proController.openProject(project);

            if (success) {
                uiHelper.loadProjectBreadcrumbs(projectBreadcrumb);
                uiHelper.alertDialogGenerator(projectView, "success", "Open project", "Successfully opened project:\n" + project.getName());
            }
        }
    }
}