package pl.sdacademy.booking.service;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import pl.sdacademy.booking.data.EventEntity;
import pl.sdacademy.booking.model.EventDto;
import pl.sdacademy.booking.model.NewEventDto;
import pl.sdacademy.booking.repository.EventRepository;
import pl.sdacademy.booking.repository.ItemRepository;
import pl.sdacademy.booking.validator.NewEventDtoValidator;

import java.util.ArrayList;
import java.util.List;

@Slf4j

public class EventService {

    private final EventRepository eventRepository;
    private ItemRepository itemRepository;

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
//        EventEntity eventEntity = new EventEntity();
//        List<String> errorList = NewEventDtoValidator.validate(newEvent);
//        if(!errorList.isEmpty()){
//            return errorList.toString();
//        }
//        //tutaj bedzie wyszukiwanie id_itemu po jego nazwie - być może można wykorzystać metode repostitory Item findbyName
//        String itemName = newEvent.getItemName();
//        Long itemId = itemRepository.findItemByName(itemName);
//        if (itemId == null) {
//            return "Produkt o nazwie '" + itemName + "' nie istnieje.";
//        }
//
//        eventEntity.setItem(eventEntity.getItem());
//        eventEntity.setFrom(newEvent.getFromTime());
//        eventEntity.setTo(newEvent.getToTime());
//        eventRepository.addEvent(eventEntity);
        return "Sesja została zapisana";
    }


}

