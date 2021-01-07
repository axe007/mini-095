package com.group8.controllers.viewcontroller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.group8.controllers.ActivityController;
import com.group8.controllers.NoteController;
import com.group8.controllers.SprintController;
import com.group8.controllers.UserController;
import com.group8.helper.UIHelper;
import com.group8.model.Activity;
import com.group8.model.Note;
import com.group8.model.NoteType;
import com.group8.model.Session;
import com.group8.model.Sprint;

import org.bson.types.ObjectId;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

public class NoteViewController implements Initializable {
    private NoteController noteController = new NoteController();
    private ObjectId currentUserID = Session.getSessionUserId();
    private ObjectId currentProjectID = Session.getOpenProjectId();
    private String currentProjectName = Session.getOpenProjectName();
    private UserController userController = new UserController();
    private ArrayList<Note> projectNoteList;
    private ArrayList<Note> sprintNoteList;
    private ArrayList<Note> activityNoteList;
    public static ArrayList<Sprint> sprintList;
    public static ArrayList<Activity> activityList;
    public static BooleanProperty isUpdated = new SimpleBooleanProperty();
    private static UIHelper uiHelper = new UIHelper();

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
    @FXML
    private StackPane noteView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        noteController.getNoteListForCurrentProject(currentProjectID);
        String currentUserName = userController.getUserDetail(currentUserID, "fullname");

        noteTreeTableSetUp(projectTreeTableView, true);
        noteTreeTableSetUp(sprintTreeTableView, false);
        noteTreeTableSetUp(activityTreeTableView, false);

        loadNotes();

        isUpdated.addListener((observable, oldValue, newValue) -> {
            // Only if completed
            if (newValue == true) {
                loadNotes();

                isUpdated.setValue(false);
            }

        });
        projectTreeTableView.addEventFilter(MouseEvent.MOUSE_CLICKED, evt -> {
            Note parentNote = projectTreeTableView.getSelectionModel().getSelectedItem().getValue();
            if (parentNote.getUserName().isEmpty()) {
                projectTreeTableView.getSelectionModel().clearSelection();
                sprintTreeTableView.getSelectionModel().clearSelection();
                activityTreeTableView.getSelectionModel().clearSelection();
            } else {
                sprintTreeTableView.getSelectionModel().clearSelection();
                activityTreeTableView.getSelectionModel().clearSelection();
            }
        });
        sprintTreeTableView.addEventFilter(MouseEvent.MOUSE_CLICKED, evt -> {
            Note parentNote = sprintTreeTableView.getSelectionModel().getSelectedItem().getValue();
            if (parentNote.getUserName().isEmpty()) {
                projectTreeTableView.getSelectionModel().clearSelection();
                sprintTreeTableView.getSelectionModel().clearSelection();
                activityTreeTableView.getSelectionModel().clearSelection();
            } else {
                projectTreeTableView.getSelectionModel().clearSelection();
                activityTreeTableView.getSelectionModel().clearSelection();
            }
        });

        activityTreeTableView.addEventFilter(MouseEvent.MOUSE_CLICKED, evt -> {
            Note parentNote = activityTreeTableView.getSelectionModel().getSelectedItem().getValue();
            if (parentNote.getUserName().isEmpty()) {
                projectTreeTableView.getSelectionModel().clearSelection();
                sprintTreeTableView.getSelectionModel().clearSelection();
                activityTreeTableView.getSelectionModel().clearSelection();
            } else {
                projectTreeTableView.getSelectionModel().clearSelection();
                sprintTreeTableView.getSelectionModel().clearSelection();
            }
        });
    }

    public void loadNotes() {
        noteController.getNoteListForCurrentProject(currentProjectID);
        projectNoteList = noteController.getProjectNoteList();
        sprintNoteList = noteController.getSprintNoteList();
        activityNoteList = noteController.getActitivyNoteList();
        addNoteToTreeTableView(projectNoteList, projectTreeTableView);
        addChildNoteToTreeTableView(activityNoteList, activityTreeTableView, NoteType.ACTIVITY_NOTE);
        addChildNoteToTreeTableView(sprintNoteList, sprintTreeTableView, NoteType.SPRINT_NOTE);
    }

    public void addNoteToTreeTableView(ArrayList<Note> noteList, TreeTableView<Note> treeView) {

        for (Note note : noteList) {
            treeView.getRoot().getChildren().add(new TreeItem<Note>(note));
        }

    }

    public void addChildNoteToTreeTableView(ArrayList<Note> noteList, TreeTableView<Note> treeView, String type) {
        if (type.equals(NoteType.ACTIVITY_NOTE)) {
            ActivityController activityController = new ActivityController();
            activityList = activityController.getActivitiesList();
            for (Activity activity : activityList) {
                Note parentNote = new Note(currentProjectID, currentProjectID, activity.getId(), activity.getName(),
                        NoteType.ACTIVITY_NOTE, currentUserID, "", null, activity.getName(), "");
                TreeItem<Note> parentItem = new TreeItem<Note>(parentNote);
                parentItem.setExpanded(true);

                for (Note note : noteList) {
                    if (note.getTargetID().equals(activity.getId())) {
                        parentItem.getChildren().add(new TreeItem<Note>(note));
                    }
                }

                treeView.getRoot().getChildren().add(parentItem);

            }

        } else if (type.equals(NoteType.SPRINT_NOTE)) {
            SprintController sprintController = new SprintController();
            sprintList = sprintController.getSprintList();
            for (Sprint sprint : sprintList) {
                Note parentNote = new Note(currentProjectID, currentProjectID, sprint.getId(), sprint.getName(),
                        NoteType.SPRINT_NOTE, currentUserID, "", null, sprint.getName(), "");
                TreeItem<Note> parentItem = new TreeItem<Note>(parentNote);
                parentItem.setExpanded(true);
                for (Note note : noteList) {
                    if (note.getTargetID().equals(sprint.getId())) {
                        parentItem.getChildren().add(new TreeItem<Note>(note));
                    }
                }

                treeView.getRoot().getChildren().add(parentItem);

            }

        }
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

    @FXML
    private void handelNewButton(ActionEvent event) throws IOException {
        Session.setWindowMode("new");
        uiHelper.loadWindow("NoteAddView", newNoteButton, "Create new note");
    }

    @FXML
    private void handelEditButton(ActionEvent event) throws IOException {
        Note projectNote = projectTreeTableView.getSelectionModel().getSelectedItem().getValue();
        Note sprintNote = sprintTreeTableView.getSelectionModel().getSelectedItem().getValue();
        Note activityNote = activityTreeTableView.getSelectionModel().getSelectedItem().getValue();
        if (projectNote == null || sprintNote == null || activityNote == null) {
            uiHelper.alertDialogGenerator(noteView, "error", "Edit Note",
                    "No Note was selected.\nPlease select a Note and try again.");
        } else {

            Session.setWindowMode("edit");
            if (projectNote != null) {
                Session.setSetOpenItem(projectNote);

            }
            if (sprintNote != null) {
                Session.setSetOpenItem(sprintNote);

            }
            if (activityNote != null) {
                Session.setSetOpenItem(activityNote);

            }

            uiHelper.loadWindow("NoteAddView", editNoteButton, "Edit note details");
        }

    }

}
