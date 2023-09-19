package pl.sdacademy.booking.model;

import lombok.*;

import java.time.LocalDateTime;
@Data
@Builder
public class NewEventDto {
    private String itemName;
    private LocalDateTime fromTime;
    private LocalDateTime toTime;
}
