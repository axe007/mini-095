package com.group8.controllers.viewcontroller;

import com.group8.model.Note;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableView;

public class NoteViewController {
    @FXML
    private Button newNoteButton;
    @FXML
    private Button editNoteButton;
    @FXML
    private TableView<Note> activityNoteTableView;
    @FXML
    private TableView<Note> projectNoteTableView;
    @FXML
    private TreeTableView<Note> sprintNoteTreeTableView;

}
