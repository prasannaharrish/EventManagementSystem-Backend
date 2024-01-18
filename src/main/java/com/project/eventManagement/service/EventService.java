package com.project.eventManagement.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.project.eventManagement.entity.Category;
import com.project.eventManagement.entity.Event;
import com.project.eventManagement.entity.User;
import com.project.eventManagement.repository.EventRepository;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserService userService;

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

    public List<Event> getEventByCategory(String category) {
        return eventRepository.findByCategoryCategory(category);
    }

    public Event createEvent(String title, String location, String description, Timestamp startTime, Timestamp endTime,
            Category category) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User creator = (User) userService.loadUserByUsername(username);

        Set<User> participants = new HashSet<>();

        return eventRepository
                .save(new Event(title, location, description, startTime, endTime, creator, participants, category));

    }

}
