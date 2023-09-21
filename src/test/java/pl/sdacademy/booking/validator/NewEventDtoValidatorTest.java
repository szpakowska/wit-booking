package pl.sdacademy.booking.validator;

import org.junit.jupiter.api.Test;
import pl.sdacademy.booking.model.NewEventDto;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class NewEventDtoValidatorTest {
    @Test
    void shouldCheckThatFromIsNull() {
        NewEventDto input = NewEventDto.builder().itemName("przyklad")
                .fromTime(null)
                .toTime(LocalDateTime.now(Clock.systemDefaultZone()).plusMinutes(25L))
                .build();
        List<String> result = NewEventDtoValidator.validate(input);
        assertThat(result).hasSize(1).contains("From is null");
    }

    @Test
    void shouldCheckThatToIsNull() {
        NewEventDto input = NewEventDto.builder().itemName("przyklad")
                .toTime(null)
                .fromTime(LocalDateTime.now(Clock.systemDefaultZone()).plusMinutes(25L))
                .build();
        List<String> result = NewEventDtoValidator.validate(input);
        assertThat(result).hasSize(1).contains("To is null");
    }

    @Test
    void shouldCheckThatToAndFromIsNull() {
        NewEventDto input = NewEventDto.builder().itemName("przyklad")
                .toTime(null)
                .fromTime(null)
                .build();
        List<String> result = NewEventDtoValidator.validate(input);
        assertThat(result).hasSize(2).contains("To is null");
    }

    @Test
    void shouldCheckThatToTimeIsBeforeFrom() {
        NewEventDto input = NewEventDto.builder().itemName("przyklad")
                .fromTime(LocalDateTime.of(LocalDate.now().plusDays(1),LocalTime.NOON))
                .toTime(LocalDateTime.of(LocalDate.now().plusDays(1),LocalTime.NOON).minusMinutes(2L))
                .build();
        List<String> result = NewEventDtoValidator.validate(input);
        assertThat(result).hasSize(2).contains("To is before from", "Too short event");
    }

    @Test
    void shouldCheckMaximumSessionDuration() {
        NewEventDto input = NewEventDto.builder().itemName("przyklad")
                .fromTime(LocalDateTime.of(LocalDate.now().plusDays(1),LocalTime.NOON))
                .toTime(LocalDateTime.of(LocalDate.now().plusDays(1),LocalTime.NOON).plusMinutes(35L))
                .build();
        List<String> result = NewEventDtoValidator.validate(input);
        assertThat(result).hasSize(1).contains("Too long event");
    }

    @Test
    void shouldCheckMinimumSessionDuration() {
        NewEventDto input = NewEventDto.builder().itemName("przyklad")
                .fromTime(LocalDateTime.of(LocalDate.now().plusDays(1),LocalTime.NOON))
                .toTime(LocalDateTime.of(LocalDate.now().plusDays(1),LocalTime.NOON))
                .build();
        List<String> result = NewEventDtoValidator.validate(input);
        assertThat(result).hasSize(1).contains("Too short event");
    }

//    @Test
//    void shouldCheckThatFromIsPast() {
//        NewEventDto input = NewEventDto.builder().itemName("przyklad")
//                .toTime(LocalDateTime.now())
//                .fromTime(LocalDateTime.now().minusMinutes(16L))
//                .build();
//        List<String> result = NewEventDtoValidator.validate(input);
//        assertThat(result).hasSize(1).contains("From date is from the past");
//    }
//

//    @Test
//    void shouldCheckThatToIsPast() {
//        NewEventDto input = NewEventDto.builder().itemName("przyklad")
//                .fromTime(LocalDateTime.now(Clock.systemDefaultZone()))
//                .toTime(LocalDateTime.now(Clock.systemDefaultZone()).minusMinutes(2L))
//                .build();
//        List<String> result = NewEventDtoValidator.validate(input);
//        assertThat(result).hasSize(3).contains("To is before from", "Too short event", "To date is from the past");
//    }

    @Test
    void shouldCheckThatToAndFromIsPast() {
        NewEventDto input = NewEventDto.builder().itemName("przyklad")
                .fromTime(LocalDateTime.of(LocalDate.now(),LocalTime.NOON).minusDays(1L))
                .toTime(LocalDateTime.of(LocalDate.now(),LocalTime.NOON).minusDays(1L))
                .build();
        List<String> result = NewEventDtoValidator.validate(input);
        assertThat(result).hasSize(3).contains("Too short event", "From date is from the past", "To date is from the past");
    }

    @Test
    void shouldCheckThatItemNameIsEmpty() {
        NewEventDto input = NewEventDto.builder().itemName("")
                .fromTime(LocalDateTime.of(LocalDate.now(),LocalTime.NOON))
                .toTime(LocalDateTime.of(LocalDate.now(),LocalTime.NOON).plusMinutes(20L))
                .build();
        List<String> result = NewEventDtoValidator.validate(input);
        assertThat(result).hasSize(1).contains("Item has no name");
    }


    @Test
    void shouldCheckThatItemNameIsNull() {
        NewEventDto input = NewEventDto.builder().itemName(null)
                .fromTime(LocalDateTime.of(LocalDate.now(),LocalTime.NOON))
                .toTime(LocalDateTime.of(LocalDate.now(),LocalTime.NOON).plusMinutes(20L))
                .build();
        List<String> result = NewEventDtoValidator.validate(input);
        assertThat(result).hasSize(1).contains("Item has no name");
    }

    @Test
    void shouldCheckThatFromIsBeforeOpening() {
        NewEventDto input = NewEventDto.builder().itemName("przyklad")
                .toTime(LocalDateTime.of(LocalDate.now().plusDays(1),LocalTime.NOON))
                .fromTime(LocalDateTime.of(LocalDate.now().plusDays(1),LocalTime.NOON).minusMinutes(241L))
                .build();
        List<String> result = NewEventDtoValidator.validate(input);
        assertThat(result).hasSize(2).contains("Too long event", "From is before opening");
    }

    @Test
    void shouldCheckThatToIsAfterClosing() {
        NewEventDto input = NewEventDto.builder().itemName("przyklad")
                .toTime(LocalDateTime.of(LocalDate.now().plusDays(1),LocalTime.NOON).plusMinutes(280L))
                .fromTime(LocalDateTime.of(LocalDate.now().plusDays(1),LocalTime.NOON))
                .build();
        List<String> result = NewEventDtoValidator.validate(input);
        assertThat(result).hasSize(1).contains("Too long event");
    }

}