package pl.sdacademy.booking.service;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import pl.sdacademy.booking.data.EventEntity;
import pl.sdacademy.booking.data.ItemEntity;
import pl.sdacademy.booking.model.EventDto;
import pl.sdacademy.booking.model.NewEventDto;
import pl.sdacademy.booking.repository.EventRepository;
import pl.sdacademy.booking.repository.ItemRepository;
import pl.sdacademy.booking.validator.NewEventDtoValidator;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Builder
public class EventService {

    private final EventRepository eventRepository;
    private final ItemRepository itemRepository;

    public EventService(EventRepository eventRepository, ItemRepository itemRepository) {
        this.eventRepository = eventRepository;
        this.itemRepository = itemRepository;
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

//        Long eventsByName = eventRepository.findEventsByDate(newEvent.getFromTime());
//        if (eventsByName != null) {
//            return "Sesja już istnieje.";
//        }

        List<String> validate = NewEventDtoValidator.validate(newEvent);

        if(validate.size() !=0) {
            String message = String.join(", ",validate);
            return message;
        }

        EventEntity eventEntity = new EventEntity();
        Long itemByName = itemRepository.findItemByName(newEvent.getItemName());

        // najlepiej ta walidacje przeniesc do osobnej klasy dolozyc do walidatora, ale wtedy *Validator musialby nie byc statyczny
        if( itemByName == null || itemByName == -1){
            return "Nie znaleziono takiego obiektu";
        }

        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setId(itemByName);

        eventEntity.setItem(itemEntity); // przekazujemy primary key
        eventEntity.setFrom(newEvent.getFromTime());
        eventEntity.setTo(newEvent.getToTime());
        eventRepository.addEvent(eventEntity);
        return "Sesja została zapisana";
    }


}

