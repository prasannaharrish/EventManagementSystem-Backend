package com.project.eventManagement.dto;

import java.sql.Timestamp;

import jakarta.validation.constraints.NotNull;

public class EventFilterDTO {

    @NotNull(message = "Start date cannot be empty")
    private Timestamp startDate;

    @NotNull(message = "End date canot be empty")
    private Timestamp endDate;

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public EventFilterDTO(Timestamp startDate, Timestamp endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

}
