package pl.sdacademy.booking.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import pl.sdacademy.booking.data.ItemEntity;

import java.time.LocalDateTime;
@Builder
@Getter
@ToString
@EqualsAndHashCode

public class EventDto {
    public class NewEventDto {
        private ItemEntity item;

        private LocalDateTime from;

        private LocalDateTime to;
}}
