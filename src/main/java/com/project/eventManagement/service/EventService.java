package com.project.eventManagement.service;

import java.util.List;
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

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public List<Event> getEventByCategory(String category) {
        return eventRepository.findByCategory(category);
    }

    public Event createEvent(String title, String location, String description, String date, String time,
            String category) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();

        User user = userRepository.findByUsername(username).get();

        return eventRepository.save(new Event(title, location, description, date, time, user, category));

    }

}
