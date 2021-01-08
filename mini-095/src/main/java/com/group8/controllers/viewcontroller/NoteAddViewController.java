package com.group8.controllers.viewcontroller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.group8.controllers.NoteController;
import com.group8.controllers.UserController;
import com.group8.helper.UIHelper;
import com.group8.model.Activity;
import com.group8.model.Note;
import com.group8.model.NoteType;
import com.group8.model.Session;
import com.group8.model.Sprint;

import org.bson.types.ObjectId;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

public class NoteAddViewController implements Initializable {
    private NoteController noteController = new NoteController();
    private final ObjectId currentUserID = Session.getSessionUserId();

    private final ObjectId currentProjectID = Session.getOpenProjectId();
    private final String currentProjectName = Session.getOpenProjectName();
    private static UIHelper uiHelper = new UIHelper();

    @FXML
    private ComboBox<String> noteTypeComboBox;
    @FXML
    private ComboBox<String> topicComboBox;
    @FXML
    private Label windowTitle;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField noteNameTextField;
    @FXML
    private TextArea noteDescription;
    @FXML
    private StackPane dialogPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (Session.getWindowMode().equals("new")) {
            windowTitle.setText("Enter new note details:");
            setUpTypeComboBox(NoteType.PROJECT_NOTE);
            setupTopicCombox(NoteType.PROJECT_NOTE);

        } else if (Session.getWindowMode().equals("edit")) {
            windowTitle.setText("Edit note details:");
            Note editNote = (Note) Session.getOpenItem();
            noteNameTextField.setText(editNote.getNoteTitle());
            noteDescription.setText(editNote.getContent());
            setUpTypeComboBox(editNote.getType());
            setupTopicCombox(editNote.getType());
            topicComboBox.setValue(editNote.getTargetName());

        }

    }

    public void setUpTypeComboBox(String type) {
        noteTypeComboBox.getItems().addAll(NoteType.PROJECT_NOTE, NoteType.SPRINT_NOTE, NoteType.ACTIVITY_NOTE);
        if (type == null || type.equals(NoteType.PROJECT_NOTE)) {
            noteTypeComboBox.setValue(NoteType.PROJECT_NOTE);
        } else {
            noteTypeComboBox.setValue(type);

        }
        setupTopicCombox(type);
        noteTypeComboBox.setCellFactory(
                (Callback<ListView<String>, ListCell<String>>) new Callback<ListView<String>, ListCell<String>>() {
                    @Override
                    public ListCell<String> call(ListView<String> param) {
                        final ListCell<String> cell = new ListCell<String>() {
                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (item != null) {
                                    setText(item);
                                    if (item.equals(NoteType.PROJECT_NOTE)) {

                                        setTextFill(Color.BLUE);
                                    } else if (item.equals(NoteType.SPRINT_NOTE)) {

                                        setTextFill(Color.PURPLE);
                                    } else if (item.equals(NoteType.ACTIVITY_NOTE)) {

                                        setTextFill(Color.GREEN);
                                    }
                                    setupTopicCombox(item);
                                } else {
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                });

    }

    public void setupTopicCombox(String type) {
        topicComboBox.getItems().clear();
        if (type.equals(NoteType.SPRINT_NOTE)) {
            ArrayList<String> sprintNameList = new ArrayList<>();
            for (Sprint sprint : NoteViewController.sprintList) {
                sprintNameList.add(sprint.getName());
            }

            topicComboBox.getItems().addAll(sprintNameList);

        } else if (type.equals(NoteType.ACTIVITY_NOTE)) {
            ArrayList<String> activityNameList = new ArrayList<>();
            for (Activity activity : NoteViewController.activityList) {
                activityNameList.add(activity.getName());
            }

            topicComboBox.getItems().addAll(activityNameList);
        } else if (type.equals(NoteType.PROJECT_NOTE)) {
            topicComboBox.getItems().add(currentProjectName);
        }
    }

    @FXML
    public void handleSaveButton(ActionEvent event) {
        UserController userController = new UserController();
        String currentUserName = userController.getUserDetail(currentUserID, "fullname");

        if (validInput()) {
            boolean isduplicated = false;
            switch (noteTypeComboBox.getValue()) {
                case NoteType.PROJECT_NOTE:
                    for (Note note : NoteViewController.projectNoteList) {
                        if (note.getNoteTitle().equalsIgnoreCase(noteNameTextField.getText())) {
                            isduplicated = true;
                            break;
                        }
                    }

                    break;
                case NoteType.ACTIVITY_NOTE:
                    for (Note note : NoteViewController.activityNoteList) {
                        if (note.getNoteTitle().equalsIgnoreCase(noteNameTextField.getText())) {
                            isduplicated = true;
                            break;
                        }
                    }

                    break;
                case NoteType.SPRINT_NOTE:
                    for (Note note : NoteViewController.sprintNoteList) {
                        if (note.getNoteTitle().equalsIgnoreCase(noteNameTextField.getText())) {
                            isduplicated = true;
                            break;
                        }
                    }

                    break;

            }
            if (isduplicated) {
                uiHelper.alertDialogGenerator(dialogPane, "error", "Duplicated Note",
                        "There is a note with same title existing.\nPlease check and try again.");

            } else {
                if (Session.getWindowMode().equals("new")) {
                    boolean findMatchID = false;
                    ObjectId matchID = null;
                    switch (noteTypeComboBox.getValue()) {
                        case NoteType.PROJECT_NOTE:
                            matchID = currentProjectID;
                            findMatchID = true;
                            break;
                        case NoteType.ACTIVITY_NOTE:
                            for (Activity activity : NoteViewController.activityList) {
                                if (activity.getName().equals(topicComboBox.getValue())) {
                                    matchID = activity.getId();
                                    findMatchID = true;
                                    break;
                                }
                            }
                            break;
                        case NoteType.SPRINT_NOTE:
                            for (Sprint sprint : NoteViewController.sprintList) {
                                if (sprint.getName().equals(topicComboBox.getValue())) {
                                    matchID = sprint.getId();
                                    findMatchID = true;
                                    break;
                                }
                            }
                            break;
                    }
                    if (findMatchID && matchID != null) {
                        Note newNote = new Note(currentProjectID, currentProjectID, topicComboBox.getValue(),
                                noteTypeComboBox.getValue(), currentUserID, currentUserName, LocalDate.now(),
                                noteNameTextField.getText(), noteDescription.getText());
                        noteController.createNote(newNote);

                    } else if (!findMatchID) {
                        uiHelper.alertDialogGenerator(dialogPane, "error", "Can't find related topic",
                                "Can't find related topic.\nPlease check and try again.");
                        return;
                    } else {
                        uiHelper.alertDialogGenerator(dialogPane, "error", "System mistake",
                                "Something is wrong with the system.\nPlease check and try again.");
                        return;
                    }

                } else if (Session.getWindowMode().equals("edit")) {
                    Note editNote = (Note) Session.getOpenItem();
                    editNote.setContent(noteDescription.getText());
                    editNote.setNoteTitle(noteNameTextField.getText());
                    editNote.setType(noteTypeComboBox.getValue());
                    editNote.setTargetName(topicComboBox.getValue());
                    boolean findMatchID = false;
                    ObjectId matchID = null;
                    switch (noteTypeComboBox.getValue()) {
                        case NoteType.PROJECT_NOTE:
                            matchID = currentProjectID;
                            findMatchID = true;
                            break;
                        case NoteType.ACTIVITY_NOTE:
                            for (Activity activity : NoteViewController.activityList) {
                                if (activity.getName().equals(editNote.getTargetName())) {
                                    matchID = activity.getId();
                                    findMatchID = true;
                                    break;
                                }
                            }
                            break;
                        case NoteType.SPRINT_NOTE:
                            for (Sprint sprint : NoteViewController.sprintList) {
                                if (sprint.getName().equals(editNote.getTargetName())) {
                                    matchID = sprint.getId();
                                    findMatchID = true;
                                    break;
                                }
                            }
                            break;
                    }
                    if (findMatchID && matchID != null) {
                        editNote.setTargetID(matchID);
                        noteController.modifyNote(editNote);
                    } else if (!findMatchID) {
                        uiHelper.alertDialogGenerator(dialogPane, "error", "Can't find related topic",
                                "Can't find related topic.\nPlease check and try again.");
                        return;
                    } else {
                        uiHelper.alertDialogGenerator(dialogPane, "error", "System mistake",
                                "Something is wrong with the system.\nPlease check and try again.");
                        return;
                    }

                }
                NoteViewController.isUpdated.setValue(true);
                Stage stage;
                stage = (Stage) cancelButton.getScene().getWindow();
                stage.close();

            }

        }

    }

    private boolean validInput() {
        if (noteNameTextField.getText().isEmpty()) {
            uiHelper.alertDialogGenerator(dialogPane, "error", "Create new note",
                    "Note name is missing.\nPlease check the field and try again.");
            noteNameTextField.getStyleClass().add("textfield-error-highlight");
            return false;
        } else if (noteDescription.getText().isEmpty()) {
            uiHelper.alertDialogGenerator(dialogPane, "error", "Create new note",
                    "Note description is missing.\nPlease check the field and try again.");
            noteDescription.getStyleClass().add("textfield-error-highlight");
            return false;

        } else if (topicComboBox.getValue() == null) {
            uiHelper.alertDialogGenerator(dialogPane, "error", "Create new note",
                    "Related topic is missing.\nPlease choose one and try again.");
            return false;

        } else {

            return true;
        }
    }

    @FXML
    public void handleCancelButton(ActionEvent event) {
        Stage stage;
        stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onNoteTypeChange(ActionEvent event) {

    }

    @FXML
    public void onTopicChange(ActionEvent event) {

    }

}
