package com.project.eventManagement.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.eventManagement.dto.EventCreationDTO;
import com.project.eventManagement.entity.Category;
import com.project.eventManagement.entity.Event;
import com.project.eventManagement.entity.User;
import com.project.eventManagement.exception.EventNotFoundException;
import com.project.eventManagement.exception.InvalidCategoryIdException;
import com.project.eventManagement.exception.ParticipationNotValidException;
import com.project.eventManagement.exception.UnAuthorizedAccessException;
import com.project.eventManagement.repository.CategoryRepository;
import com.project.eventManagement.repository.EventRepository;
import com.project.eventManagement.repository.UserRepository;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    UserRepository userRepository;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public List<Event> getPastEvents() {
        return eventRepository.findByEndTimeBefore(new Date());
    }

    public List<Event> getLiveEvents() {
        return eventRepository.findByStartTimeBeforeAndEndTimeAfter(new Date(), new Date());
    }

    public List<Event> getUpcomingEvents() {
        return eventRepository.findByStartTimeAfter(new Date());
    }

    public List<Event> getEventByCategory(@RequestParam(required = true) int categoryId)
            throws EventNotFoundException {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new EventNotFoundException("Event not found with the invalid category id :" + categoryId));
        List<Event> events = eventRepository.findByCategory(category).orElse(null);
        return events;

    }

    public Event getEventById(@RequestParam(required = true) Long eventId) throws EventNotFoundException {
        Event event = eventRepository.findByEventId(eventId);
        if (event != null) {
            return event;
        } else
            throw new EventNotFoundException("Event not found with the id:" + eventId);
    }

    public List<Event> getEventsBetweenTimeRange(Timestamp startTime, Timestamp endTime) {
        List<Event> events = eventRepository.findByStartTimeBetween(startTime, endTime);
        events.addAll(eventRepository.findByEndTimeBetween(startTime, endTime));
        events.addAll(eventRepository.findByEndTimeAfter(endTime));
        HashSet<Event> uniqueEventsSet = new HashSet<>(events);
        List<Event> eventsWithoutDuplicates = new ArrayList<>(uniqueEventsSet);
        return eventsWithoutDuplicates;

    }

    public Event createEvent(String title, String location, String description, Timestamp startTime, Timestamp endTime,
            int categoryId)
            throws InvalidCategoryIdException, IllegalArgumentException, MethodArgumentNotValidException {

        User currentUser = getCurrentUser();
        validateDates(startTime, endTime);

        Set<User> participants = new HashSet<>();
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new InvalidCategoryIdException("Invalid category id:"));
        return eventRepository
                .saveAndFlush(new Event(title, location, description, startTime, endTime, currentUser, participants,
                        category));
    }

    public User participateInAnEvent(Long eventId) throws ParticipationNotValidException {
        Event event = eventRepository.findByEventId(eventId);
        Timestamp eventStartTime = event.getStartTime();
        Instant currentInstant = Instant.now();

        if (eventStartTime.toInstant().isAfter(currentInstant)) {
            User participant = getCurrentUser();
            participant.getParticipatingEvents().add(event);
            userRepository.saveAndFlush(participant);
            return participant;

        } else {
            throw new ParticipationNotValidException("Cannot participate in the Event; it has already happened");
        }
    }

    public Set<User> getEventParticipants(Long eventId)
            throws UnAuthorizedAccessException {
        Event event = eventRepository.findByEventId(eventId);
        User currentUser = getCurrentUser();
        if (event.getCreator().equals(currentUser) || currentUser.getAuthority().equals("ADMIN")) {
            return event.getParticipants();
        } else {
            throw new UnAuthorizedAccessException("You don't have access to view paticipants of this event");
        }

    }

    public Event modifyEvent(EventCreationDTO eventCreationDTO, Long eventId) {
        Event eventTomodify = eventRepository.findByEventId(eventId);
        if (eventTomodify == null) {
            throw new EventNotFoundException("Event not found with the event id:" + eventId);
        }
        Instant currentInstant = Instant.now();
        if (eventTomodify.getStartTime().toInstant().isBefore(currentInstant)) {
            throw new IllegalArgumentException("You cannot modify a event that is already happened.");
        } else {
            User currentUser = getCurrentUser();
            if (currentUser.equals(eventTomodify.getCreator()) || currentUser.getAuthority().equals("ADMIN")) {
                eventTomodify.setTitle(eventCreationDTO.getTitle());
                eventTomodify.setDescription(eventCreationDTO.getDescription());
                eventTomodify.setLocation(eventCreationDTO.getLocation());

                Category category = categoryRepository.findById(eventCreationDTO.getCategoryId())
                        .orElseThrow(() -> new InvalidCategoryIdException("Invalid category id:"));

                eventTomodify.setCategory(category);
                eventTomodify.setUpdatedAt(new Date());
                validateDates(eventCreationDTO.getStartTime(), eventCreationDTO.getEndTime());
                eventTomodify.setStartTime(eventCreationDTO.getStartTime());
                eventTomodify.setEndTime(eventCreationDTO.getEndTime());
                return eventRepository.saveAndFlush(eventTomodify);
            } else {
                throw new UnAuthorizedAccessException("You don't have access to modify this event");
            }
        }

    }

    public Event cancelEvent(Long eventId) {
        Event eventTocancel = eventRepository.findByEventId(eventId);
        if (eventTocancel == null) {
            throw new EventNotFoundException("Event not found with the event id:" + eventId);
        }
        User currentUser = getCurrentUser();
        if (currentUser.equals(eventTocancel.getCreator()) || currentUser.getAuthority().equals("ADMIN")) {

            for (User participant : eventTocancel.getParticipants()) {
                participant.getParticipatingEvents().remove(eventTocancel);
            }
            eventTocancel.getParticipants().clear();
            eventRepository.saveAndFlush(eventTocancel);
            eventRepository.delete(eventTocancel);
            return eventTocancel;
        } else {
            throw new UnAuthorizedAccessException("You don't have access to delete this event");
        }

    }

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepository.findByUsername(username).get();
        return user;
    }

    private void validateDates(Timestamp startTime, Timestamp endTime) {

        Instant currentInstant = Instant.now();

        if (startTime.toInstant().isBefore(currentInstant) || endTime.toInstant().isBefore(currentInstant)) {
            throw new IllegalArgumentException("Start time and End time must be greater than Current Time");

        }

        if (endTime.toInstant().isBefore(startTime.toInstant()) || endTime.toInstant().equals(startTime.toInstant())) {
            throw new IllegalArgumentException("End time must be greater than Start Time");

        }

    }

}
