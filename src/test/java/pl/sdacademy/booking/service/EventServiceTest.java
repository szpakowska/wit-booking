package pl.sdacademy.booking.service;

import org.junit.jupiter.api.Test;
import pl.sdacademy.booking.data.EventEntity;
import pl.sdacademy.booking.data.ItemEntity;
import pl.sdacademy.booking.model.EventDto;

import pl.sdacademy.booking.repository.EventRepository;

import java.math.BigDecimal;

import java.time.LocalDateTime;

import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;

class EventServiceTest {

    private EventService eventService;

    @Test
    void shouldResultAllEventsInDbAsListOfDto() {
        eventService = new EventService(new TestEventRepository(), new ItemServiceTest.TestItemRepository());


        List<EventDto> result = eventService.findEvents();

        assertThat(result).hasSize(2);
        EventDto first = result.get(0);
        // liczba ponizszych asercji sygnalizuje, ze klasa moze miec za duzo odpowiedzialnosci
        // powinna zostac podzielona na bardziej specjalistyczne klasy
        assertThat(first.getName()).isEqualTo("jeden");
        assertThat(first.getPrice()).isEqualTo(BigDecimal.valueOf(120.0));
        assertThat(first.getFromTime()).isEqualTo(LocalDateTime.of(2023, 9, 18, 10, 30, 00));
        assertThat(first.getToTime()).isEqualTo(LocalDateTime.of(2023, 9, 18, 11, 30, 00));


        EventDto second = result.get(1);
        assertThat(second.getName()).isEqualTo("jeden");
    }

    public static class TestEventRepository implements EventRepository {

        @Override
        public List<EventEntity> findEvents() {
            ItemEntity firstItem = new ItemEntity();
            firstItem.setId(1L);
            firstItem.setName("jeden");
            firstItem.setDescription("opis pierwszy");
            firstItem.setPrice(BigDecimal.valueOf(120.0));
            EventEntity first = new EventEntity();
            first.setId(1L);
            first.setFrom(LocalDateTime.of(2023, 9, 18, 10, 30, 00));
            first.setTo(LocalDateTime.of(2023, 9, 18, 11, 30, 00));
            first.setItem(firstItem);

            EventEntity second = new EventEntity();
            second.setId(2L);
            second.setFrom(LocalDateTime.of(2023, 9, 18, 11, 30, 00));
            second.setTo(LocalDateTime.of(2023, 9, 18, 12, 30, 00));
            second.setItem(firstItem);
            return List.of(first, second);
        }


        @Override
        public void addEvent(EventEntity item) {

        }

        @Override
        public Long findEventsByDate(LocalDateTime date) {
            return null;
        }

    }
}
