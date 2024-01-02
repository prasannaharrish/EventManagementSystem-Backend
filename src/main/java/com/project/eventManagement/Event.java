package com.project.eventManagement;

import java.time.LocalDateTime;

import org.springframework.cglib.core.Local;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // primary key of the entity;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String eventDate;

    @Column(nullable = false)
    private String eventTime;

    @Column(nullable = false)
    private String location;

    @Column(length = 500)
    private String description;

    public Event() {
    }

    public Event(String title, String eventDate, String eventTime, String location, String description) {
        this.title = title;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.location = location;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return eventDate;
    }

    public void setDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getTime() {
        return eventTime;
    }

    public void setTime(String eventTime) {
        this.eventTime = eventTime;
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

}
