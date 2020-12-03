package com.group8.entity;

public enum ActivityStatus {
    TODO("To do"),
    INPROGRESS("In progress"),
    REVIEW("Review"),
    DONE("Done"); 

    private final String simpleName;

    ActivityStatus(String simpleName) {
        this.simpleName = simpleName;
    }

    @Override
    public String toString() {
        return simpleName;
    }
}
