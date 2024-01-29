package com.project.eventManagement.dto;

import java.sql.Timestamp;

import com.project.eventManagement.entity.Event;
import com.project.eventManagement.validation.UniqueField;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class EventCreationDTO {

    @NotEmpty(message = "Title cannot be empty")
    @UniqueField(message = "Event Title Alreay Taken. Choose an unique One", fieldName = "title", className = Event.class)
    private String title;

    @NotEmpty(message = "Location cannot be empty")
    private String location;

    @NotEmpty(message = "Description cannot be empty")
    private String description;

    @NotNull(message = "StartTime cannot be empty")
    private Timestamp startTime;

    @NotNull(message = "EndTime cannot be empty")
    private Timestamp endTime;

    @NotNull(message = "Category Id cannot be empty")
    private int categoryId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategory(int categoryId) {
        this.categoryId = categoryId;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

}
