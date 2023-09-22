package pl.sdacademy.booking.mapper;

import pl.sdacademy.booking.data.EventEntity;
import pl.sdacademy.booking.model.EventDto;

public class EventDtoMapper {
    public static EventDto mapToEventDto(EventEntity entity) {
        if (entity == null) {
            return null;
        }

        return EventDto.builder()
                .id(entity.getId())
                .name(entity.getItem().getName())
                .price(entity.getItem().getPrice())
                .fromTime(entity.getFrom())
                .toTime(entity.getTo())
                .build();
    }
}
