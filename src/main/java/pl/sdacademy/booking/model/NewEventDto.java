package pl.sdacademy.booking.model;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class NewEventDto {

    private String itemName;
    private LocalDateTime fromTime;
    private LocalDateTime toTime;

}
