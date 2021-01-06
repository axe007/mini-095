package com.group8.model;

public enum NoteType {
    ProjectNote("Project Note"), SprintNote("Sprint Note"), ActivityNote("Activity Note");

    public final String label;

    private NoteType(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return this.label;
    }
}
