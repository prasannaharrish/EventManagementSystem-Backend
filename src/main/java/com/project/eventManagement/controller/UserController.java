package com.project.eventManagement.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.eventManagement.advice.ErrorResponse;
import com.project.eventManagement.dto.EventCreationDTO;
import com.project.eventManagement.entity.Event;
import com.project.eventManagement.entity.User;
import com.project.eventManagement.exception.EventNotFoundException;
import com.project.eventManagement.exception.InvalidCategoryIdException;
import com.project.eventManagement.exception.ParticipationNotValidException;
import com.project.eventManagement.exception.UnAuthorizedAccessException;
import com.project.eventManagement.repository.UserRepository;
import com.project.eventManagement.service.EventService;
import com.project.eventManagement.service.UserService;

import jakarta.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @GetMapping("/user/home")
    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = (User) userService.loadUserByUsername(username);
        return user;

    }

    @PostMapping("/user/events/create")
    public ResponseEntity<Event> createEvent(@RequestBody @Valid EventCreationDTO eventCreationDTO)
            throws InvalidCategoryIdException, IllegalArgumentException, MethodArgumentNotValidException {
        Event event = eventService.createEvent(eventCreationDTO.getTitle(), eventCreationDTO.getLocation(),
                eventCreationDTO.getDescription(), eventCreationDTO.getStartTime(), eventCreationDTO.getEndTime(),
                eventCreationDTO.getCategoryId());

        return new ResponseEntity<>(event, HttpStatus.OK);

    }

    @GetMapping("/user/events/created")
    public ResponseEntity<List<Event>> getCreatedEvents() {
        List<Event> createdEvents = userService.getCreatedEvents();
        return new ResponseEntity<>(createdEvents, HttpStatus.OK);
    }

    @GetMapping("/user/events/{eventId}/participate")
    public ResponseEntity<User> participate(@PathVariable Long eventId) throws ParticipationNotValidException {
        User user = eventService.participateInAnEvent(eventId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/user/events/participating")
    public ResponseEntity<Set<Event>> getParticipatedEvents() {
        Set<Event> participatingEvents = userService.getParticipatedEvents();
        return new ResponseEntity<>(participatingEvents, HttpStatus.OK);
    }

    @GetMapping("/user/events/participants")
    public ResponseEntity<Set<User>> getEventParticipants(@RequestParam(required = true) long eventId)
            throws UnAuthorizedAccessException {
        Set<User> participants = eventService.getEventParticipants(eventId);
        return new ResponseEntity<>(participants, HttpStatus.OK);

    }

    @PutMapping("/user/events/modify/{eventId}")
    public ResponseEntity<Event> modifyEvent(@RequestBody @Valid EventCreationDTO eventCreationDTO,
            @PathVariable Long eventId) {
        Event modifiedEvent = eventService.modifyEvent(eventCreationDTO, eventId);
        return new ResponseEntity<>(modifiedEvent, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/user/events/delete/{eventId}")
    public ResponseEntity<Event> cancelEvent(@PathVariable Long eventId) {
        Event deletedEvent = eventService.cancelEvent(eventId);
        return new ResponseEntity<>(deletedEvent, HttpStatus.OK);
    }

    @GetMapping("/admin/")
    public String hiAdmin() {
        return "admin";
    }

}
