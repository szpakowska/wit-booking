package pl.sdacademy.booking.mapper;

import org.junit.jupiter.api.Test;
import pl.sdacademy.booking.data.EventEntity;
import pl.sdacademy.booking.data.ItemEntity;
import pl.sdacademy.booking.model.EventDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

public class EventDtoMapperTest {
    @Test
    void shouldMapEventEntityToEventDto() {

        EventEntity eventEntity = new EventEntity();
        eventEntity.setId(1L);
        eventEntity.setFrom(LocalDateTime.of(2023, Month.SEPTEMBER, 13, 12, 00));
        eventEntity.setTo(LocalDateTime.of(2023, Month.SEPTEMBER, 13, 12, 50, 00));

        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setId(1L);
        itemEntity.setName("Usuwanie przebarwień twarz");
        itemEntity.setPrice(BigDecimal.valueOf(130.0));

        eventEntity.setItem(itemEntity);

        EventDto eventDto = EventDtoMapper.mapToEventDto(eventEntity);

        assertThat(eventDto.getId()).isEqualTo(1L);
        assertThat(eventDto.getItemName()).isEqualTo("Usuwanie przebarwień twarz");
        assertThat(eventDto.getItemPrice()).isEqualByComparingTo(BigDecimal.valueOf(130.0));
        assertThat(eventDto.getFromTime()).isEqualTo(LocalDateTime.of(2023, Month.SEPTEMBER, 13, 12, 00));
        assertThat(eventDto.getToTime()).isEqualTo(LocalDateTime.of(2023, Month.SEPTEMBER, 13, 12, 50));
    }

    @Test
    public void testMapToEventDtoWithNullEntity() {

        EventDto dto = EventDtoMapper.mapToEventDto(null);

        assertNull(dto);
    }

}
