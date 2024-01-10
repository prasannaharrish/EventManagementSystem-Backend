package com.project.eventManagement.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.eventManagement.entity.Event;
import com.project.eventManagement.repository.EventRepository;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public List<Event> getEventByCategory(String category) {
        return eventRepository.findByCategory(category);
    }

}
