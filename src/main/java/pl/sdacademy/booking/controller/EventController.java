package pl.sdacademy.booking.controller;

import pl.sdacademy.booking.repository.EventRepositoryImpl;
import pl.sdacademy.booking.repository.ItemRepositoryImpl;
import pl.sdacademy.booking.service.EventService;

public class EventController {

    private final EventService eventService;

    public EventController() {
        // nienajlepsze rozwiazanie - skrot by moc zaprezentowac pseudo controller
        this.eventService = new EventService(new EventRepositoryImpl(), new ItemRepositoryImpl());
    }

    public void presentEventSchedule() {
        System.out.println("\n ----- TERMINARZ SESJI ----- ");

        eventService.findEvents().forEach(System.out::println);

        System.out.println(" ----- KONIEC TERMINARZA SESJI ----- ");
    }

}

