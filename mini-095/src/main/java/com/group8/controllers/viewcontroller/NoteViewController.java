package com.group8.controllers.viewcontroller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.group8.controllers.ActivityController;
import com.group8.controllers.NoteController;
import com.group8.controllers.SprintController;
import com.group8.helper.UIHelper;
import com.group8.model.Activity;
import com.group8.model.Note;
import com.group8.model.NoteType;
import com.group8.model.Session;
import com.group8.model.Sprint;

import javafx.scene.layout.Region;
import org.bson.types.ObjectId;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class NoteViewController implements Initializable {
    private NoteController noteController = new NoteController();
    private ObjectId currentUserID = Session.getSessionUserId();
    private ObjectId currentProjectID = Session.getOpenProjectId();
    private String currentProjectName = Session.getOpenProjectName();
    public static ArrayList<Note> projectNoteList;
    public static ArrayList<Note> sprintNoteList;
    public static ArrayList<Note> activityNoteList;
    public static ArrayList<Sprint> sprintList;
    public static ArrayList<Activity> activityList;
    public static BooleanProperty isUpdated = new SimpleBooleanProperty();
    private static UIHelper uiHelper = new UIHelper();
    private int selectedTable = 0;

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

        noteTreeTableSetUp(projectTreeTableView, true);
        noteTreeTableSetUp(sprintTreeTableView, false);
        noteTreeTableSetUp(activityTreeTableView, false);

        loadNotes();
        editNoteButton.setDisable(true);

        isUpdated.addListener((observable, oldValue, newValue) -> {
            // Only if completed
            if (newValue == true) {
                loadNotes();
                projectTreeTableView.getSelectionModel().clearSelection();
                sprintTreeTableView.getSelectionModel().clearSelection();
                activityTreeTableView.getSelectionModel().clearSelection();
                selectedTable = 0;
                isUpdated.setValue(false);
            }

        });

        projectTreeTableView.addEventFilter(MouseEvent.MOUSE_CLICKED, evt -> {
            if (projectTreeTableView.getSelectionModel().getSelectedItem() != null ) {
                Note parentNote = projectTreeTableView.getSelectionModel().getSelectedItem().getValue();
                if (parentNote.getUserName().isEmpty()) {
                    projectTreeTableView.getSelectionModel().clearSelection();
                    editNoteButton.setDisable(true);
                    selectedTable = 0;
                } else {
                    if (!parentNote.getUserID().equals(currentUserID)) {
                        editNoteButton.setDisable(true);
                    } else {
                        editNoteButton.setDisable(false);
                        selectedTable = 1;
                    }
                }
            }
        });

        sprintTreeTableView.addEventFilter(MouseEvent.MOUSE_CLICKED, evt -> {
            if (sprintTreeTableView.getSelectionModel().getSelectedItem() != null) {
                Note parentNote = sprintTreeTableView.getSelectionModel().getSelectedItem().getValue();
                if (parentNote.getUserName().isEmpty()) {
                    sprintTreeTableView.getSelectionModel().clearSelection();
                    editNoteButton.setDisable(true);
                    selectedTable = 0;
                } else {
                    if (!parentNote.getUserID().equals(currentUserID)) {
                        editNoteButton.setDisable(true);
                    } else {
                        editNoteButton.setDisable(false);
                        selectedTable = 2;
                    }
                }
            }
        });

        activityTreeTableView.addEventFilter(MouseEvent.MOUSE_CLICKED, evt -> {
            if (activityTreeTableView.getSelectionModel().getSelectedItem() != null) {
                Note parentNote = activityTreeTableView.getSelectionModel().getSelectedItem().getValue();
                if (parentNote.getUserName().isEmpty()) {
                    activityTreeTableView.getSelectionModel().clearSelection();
                    editNoteButton.setDisable(true);
                    selectedTable = 0;
                } else {
                    if (!parentNote.getUserID().equals(currentUserID)) {
                        editNoteButton.setDisable(true);
                    } else {
                        editNoteButton.setDisable(false);
                        selectedTable = 3;

                    }
                }
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
        treeView.getRoot().getChildren().clear();
        for (Note note : noteList) {

            treeView.getRoot().getChildren().add(new TreeItem<Note>(note));

        }

    }

    public void addChildNoteToTreeTableView(ArrayList<Note> noteList, TreeTableView<Note> treeView, String type) {
        treeView.getRoot().getChildren().clear();
        if (type.equals(NoteType.ACTIVITY_NOTE)) {
            ActivityController activityController = new ActivityController();
            activityList = activityController.getActivitiesList();
            for (Activity activity : activityList) {
                Note parentNote = new Note(currentProjectID, activity.getId(), activity.getName(),
                        NoteType.ACTIVITY_NOTE, currentUserID, "", null, activity.getName(), "");
                TreeItem<Note> parentItem = new TreeItem<Note>(parentNote);
                parentItem.setExpanded(true);

                for (Note note : noteList) {
                    if (note.getTargetName().equals(activity.getName())) {
                        parentItem.getChildren().add(new TreeItem<Note>(note));

                    }
                }

                treeView.getRoot().getChildren().add(parentItem);

            }

        } else if (type.equals(NoteType.SPRINT_NOTE)) {
            SprintController sprintController = new SprintController();
            sprintList = sprintController.getSprintList();
            for (Sprint sprint : sprintList) {
                Note parentNote = new Note(currentProjectID, sprint.getId(), sprint.getName(), NoteType.SPRINT_NOTE,
                        currentUserID, "", null, sprint.getName(), "");
                TreeItem<Note> parentItem = new TreeItem<Note>(parentNote);
                parentItem.setExpanded(true);
                for (Note note : noteList) {
                    if (note.getTargetName().equals(sprint.getName())) {
                        parentItem.getChildren().add(new TreeItem<Note>(note));
                    }
                }

                treeView.getRoot().getChildren().add(parentItem);

            }

        }
    }

    private void noteTreeTableSetUp(TreeTableView<Note> treeView, boolean showRoot) {
        TreeTableColumn<Note, String> projectTreeTableColumn1 = new TreeTableColumn<>("Title");
        projectTreeTableColumn1.setPrefWidth(120.0);
        projectTreeTableColumn1.setMinWidth(120.0);
        TreeTableColumn<Note, String> projectTreeTableColumn2 = new TreeTableColumn<>("Date");
        projectTreeTableColumn2.setPrefWidth(90.0);
        projectTreeTableColumn2.setMinWidth(90.0);
        TreeTableColumn<Note, String> projectTreeTableColumn3 = new TreeTableColumn<>("User");
        projectTreeTableColumn3.setPrefWidth(120.0);
        projectTreeTableColumn3.setMinWidth(90.0);
        TreeTableColumn<Note, String> projectTreeTableColumn4 = new TreeTableColumn<>("Content");
        projectTreeTableColumn4.setMinWidth(300.0);

        projectTreeTableColumn1.setCellValueFactory(new TreeItemPropertyValueFactory<>("noteTitle"));
        projectTreeTableColumn2.setCellValueFactory(new TreeItemPropertyValueFactory<>("createDate"));
        projectTreeTableColumn3.setCellValueFactory(new TreeItemPropertyValueFactory<>("userName"));
        projectTreeTableColumn4.setCellValueFactory(new TreeItemPropertyValueFactory<>("content"));

        treeView.getColumns().add(projectTreeTableColumn1);
        treeView.getColumns().add(projectTreeTableColumn2);
        treeView.getColumns().add(projectTreeTableColumn3);
        treeView.getColumns().add(projectTreeTableColumn4);
        treeView.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);

        Note projectRootNote = new Note(currentProjectID, currentProjectID, currentProjectName, NoteType.PROJECT_NOTE,
                currentUserID, "", null, currentProjectName, "");
        TreeItem<Note> projectRootItem = new TreeItem<Note>(projectRootNote);
        projectRootItem.setExpanded(true);

        treeView.setRoot(projectRootItem);
        treeView.setShowRoot(showRoot);
    }

    @FXML
    private void handelNewButton(ActionEvent event) throws IOException {
        Session.setWindowMode("new");
        uiHelper.loadWindow("NoteAddView", newNoteButton, "Create a new note");
    }

    @FXML
    private void handelEditButton(ActionEvent event) throws IOException {
        boolean withSelectedItem = false;
        if (selectedTable == 1) {
            Note projectNote = projectTreeTableView.getSelectionModel().getSelectedItem().getValue();
            Session.setSetOpenItem(projectNote);
            withSelectedItem = true;
        } else if (selectedTable == 2) {
            Note sprintNote = sprintTreeTableView.getSelectionModel().getSelectedItem().getValue();
            Session.setSetOpenItem(sprintNote);
            withSelectedItem = true;

        } else if (selectedTable == 3) {
            Note activityNote = activityTreeTableView.getSelectionModel().getSelectedItem().getValue();
            Session.setSetOpenItem(activityNote);
            withSelectedItem = true;

        } else {
            uiHelper.alertDialogGenerator(noteView, "error", "Edit note",
                    "No note was selected.\nPlease select a Note and try again.");
        }

        if (withSelectedItem) {
            Session.setWindowMode("edit");

            uiHelper.loadWindow("NoteAddView", editNoteButton, "Edit note details");
        }
    }
}
