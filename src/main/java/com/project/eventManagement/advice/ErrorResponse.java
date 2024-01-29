package com.project.eventManagement.advice;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private ArrayList<String> messages;

    public ErrorResponse(int status, String error, ArrayList<String> messages) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.messages = messages;
    }

    public ErrorResponse(String message) {
        this.timestamp = LocalDateTime.now();

    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ArrayList<String> getMessages() {
        return messages;
    }

    public void setMessage(ArrayList<String> messages) {
        this.messages = messages;
    }

}
