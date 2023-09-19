package pl.sdacademy.booking.model;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Data
@Builder
public class EventDto {

    private Long id;
    private String itemName;
    private BigDecimal itemPrice;
    private LocalDateTime fromTime;
    private LocalDateTime toTime;

}
