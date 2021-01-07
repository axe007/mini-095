package com.group8.controllers;

import java.util.ArrayList;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

import com.group8.model.Note;
import com.group8.model.NoteType;

import org.bson.types.ObjectId;

public class NoteController {
    private static DatabaseController mongoDb = new DatabaseController();
    public static ArrayList<Note> allNotesOfCurrentProject;

    public void createNote(Note note) {

        mongoDb.getNoteCollection().insertOne(note);

    }

    public void modifyNote(Note note) {
        ObjectId noteId = note.getId();

        mongoDb.getNoteCollection().updateOne(eq("_id", noteId),
                combine(set("projectID", note.getProjectID()), set("targetID", note.getTargetID()),
                        set("targetName", note.getTargetName()), set("type", note.getType()),
                        set("userID", note.getUserID()), set("userName", note.getUserName()),
                        set("noteTitle", note.getNoteTitle()), set("content", note.getContent())));

    }

    public ArrayList<Note> getNoteListForCurrentProject(ObjectId projectId) {
        allNotesOfCurrentProject = mongoDb.getNoteCollection().find(eq("projectID", projectId))
                .into(new ArrayList<Note>());
        return allNotesOfCurrentProject;
    }

    public ArrayList<Note> getProjectNoteList() {
        ArrayList<Note> noteList = new ArrayList<>();
        for (Note note : allNotesOfCurrentProject) {
            if (note.getType().equals(NoteType.PROJECT_NOTE)) {
                noteList.add(note);
            }
        }
        return noteList;
    }

    public ArrayList<Note> getSprintNoteList() {
        ArrayList<Note> noteList = new ArrayList<>();
        for (Note note : allNotesOfCurrentProject) {
            if (note.getType().equals(NoteType.SPRINT_NOTE)) {
                noteList.add(note);
            }
        }
        return noteList;
    }

    public ArrayList<Note> getActitivyNoteList() {
        ArrayList<Note> noteList = new ArrayList<>();
        for (Note note : allNotesOfCurrentProject) {
            if (note.getType().equals(NoteType.ACTIVITY_NOTE)) {
                noteList.add(note);
            }
        }
        return noteList;
    }

}
