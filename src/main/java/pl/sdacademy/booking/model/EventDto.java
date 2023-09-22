package pl.sdacademy.booking.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@ToString
@EqualsAndHashCode
public class EventDto {

    private Long id;
    private String name;
    private BigDecimal price;
    private LocalDateTime fromTime;
    private LocalDateTime toTime;

}
