package pl.sdacademy.booking.repository;

import pl.sdacademy.booking.data.EventEntity;
import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository {

    List<EventEntity> findEvents();

    void addEvent(EventEntity event);

    Long findEventsByDate(LocalDateTime date);

}
