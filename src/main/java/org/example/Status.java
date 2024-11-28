package org.example;

public enum Status {
    PENDING("PENDING"),
    IN_PROGRESS("IN_PROGRESS"),
    COMPLETED("COMPLETED"),
    ERROR("ERROR");

    private final String taskStatus;

    Status(String status) {
        this.taskStatus = status;
    }

    public String getTaskStatus() {
        return taskStatus;
    }
}
