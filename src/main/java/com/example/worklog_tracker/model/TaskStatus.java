package com.example.worklog_tracker.model;

// this enum is used to represent the status of a task
public enum TaskStatus {
    TODO("Belum dimulai"),
    IN_PROGRESS("Sedang dikerjakan"),
    DONE("Selesai"),
    BLOCKED("Terkendala");

    // this field holds the human-readable display name of the status
    private final String displayName;

    // constructor to initialize the display name
    TaskStatus(String displayName) {
        this.displayName = displayName; // initialize the display name
    }

    // getter for the display name
    public String getDisplayName() {
        return displayName; // return the display name
    }
}
