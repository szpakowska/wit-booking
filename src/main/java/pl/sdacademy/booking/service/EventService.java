package pl.sdacademy.booking.service;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import pl.sdacademy.booking.data.EventEntity;
import pl.sdacademy.booking.model.EventDto;
import pl.sdacademy.booking.model.NewEventDto;
import pl.sdacademy.booking.repository.EventRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Builder
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<EventDto> findEvents() {
        log.info("findEvents");
        List<EventDto> result = new ArrayList<>();
        List<EventEntity> eventEntities = eventRepository.findEvents();

        for (EventEntity entity : eventEntities) {
            result.add(EventDto.builder()
                    .itemName(entity.getItem().getName())
                    .itemPrice(entity.getItem().getPrice())
                    .fromTime(entity.getFrom())
                    .toTime(entity.getTo())
                    .build());
        }
        return result;
    }

    public String addEvent(NewEventDto newEvent) {
        Long eventsByName = eventRepository.findEventsByDate(newEvent.getFromTime());
        if (eventsByName != null) {
            return "Sesja już istnieje.";
        }
        EventEntity eventEntity = new EventEntity();

        //tutaj bedzie wyszukiwanie id_itemu po jego nazwie - być może można wykorzystać metode repostitory Item findbyName
        //eventEntity.setItem(itemId)
        eventEntity.setFrom(newEvent.getFromTime());
        eventEntity.setTo(newEvent.getToTime());
        eventRepository.addEvent(eventEntity);
        return "Sesja została zapisana";
    }


}

