package com.group8.controllers.viewcontroller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.group8.controllers.NoteController;
import com.group8.controllers.UserController;
import com.group8.model.Activity;
import com.group8.model.Note;
import com.group8.model.NoteType;
import com.group8.model.Session;
import com.group8.model.Sprint;

import org.bson.types.ObjectId;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;

public class NoteViewController implements Initializable {
    NoteController noteController = new NoteController();
    ObjectId currentUserID = Session.getSessionUserId();
    ObjectId currentProjectID = Session.getOpenProjectId();
    String currentProjectName = Session.getOpenProjectName();
    UserController userController = new UserController();
    ArrayList<Note> projectNoteList;
    ArrayList<Note> sprintNoteList;
    ArrayList<Note> activityNoteList;
    ArrayList<Sprint> sprintList;
    ArrayList<Activity> activityList;

    // private ObjectId noteID;
    // private ObjectId projectID;
    // private ObjectId targetID; // SprintID or ActivityID or projectID
    // private String targetName;
    // private String type;// SprintNote or ActivityNote or ProjectNote
    // private ObjectId userID;
    // private String userName;
    // private LocalDate createDate;
    // private String content;

    @FXML
    private Button newNoteButton;
    @FXML
    private Button editNoteButton;
    @FXML
    private TreeTableView<Note> activityTreeTableView;
    @FXML
    private TreeTableView<Note> projectTreeTableView;
    @FXML
    private TreeTableView<Note> sprintTreeTableView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        noteController.getNoteListForCurrentProject(currentProjectID);
        String currentUserName = userController.getUserDetail(currentUserID, "fullname");

        noteTreeTableSetUp(projectTreeTableView, true);
        noteTreeTableSetUp(sprintTreeTableView, false);
        noteTreeTableSetUp(activityTreeTableView, false);

    }

    private void noteTreeTableSetUp(TreeTableView<Note> treeView, boolean showRoot) {
        TreeTableColumn<Note, String> projectTreeTableColumn1 = new TreeTableColumn<>("Title");
        projectTreeTableColumn1.setPrefWidth(80.0);
        projectTreeTableColumn1.setMinWidth(80.0);
        TreeTableColumn<Note, String> projectTreeTableColumn2 = new TreeTableColumn<>("Date");
        projectTreeTableColumn2.setPrefWidth(80);
        projectTreeTableColumn2.setMinWidth(80.0);
        TreeTableColumn<Note, String> projectTreeTableColumn3 = new TreeTableColumn<>("User");
        projectTreeTableColumn3.setPrefWidth(80.0);
        projectTreeTableColumn3.setMinWidth(80.0);

        projectTreeTableColumn1.setCellValueFactory(new TreeItemPropertyValueFactory<>("noteTitle"));
        projectTreeTableColumn2.setCellValueFactory(new TreeItemPropertyValueFactory<>("createDate"));
        projectTreeTableColumn3.setCellValueFactory(new TreeItemPropertyValueFactory<>("userName"));

        treeView.getColumns().add(projectTreeTableColumn1);
        treeView.getColumns().add(projectTreeTableColumn2);
        treeView.getColumns().add(projectTreeTableColumn3);

        Note projectRootNote = new Note(currentProjectID, currentProjectID, currentProjectID, currentProjectName,
                NoteType.PROJECT_NOTE, currentUserID, "", null, currentProjectName, "");
        TreeItem<Note> projectRootItem = new TreeItem<Note>(projectRootNote);
        projectRootItem.setExpanded(true);

        treeView.setRoot(projectRootItem);
        treeView.setShowRoot(showRoot);
    }

}
