package com.project.eventManagement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event createEvent(Event event) {
        // logic for creating the events
        return eventRepository.save(event);

    }

    public Event editEvent(Event event) {

        // logic for editing the events
        return eventRepository.save(event);
    }

    public void cancelEvent(Long eventId) {

        // logic for cancelling the events.
        eventRepository.deleteById(eventId);
    }

    public Optional<Event> getEventbyId(Long eventId) {
        // logic
        return eventRepository.findById(eventId);
    }

}
