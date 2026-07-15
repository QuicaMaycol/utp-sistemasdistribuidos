package com.edu.tickets.booking.client;

import com.edu.tickets.booking.dto.EventDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "event-service")
public interface EventClient {

    @GetMapping("/api/events/{id}")
    EventDto getEventById(@PathVariable("id") Long id);

    @PutMapping("/api/events/{id}/reduce")
    EventDto reduceTickets(@PathVariable("id") Long id, @RequestParam("quantity") int quantity);
}
