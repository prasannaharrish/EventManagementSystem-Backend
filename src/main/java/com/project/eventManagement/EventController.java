package com.project.eventManagement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/all")
    public String showEvents(Model model) {
        List<Event> events = eventService.getAllEvents();

        model.addAttribute("events", events);
        return "events";
    }

    @PostMapping("/add")
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        Event createdEvent = eventService.createEvent(event);
        return ResponseEntity.ok(createdEvent);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Event> editEvent(@PathVariable Long id, @RequestBody Event updatedEvent) {
        Event editedEvent = eventService.editEvent(id, updatedEvent);

        if (editedEvent != null) {
            return ResponseEntity.ok(editedEvent);

        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<String> cancelEvent(@PathVariable Long id) {
        boolean canceled = eventService.cancelEvent(id);

        if (canceled) {
            return ResponseEntity.ok("Event canceled!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
