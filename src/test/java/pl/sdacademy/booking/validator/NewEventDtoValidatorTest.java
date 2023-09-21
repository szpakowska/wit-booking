package pl.sdacademy.booking.validator;

import org.junit.jupiter.api.Test;
import pl.sdacademy.booking.model.NewEventDto;

import java.time.Clock;
import java.time.LocalDateTime;
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
                .fromTime(LocalDateTime.now(Clock.systemDefaultZone()).plusMinutes(2L))
                .toTime(LocalDateTime.now(Clock.systemDefaultZone()).minusMinutes(25L))
                .build();
        List<String> result = NewEventDtoValidator.validate(input);
        assertThat(result).hasSize(3).contains("To is before from", "Too short event", "To date is from the past");
    }

    @Test
    void shouldCheckMaximumSessionDuration() {
        NewEventDto input = NewEventDto.builder().itemName("przyklad")
                .fromTime(LocalDateTime.now(Clock.systemDefaultZone()).plusMinutes(2L))
                .toTime(LocalDateTime.now(Clock.systemDefaultZone()).plusMinutes(35L))
                .build();
        List<String> result = NewEventDtoValidator.validate(input);
        assertThat(result).hasSize(1).contains("Too long event");
    }
    @Test
    void shouldCheckMinimumSessionDuration() {
        NewEventDto input = NewEventDto.builder().itemName("przyklad")
                .fromTime(LocalDateTime.now(Clock.systemDefaultZone()).plusMinutes(2L))
                .toTime(LocalDateTime.now(Clock.systemDefaultZone()).plusMinutes(10L))
                .build();
        List<String> result = NewEventDtoValidator.validate(input);
        assertThat(result).hasSize(1).contains("Too short event");
    }

    @Test
    void shouldCheckThatFromIsPast() {
        NewEventDto input = NewEventDto.builder().itemName("przyklad")
                .toTime(LocalDateTime.now(Clock.systemDefaultZone()).plusMinutes(2L))
                .fromTime(LocalDateTime.now(Clock.systemDefaultZone()).minusMinutes(15L))
                .build();
        List<String> result = NewEventDtoValidator.validate(input);
        assertThat(result).hasSize(1).contains("From date is from the past");
    }

    @Test
    void shouldCheckThatToIsPast() {
        NewEventDto input = NewEventDto.builder().itemName("przyklad")
                .fromTime(LocalDateTime.now(Clock.systemDefaultZone()))
                .toTime(LocalDateTime.now(Clock.systemDefaultZone()).minusMinutes(2L))
                .build();
        List<String> result = NewEventDtoValidator.validate(input);
        assertThat(result).hasSize(3).contains("To is before from", "Too short event", "To date is from the past");
    }

    @Test
    void shouldCheckThatToAndFromIsPast() {
        NewEventDto input = NewEventDto.builder().itemName("przyklad")
                .fromTime(LocalDateTime.now(Clock.systemDefaultZone()).minusMinutes(2L))
                .toTime(LocalDateTime.now(Clock.systemDefaultZone()).minusMinutes(2L))
                .build();
        List<String> result = NewEventDtoValidator.validate(input);
        assertThat(result).hasSize(3).contains("Too short event", "From date is from the past", "To date is from the past");
    }

    @Test
    void shouldCheckThatItemNameIsEmpty() {
        NewEventDto input = NewEventDto.builder().itemName("")
                .fromTime(LocalDateTime.now().plusMinutes(2L))
                .toTime(LocalDateTime.now().plusMinutes(20L))
                .build();
        List<String> result = NewEventDtoValidator.validate(input);
        assertThat(result).hasSize(1).contains("Item has no name");
    }

    @Test
    void shouldCheckThatItemNameIsNull() {
        NewEventDto input = NewEventDto.builder().itemName(null)
                .fromTime(LocalDateTime.now().plusMinutes(2L))
                .toTime(LocalDateTime.now().plusMinutes(20L))
                .build();
        List<String> result = NewEventDtoValidator.validate(input);
        assertThat(result).hasSize(1).contains("Item has no name");
    }

    /**


     *     @Test
     *     void shouldCheckThatFromIsBefore8() {
     *         NewEventDto input= NewEventDto.builder().itemName("przyklad")
     *                 .toTime(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(8,9)))
     *                 .fromTime(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(7,59)))
     *                 .build();
     *         List<String> result=NewEventDtoValidator.validate(input);
     *         assertThat(result).hasSize(1).contains("Time out of working hours");
     *     }
     *     @Test
     *     void shouldCheckThatToIsAfter16() {
     *         NewEventDto input= NewEventDto.builder().itemName("przyklad")
     *                 .toTime(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(16,9)))
     *                 .fromTime(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(15,50)))
     *                 .build();
     *         List<String> result=NewEventDtoValidator.validate(input);
     *         assertThat(result).hasSize(1).contains("Time out of working hours");
     *     }
     *
     */

}