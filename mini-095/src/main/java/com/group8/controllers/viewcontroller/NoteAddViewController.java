package com.group8.controllers.viewcontroller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.group8.model.Note;
import com.group8.model.NoteType;
import com.group8.model.Session;

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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

public class NoteAddViewController implements Initializable {
    private ObjectId currentUserID = Session.getSessionUserId();
    private ObjectId currentProjectID = Session.getOpenProjectId();
    private String currentProjectName = Session.getOpenProjectName();

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        if (Session.getWindowMode().equals("new")) {
            windowTitle.setText("Enter new note details:");

        } else if (Session.getWindowMode().equals("edit")) {
            windowTitle.setText("Edit note details:");
            Note editNote = (Note) Session.getOpenItem();
            noteNameTextField.setText(editNote.getNoteTitle());
            noteDescription.setText(editNote.getContent());

        }

    }

    public void setUpTypeComboBox(String type) {
        noteTypeComboBox.getItems().addAll(NoteType.PROJECT_NOTE, NoteType.SPRINT_NOTE, NoteType.ACTIVITY_NOTE);
        if (type == null || type.equals(NoteType.PROJECT_NOTE)) {
            noteTypeComboBox.setValue(NoteType.PROJECT_NOTE);
            topicComboBox.getItems().add(currentProjectName);

            topicComboBox.setValue(currentProjectName);
        } else {
            noteTypeComboBox.setValue(type);
            if (type.equals(NoteType.SPRINT_NOTE)) {
                ArrayList<String> sprintNameList = new ArrayList<>();
                
                topicComboBox.getItems().addAll(NoteViewController.sprintList.iterator())

            } else if(type.equals(NoteType.ACTIVITY_NOTE)) {
                
            }

        }
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
                                    if (item.contains("User story")) {
                                        setTextFill(Color.GREEN);
                                    } else if (item.contains("Task")) {
                                        setTextFill(Color.BLUE);
                                    } else if (item.contains("Bug")) {
                                        setTextFill(Color.RED);
                                    }
                                } else {
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                });

    }

    @FXML
    public void handleSaveButton(ActionEvent event) {

        NoteViewController.isUpdated.setValue(true);
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
