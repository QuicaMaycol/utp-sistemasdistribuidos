package com.edu.tickets.booking.controller;

import com.edu.tickets.booking.client.EventClient;
import com.edu.tickets.booking.dto.EventDto;
import com.edu.tickets.booking.model.Booking;
import com.edu.tickets.booking.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private EventClient eventClient;

    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @PostMapping
    public Booking createBooking(@RequestBody BookingRequest request) {
        // 1. Obtener los detalles del evento del microservicio de eventos usando Feign
        EventDto event;
        try {
            event = eventClient.getEventById(request.getEventId());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El espectáculo seleccionado no existe.");
        }

        // 2. Validar stock de boletos
        if (event.getAvailableTickets() < request.getQuantity()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No hay suficientes boletos disponibles.");
        }

        // 3. Llamar al microservicio de eventos para reducir el stock
        try {
            eventClient.reduceTickets(request.getEventId(), request.getQuantity());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error al procesar la compra en el servicio de eventos: " + e.getMessage());
        }

        // 4. Calcular total y guardar la compra
        double totalAmount = event.getPrice() * request.getQuantity();
        String formattedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Booking booking = new Booking(
                request.getEventId(),
                event.getTitle(),
                request.getCustomerName(),
                request.getQuantity(),
                totalAmount,
                formattedDate
        );

        return bookingRepository.save(booking);
    }

    // Clase DTO para la petición de compra
    public static class BookingRequest {
        private Long eventId;
        private String customerName;
        private int quantity;

        public Long getEventId() { return eventId; }
        public void setEventId(Long eventId) { this.eventId = eventId; }

        public String getCustomerName() { return customerName; }
        public void setCustomerName(String customerName) { this.customerName = customerName; }

        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
    }
}
