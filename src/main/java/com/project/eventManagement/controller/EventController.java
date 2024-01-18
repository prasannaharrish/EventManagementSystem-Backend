package com.project.eventManagement.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.eventManagement.dto.EventCreationDTO;
import com.project.eventManagement.entity.Category;
import com.project.eventManagement.entity.Event;
import com.project.eventManagement.service.EventService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/all")
    public ResponseEntity<List<Event>> getAllEventS() {

        List<Event> events = eventService.getAllEvents();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("/past")
    public ResponseEntity<List<Event>> getPastEvents() {
        List<Event> events = eventService.getPastEvents();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("/live")
    public ResponseEntity<List<Event>> getLiveEvents() {
        List<Event> events = eventService.getLiveEvents();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<Event>> getUpcomingEvents() {
        List<Event> events = eventService.getUpcomingEvents();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("/{category}")
    public ResponseEntity<List<Event>> getEventByCategory(@PathVariable String category) {
        List<Event> events = eventService.getEventByCategory(category);
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
