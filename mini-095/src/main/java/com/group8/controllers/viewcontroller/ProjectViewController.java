package com.group8.controllers.viewcontroller;

import com.group8.controllers.ProjectController;
import com.group8.controllers.UserController;
import com.group8.helper.UIHelper;
import com.group8.model.Project;
import com.group8.model.Session;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
import java.util.ResourceBundle;

public class ProjectViewController implements Initializable {

    private static ProjectController proController = new ProjectController();
    private static UIHelper uiHelper = new UIHelper();

    public static BooleanProperty isUpdated = new SimpleBooleanProperty();

    @FXML
    private StackPane projectView;
    @FXML
    private Button projectNewButton;
    @FXML
    private Button projectModifyButton;
    @FXML
    private Button projectOpenButton;
    @FXML
    private Button projectCloseButton;
    @FXML
    private TextField projectSearch;
    @FXML
    private GridPane projectBreadcrumb;
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
        if (event.getSource() == projectNewButton || event.getSource() == projectModifyButton
                || event.getSource() == projectCloseButton) {
            ObjectId loggedUserId = Session.getSessionUserId();
            UserController userController = new UserController();
            String userRole = userController.getUserDetail(loggedUserId, "userRole");

            if (userRole.equals("Developer")) {
                // Deny access
                uiHelper.alertDialogGenerator(projectView, "error", "Unauthorized action",
                        "You have to be either \"Project Manager\" or\n \"Scrum Master\" user to perform this action.");
                return;

            } else {
                if (event.getSource() == projectNewButton) {
                    Session.setWindowMode("new");
                    uiHelper.loadWindow("ProjectAddView", projectNewButton, (Project) null);

                } else if (event.getSource() == projectModifyButton) {
                    // Modify project details
                    Project project = tblProjects.getSelectionModel().getSelectedItem();
                    Session.setSetOpenItem(project);
                    if (project == null) {
                        uiHelper.alertDialogGenerator(projectView, "error", "Modify project",
                                "No project exist or no project selected.\nPlease select a project and try again.");
                    } else {
                        Session.setWindowMode("edit");
                        uiHelper.loadWindow("ProjectAddView", projectModifyButton, project);
                    }

                } else if (event.getSource() == projectCloseButton) {
                    // Method for closing a project
                    Project project = tblProjects.getSelectionModel().getSelectedItem();
                    Session.setSetOpenItem(project);
                    if (project == null) {
                        uiHelper.alertDialogGenerator(projectView, "error", "Close project",
                                "No project exist or no project selected.\nPlease select a project and try again.");
                        return;
                    } else {
                        String projectStatus = project.getStatus();
                        if (projectStatus.equals("Closed")) {
                            uiHelper.alertDialogGenerator(projectView, "error", "Close project",
                                    "This project is already \"Closed\".\nPlease try again later.");
                            return;
                        } else {
                            Session.setWindowMode("edit");
                            uiHelper.loadWindow("ProjectCloseView", projectCloseButton, project);
                        }
                    }
                }
            }
        } else if (event.getSource() == projectOpenButton) {
            // Open project window
            openProject();
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
        isUpdated.addListener((observable, oldValue, newValue) -> {
            // Only if completed
            if (newValue == true) {
                loadProjectData();
                uiHelper.loadProjectBreadcrumbs(projectBreadcrumb);
                isUpdated.setValue(false);
            }

        });
    }

    public void loadProjectData() {

        // getting the full list of books from file
        ArrayList<Project> projectList = proController.getProjectList();
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
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                if (mouseEvent.getClickCount() == 2) {
                    openProject();
                }
            }
        });
    }

    @FXML
    private void projectSearchOnKeyReleased(KeyEvent event) {
        /*
         * tblSellView.getItems().clear(); projectController.projectID =
         * projectSearch.getText(); sellCartGerway.searchView(sellCart);
         */
    }

    private void openProject() {
        Project project = tblProjects.getSelectionModel().getSelectedItem();
        if (project == null) {
            uiHelper.alertDialogGenerator(projectView, "error", "Open project",
                    "No project exist or no project selected.\nPlease select a project and try again.");
        } else {
            String projectStatus = project.getStatus();
            ObjectId userId = Session.getSessionUserId();
            UserController userController = new UserController();
            String userRole = userController.getUserDetail(userId, "userRole");

            if (projectStatus.equals("Closed") && userRole.equals("Developer")) {
                uiHelper.alertDialogGenerator(projectView, "error", "Open project",
                        "This project is Closed. Only Project Manager or Scrum Master users can open.\nPlease try again later.");
                return;
            } else {
                proController.openProject(project);
                uiHelper.alertDialogGenerator(projectView, "success", "Open project",
                        "Successfully opened project:\n" + project.getName());
                uiHelper.loadProjectBreadcrumbs(projectBreadcrumb);
            }
        }
    }
}