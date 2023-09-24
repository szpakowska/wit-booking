package pl.sdacademy.booking.validator;

import org.junit.jupiter.api.Test;
import pl.sdacademy.booking.model.NewEventDto;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class NewEventDtoValidatorTest {

    // domyslne ustawienie na poziomie testu uprasza inicjowanie tych ktore dzialaja bez kontroli czasu
    private Clock clock = Clock.systemUTC();

    @Test
    void shouldCheckThatFromIsNull() {
        clock = Clock.fixed(Instant.parse("2023-09-20T16:00:00Z"), ZoneId.systemDefault());

        NewEventDto input = NewEventDto.builder().itemName("przyklad")
                .fromTime(null)
                .toTime(LocalDateTime.now(Clock.systemDefaultZone()).plusMinutes(25L))
                .build();
        List<String> result = NewEventDtoValidator.validate(input);
        assertThat(result).hasSize(1).contains("Time of event must be set");
    }

    @Test
    void shouldCheckThatToIsNull() {
        NewEventDto input = NewEventDto.builder().itemName("przyklad")
                .toTime(null)
                .fromTime(LocalDateTime.now(Clock.systemDefaultZone()).plusMinutes(25L))
                .build();
        List<String> result = NewEventDtoValidator.validate(input);
        assertThat(result).hasSize(1).contains("Time of event must be set");
    }

    @Test
    void shouldCheckThatToAndFromIsNull() {
        NewEventDto input = NewEventDto.builder().itemName("przyklad")
                .toTime(null)
                .fromTime(null)
                .build();
        List<String> result = NewEventDtoValidator.validate(input);
        assertThat(result).hasSize(1).contains("Time of event must be set");
    }

    @Test
    void shouldCheckThatToTimeIsBeforeFrom() {
        NewEventDto input = NewEventDto.builder().itemName("przyklad")
                .fromTime(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.NOON))
                .toTime(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.NOON).minusMinutes(2L))
                .build();
        List<String> result = NewEventDtoValidator.validate(input);
        assertThat(result).hasSize(1).contains("Event's length must be from 10-30 minutes");
    }

    @Test
    void shouldCheckMaximumSessionDuration() {
        NewEventDto input = NewEventDto.builder().itemName("przyklad")
                .fromTime(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.NOON))
                .toTime(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.NOON).plusMinutes(35L))
                .build();
        List<String> result = NewEventDtoValidator.validate(input);
        assertThat(result).hasSize(1).contains("Event's length must be from 10-30 minutes");
    }

    @Test
    void shouldCheckMinimumSessionDuration() {
        NewEventDto input = NewEventDto.builder().itemName("przyklad")
                .fromTime(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.NOON))
                .toTime(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.NOON))
                .build();
        List<String> result = NewEventDtoValidator.validate(input);
        assertThat(result).hasSize(1).contains("Event's length must be from 10-30 minutes");
    }

    @Test
    void shouldCheckThatFromIsPast() {
        NewEventDto input = NewEventDto.builder().itemName("przyklad")
                .toTime(LocalDateTime.now())
                .fromTime(LocalDateTime.now().minusMinutes(16L))
                .build();
        List<String> result = NewEventDtoValidator.validate(input);
        assertThat(result).hasSize(1).contains("Event cannot be set in the past");
    }


    @Test
    void shouldCheckThatToIsPast() {
        NewEventDto input = NewEventDto.builder().itemName("przyklad")
                .fromTime(LocalDateTime.now(Clock.systemDefaultZone()))
                .toTime(LocalDateTime.now(Clock.systemDefaultZone()).minusMinutes(2L))
                .build();
        List<String> result = NewEventDtoValidator.validate(input);
        assertThat(result).hasSize(1).contains("Event cannot be set in the past");
    }

    @Test
    void shouldCheckThatToAndFromIsPast() {
        NewEventDto input = NewEventDto.builder().itemName("przyklad")
                .fromTime(LocalDateTime.of(LocalDate.now(), LocalTime.NOON).minusDays(1L))
                .toTime(LocalDateTime.of(LocalDate.now(), LocalTime.NOON).minusDays(1L))
                .build();
        List<String> result = NewEventDtoValidator.validate(input);
        assertThat(result).hasSize(1).contains("Event cannot be set in the past");
    }

    @Test
    void shouldCheckThatItemNameIsEmpty() {
        NewEventDto input = NewEventDto.builder().itemName("")
                .fromTime(LocalDateTime.of(LocalDate.now(), LocalTime.NOON))
                .toTime(LocalDateTime.of(LocalDate.now(), LocalTime.NOON).plusMinutes(20L))
                .build();
        List<String> result = NewEventDtoValidator.validate(input);
        assertThat(result).hasSize(1).contains("Item name have to be set");
    }


    @Test
    void shouldCheckThatItemNameIsNull() {
        NewEventDto input = NewEventDto.builder().itemName(null)
                .fromTime(LocalDateTime.of(LocalDate.now(), LocalTime.NOON))
                .toTime(LocalDateTime.of(LocalDate.now(), LocalTime.NOON).plusMinutes(20L))
                .build();
        List<String> result = NewEventDtoValidator.validate(input);
        assertThat(result).hasSize(1).contains("Item name have to be set");
    }

    @Test
    void shouldCheckThatFromIsBeforeOpening() {
        NewEventDto input = NewEventDto.builder().itemName("przyklad")
                .toTime(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.NOON))
                .fromTime(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.NOON).minusMinutes(241L))
                .build();
        List<String> result = NewEventDtoValidator.validate(input);
        assertThat(result).hasSize(1).contains("Event can't be set out of the working hours - from 08:00 to 16:00");
    }


    @Test
    void shouldCheckThatToIsAfterClosing() {
        NewEventDto input = NewEventDto.builder().itemName("przyklad")
                .toTime(LocalDateTime.of(LocalDate.now().plusDays(1),LocalTime.NOON).plusMinutes(280L))
                .fromTime(LocalDateTime.of(LocalDate.now().plusDays(1),LocalTime.NOON))
                .build();
        List<String> result = NewEventDtoValidator.validate(input);
        assertThat(result).hasSize(1).contains("Event's length must be from 10-30 minutes");
    }

}