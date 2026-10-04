package com.example.worklog_tracker.model;

// Enum representing the priority of a task
public enum TaskPriority{
    LOW("Rednah"),
    MEDIUM("Sedang"),
    HIGH("Tinggi"),
    URGENT("Sangat Penting");

    // The display name of the priority
    private final String displayName;

    // Constructor for the priority enum
    TaskPriority(String displayName) {
        this.displayName = displayName; // Initializes the display name
    }

    // Returns the display name of the priority
    public String getDisplayName() {
        return displayName;
    }
}
