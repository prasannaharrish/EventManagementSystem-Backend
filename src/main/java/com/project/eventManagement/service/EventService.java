package com.project.eventManagement.service;

import java.sql.Timestamp;
import java.time.Instant;
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

import com.project.eventManagement.entity.Category;
import com.project.eventManagement.entity.Event;
import com.project.eventManagement.entity.User;
import com.project.eventManagement.exception.EventNotFoundException;
import com.project.eventManagement.exception.InvalidCategoryIdException;
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
        return eventRepository.findByStartTimeBetween(startTime, endTime);

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

    private User getCurrentUser() {
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
