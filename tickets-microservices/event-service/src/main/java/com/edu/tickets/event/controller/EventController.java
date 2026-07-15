package com.edu.tickets.event.controller;

import com.edu.tickets.event.model.Event;
import com.edu.tickets.event.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @GetMapping
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @GetMapping("/{id}")
    public Event getEventById(@PathVariable Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento no encontrado"));
    }

    @PutMapping("/{id}/reduce")
    public Event reduceTickets(@PathVariable Long id, @RequestParam int quantity) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento no encontrado"));

        if (event.getAvailableTickets() < quantity) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No hay suficientes boletos disponibles");
        }

        event.setAvailableTickets(event.getAvailableTickets() - quantity);
        return eventRepository.save(event);
    }
}
