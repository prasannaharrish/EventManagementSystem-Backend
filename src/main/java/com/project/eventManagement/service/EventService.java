package com.project.eventManagement.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.project.eventManagement.dto.EventCreationDTO;
import com.project.eventManagement.entity.Event;
import com.project.eventManagement.entity.User;
import com.project.eventManagement.repository.EventRepository;
import com.project.eventManagement.repository.UserRepository;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public List<Event> getEventByCategory(String category) {
        return eventRepository.findByCategory(category);
    }

    public Event createEvent(String title, String location, String description, String date, String time,
            String category) {

        // Authentication authentication =
        // SecurityContextHolder.getContext().getAuthentication();
        // UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        // String username = userDetails.getUsername();
        // User user = userRepository.findByUsername(username).get();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User creator = (User) userService.loadUserByUsername(username);

        Set<User> participants = new HashSet<>();

        return eventRepository
                .save(new Event(title, location, description, date, time, creator, participants, category));

    }

}
