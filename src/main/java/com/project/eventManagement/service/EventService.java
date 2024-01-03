// package com.project.eventManagement.service;

// import java.util.List;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import com.project.eventManagement.entity.Event;
// import com.project.eventManagement.repository.EventRepository;

// @Service
// public class EventService {

// @Autowired
// private EventRepository eventRepository;

// public List<Event> getAllEvents() {
// return eventRepository.findAll();
// }

// public Event createEvent(Event event) {

// // if (event.getDate() == null) {
// // event.setDate(LocalDate.now());
// // }
// // if (event.getTime() == null) {
// // event.setTime(LocalTime.now().toString());
// // }

// if (event.getTitle() == null || event.getTitle().trim().isEmpty()) {
// throw new IllegalArgumentException("Event title cannot be empty");

// }
// return eventRepository.save(event);

// }

// public Event editEvent(Long id, Event updatedEvent) {
// Event existingEvent = eventRepository.findById(id).orElse(null);

// if (existingEvent != null) {
// existingEvent.setTitle(updatedEvent.getTitle());
// existingEvent.setLocation(updatedEvent.getLocation());
// existingEvent.setDescription(updatedEvent.getDescription());
// existingEvent.setDate(updatedEvent.getDate());
// existingEvent.setTime(updatedEvent.getTime());

// return eventRepository.save(existingEvent);

// } else {
// return null;
// }

// }

// public boolean cancelEvent(Long id) {
// Event eventToCancel = eventRepository.findById(id).orElse(null);

// if (eventToCancel != null) {

// eventRepository.delete(eventToCancel);

// return true;
// } else {
// return false;
// }

// }

// }
