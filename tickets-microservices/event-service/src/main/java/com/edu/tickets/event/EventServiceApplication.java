package com.edu.tickets.event;

import com.edu.tickets.event.model.Event;
import com.edu.tickets.event.repository.EventRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EventServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(EventRepository eventRepository) {
        return args -> {
            eventRepository.save(new Event(
                    "Concierto de Rock: Los Planetas",
                    "Disfruta de la mejor banda de rock alternativo en vivo. Un espectáculo único con sus grandes éxitos.",
                    "2026-08-15 21:00",
                    "Estadio Nacional",
                    120.00,
                    150
            ));
            eventRepository.save(new Event(
                    "Obra de Teatro: Hamlet",
                    "La clásica tragedia de Shakespeare adaptada por un elenco internacional de primer nivel.",
                    "2026-08-20 19:30",
                    "Teatro Municipal",
                    80.00,
                    80
            ));
            eventRepository.save(new Event(
                    "Show de Stand-Up Comedy: Risas de Noche",
                    "Una noche llena de carcajadas con los mejores comediantes del país en un formato íntimo.",
                    "2026-08-22 22:00",
                    "Club de Comedia Central",
                    45.00,
                    50
            ));
            eventRepository.save(new Event(
                    "Ópera: La Traviata",
                    "La majestuosa ópera de Giuseppe Verdi interpretada por la orquesta filarmónica.",
                    "2026-09-05 18:00",
                    "Gran Teatro de la Ópera",
                    250.00,
                    100
            ));
        };
    }
}
