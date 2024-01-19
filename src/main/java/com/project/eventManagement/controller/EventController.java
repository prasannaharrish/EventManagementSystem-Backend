package com.project.eventManagement.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.eventManagement.dto.EventCreationDTO;
import com.project.eventManagement.dto.EventFilterDTO;
import com.project.eventManagement.entity.Event;
import com.project.eventManagement.exception.EventNotFoundException;
import com.project.eventManagement.service.EventService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/events/all")
    public ResponseEntity<List<Event>> getAllEventS() {

        List<Event> events = eventService.getAllEvents();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("/events")
    public ResponseEntity<Event> getEventByEventId(@RequestParam(required = true) long eventId)
            throws EventNotFoundException {
        Event event = eventService.getEventById(eventId);
        return new ResponseEntity<>(event, HttpStatus.OK);

    }

    @GetMapping("events/{filter}")
    public ResponseEntity<List<Event>> getFilteredEvents(@PathVariable String filter) {
        List<Event> events = new ArrayList<>();
        if (filter.equals("past")) {
            events = eventService.getPastEvents();
        } else if (filter.equals("live")) {
            events = eventService.getLiveEvents();
        } else if (filter.equals("upcoming")) {
            events = eventService.getUpcomingEvents();
        } else {
            events = null;
        }
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("/events/category")
    public ResponseEntity<List<Event>> getEventByCategory(@RequestParam(required = true) int categoryId) {
        List<Event> events = eventService.getEventByCategory(categoryId);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @PostMapping("/events/timerange")
    public ResponseEntity<List<Event>> getEventsBetweenTimeRange(@RequestBody EventFilterDTO eventFilterDTO) {
        List<Event> events = eventService.getEventsBetweenTimeRange(eventFilterDTO.getStartDate(),
                eventFilterDTO.getEndDate());
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Event> createEvent(@RequestBody EventCreationDTO eventCreationDTO) {
        Event event = eventService.createEvent(eventCreationDTO.getTitle(), eventCreationDTO.getLocation(),
                eventCreationDTO.getDescription(), eventCreationDTO.getStartTime(), eventCreationDTO.getEndTime(),
                eventCreationDTO.getCategory());

        return new ResponseEntity<>(event, HttpStatus.OK);

    }

}
