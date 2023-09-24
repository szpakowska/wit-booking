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
                .toTime(LocalDateTime.of(2023, 9, 19, 19, 56))
                .build();
        List<String> result = NewEventDtoValidator.validate(input, clock);
        assertThat(result).hasSize(1).contains("Time of event must be set");
    }

    @Test
    void shouldCheckThatToIsNull() {
        NewEventDto input = NewEventDto.builder().itemName("przyklad")
                .toTime(null)
                .fromTime(LocalDateTime.of(2023, 9, 19, 19, 56))
                .build();
        List<String> result = NewEventDtoValidator.validate(input, clock);
        assertThat(result).hasSize(1).contains("Event cannot be set in the past");
    }

    @Test
    void shouldCheckThatToAndFromIsNull() {
        NewEventDto input = NewEventDto.builder().itemName("przyklad")
                .toTime(null)
                .fromTime(null)
                .build();
        List<String> result = NewEventDtoValidator.validate(input, clock);
        assertThat(result).hasSize(1).contains("Time of event must be set");
    }

    @Test
    void shouldCheckThatToTimeIsBeforeFrom() {
        NewEventDto input = NewEventDto.builder().itemName("przyklad")
                .fromTime(LocalDateTime.of(2223, 9, 19, 9, 56))
                .toTime(LocalDateTime.of(2223, 9, 19, 9, 52))
                .build();
        List<String> result = NewEventDtoValidator.validate(input, clock);
        assertThat(result).hasSize(1).contains("Event's length must be from 10-30 minutes");
    }

    @Test
    void shouldCheckMaximumSessionDuration() {
        NewEventDto input = NewEventDto.builder().itemName("przyklad")
                .fromTime(LocalDateTime.of(2223, 9, 19, 9, 00))
                .toTime(LocalDateTime.of(2223, 9, 19, 9, 52).plusMinutes(35L))
                .build();
        List<String> result = NewEventDtoValidator.validate(input, clock);
        assertThat(result).hasSize(1).contains("Event's length must be from 10-30 minutes");
    }

    @Test
    void shouldCheckMinimumSessionDuration() {
        NewEventDto input = NewEventDto.builder().itemName("przyklad")
                .fromTime(LocalDateTime.of(2223, 9, 19, 9, 00))
                .toTime(LocalDateTime.of(2223, 9, 19, 9, 05))
                .build();
        List<String> result = NewEventDtoValidator.validate(input, clock);
        assertThat(result).hasSize(1).contains("Event's length must be from 10-30 minutes");
    }

    @Test
    void shouldCheckThatFromIsPast() {
        clock = Clock.fixed(Instant.parse("2124-09-29T16:00:00Z"), ZoneId.systemDefault());
        NewEventDto input = NewEventDto.builder().itemName("przyklad")
                .toTime(LocalDateTime.of(2124, 9, 22, 8, 25))
                .fromTime(LocalDateTime.of(2124, 9, 22, 8, 30))
                .build();
        List<String> result = NewEventDtoValidator.validate(input, clock);
        assertThat(result).hasSize(1).contains("Event's length must be from 10-30 minutes");
    }


    @Test
    void shouldCheckThatToIsPast() {
        clock = Clock.fixed(Instant.parse("2124-09-29T16:00:00Z"), ZoneId.systemDefault());
        NewEventDto input = NewEventDto.builder().itemName("przyklad")
                .fromTime(LocalDateTime.of(2124, 9, 22, 8, 30))
                .toTime(LocalDateTime.of(2124, 9, 22, 8, 25))
                .build();
        List<String> result = NewEventDtoValidator.validate(input, clock);
        assertThat(result).hasSize(1).contains("Event's length must be from 10-30 minutes");
    }

    @Test
    void shouldCheckThatToAndFromIsPast() {
        NewEventDto input = NewEventDto.builder().itemName("przyklad")
                .fromTime(LocalDateTime.of(LocalDate.now(), LocalTime.NOON).minusDays(1L))
                .toTime(LocalDateTime.of(LocalDate.now(), LocalTime.NOON).minusDays(1L))
                .build();
        List<String> result = NewEventDtoValidator.validate(input, clock);
        assertThat(result).hasSize(1).contains("Event cannot be set in the past");
    }

    @Test
    void shouldCheckThatItemNameIsEmpty() {
        clock = Clock.fixed(Instant.parse("2022-09-29T16:00:00Z"), ZoneId.systemDefault());
        NewEventDto input = NewEventDto.builder().itemName("")
                // relatywny czas (LocalDateTime.now() jako dane testowe tez moze byc stosowany
                // ale musimy miec pewnosc ze spelnia warunki
                // w naszym przypadku z uwagi na godziny pracy nie ma takiej gwaranci
                .fromTime(LocalDateTime.of(2023, 9, 22, 8, 25))
                .toTime(LocalDateTime.of(2023, 9, 22, 8, 45))
                .build();
        List<String> result = NewEventDtoValidator.validate(input, clock);
        assertThat(result).hasSize(1).contains("Item name have to be set");
    }


    @Test
    void shouldCheckThatItemNameIsNull() {
        clock = Clock.fixed(Instant.parse("2022-09-29T16:00:00Z"), ZoneId.systemDefault());
        NewEventDto input = NewEventDto.builder().itemName(null)
                // relatywny czas (LocalDateTime.now() jako dane testowe tez moze byc stosowany
                // ale musimy miec pewnosc ze spelnia warunki
                // w naszym przypadku z uwagi na godziny pracy nie ma takiej gwaranci
                .fromTime(LocalDateTime.of(2023, 9, 22, 8, 25))
                .toTime(LocalDateTime.of(2023, 9, 22, 8, 45))
                .build();
        List<String> result = NewEventDtoValidator.validate(input, clock);
        assertThat(result).hasSize(1).contains("Item name have to be set");
    }

    @Test
    void shouldCheckThatFromIsBeforeOpening() {
        NewEventDto input = NewEventDto.builder()
                .itemName("przyklad")
                // czas dlugo w przyszlosc powinien nas zabezpieczyc przed bledem - mozna tak to osiagnac ale nie ma 100% pewnosci
                .fromTime(LocalDateTime.of(2124, 9, 22, 18, 25))
                .toTime(LocalDateTime.of(2124, 9, 22, 18, 56))
                .build();
        List<String> result = NewEventDtoValidator.validate(input, clock);
        assertThat(result).hasSize(1).contains("Event can't be set out of the working hours - from 08:00 to 16:00");
    }


    @Test
    void shouldCheckThatToIsAfterClosing() {
        NewEventDto input = NewEventDto.builder()
                .itemName("przyklad")
                // czas dlugo w przyszlosc powinien nas zabezpieczyc przed bledem - mozna tak to osiagnac ale nie ma 100% pewnosci
                .fromTime(LocalDateTime.of(2224, 9, 22, 18, 25))
                .toTime(LocalDateTime.of(2224, 9, 22, 18, 56))
                .build();
        List<String> result = NewEventDtoValidator.validate(input, clock);
        assertThat(result).hasSize(1).contains("Event can't be set out of the working hours - from 08:00 to 16:00");
    }

}