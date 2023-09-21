package pl.sdacademy.booking.validator;

import org.junit.jupiter.api.Test;
import pl.sdacademy.booking.model.NewEventDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class NewEventDtoValidatorTest {
    @Test
    void shouldCheckThatFromIsNull() {
        NewEventDto input= NewEventDto.builder().itemName("przyklad")
                .fromTime(null)
                .toTime(LocalDateTime.of(LocalDate.now(), LocalTime.NOON))
                .build();
        List<String> result=NewEventDtoValidator.validate(input);
        assertThat(result).hasSize(1).contains("From is null");
    }
    @Test
    void shouldCheckThatToIsNull() {
        NewEventDto input= NewEventDto.builder().itemName("przyklad")
                .toTime(null)
                .fromTime(LocalDateTime.of(LocalDate.now(), LocalTime.NOON))
                .build();
        List<String> result=NewEventDtoValidator.validate(input);
        assertThat(result).hasSize(1).contains("To is null");
    }
    @Test
    void shouldCheckThatToAndFromIsNull() {
        NewEventDto input= NewEventDto.builder().itemName("przyklad")
                .toTime(null)
                .fromTime(null)
                .build();
        List<String> result=NewEventDtoValidator.validate(input);
        assertThat(result).hasSize(2).contains("To is null");
    }

    @Test
    void shouldCheckThatToIsSmallerThanFrom() {
        NewEventDto input= NewEventDto.builder().itemName("przyklad")
                .toTime(LocalDateTime.of(LocalDate.now(), LocalTime.NOON))
                .fromTime(LocalDateTime.of(LocalDate.now(), LocalTime.NOON).plusMinutes(1L))
                .build();
        List<String> result=NewEventDtoValidator.validate(input);
        assertThat(result).hasSize(2).contains("To is before from");
    }

    @Test
    void shouldCheckThatDurationIsGreaterThan30Min() {
        NewEventDto input= NewEventDto.builder().itemName("przyklad")
                .toTime(LocalDateTime.of(LocalDate.now(), LocalTime.NOON).plusMinutes(31))
                .fromTime(LocalDateTime.of(LocalDate.now(), LocalTime.NOON))
                .build();
        List<String> result=NewEventDtoValidator.validate(input);
        assertThat(result).hasSize(2).contains("Too long event");
    }

    @Test
    void shouldCheckThatDateIsInThePast() {
        NewEventDto input= NewEventDto.builder().itemName("przyklad")
                .toTime(LocalDateTime.of(LocalDate.now(), LocalTime.NOON))
                .fromTime(LocalDateTime.of(LocalDate.now(), LocalTime.NOON).minusMinutes(1L))
                .build();
        List<String> result=NewEventDtoValidator.validate(input);
        assertThat(result).hasSize(1).contains("Time in the past");
    }
    @Test
    void shouldCheckThatFromIsBefore8() {
        NewEventDto input= NewEventDto.builder().itemName("przyklad")
                .toTime(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(8,9)))
                .fromTime(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(7,59)))
                .build();
        List<String> result=NewEventDtoValidator.validate(input);
        assertThat(result).hasSize(1).contains("Time out of working hours");
    }
    @Test
    void shouldCheckThatToIsAfter16() {
        NewEventDto input= NewEventDto.builder().itemName("przyklad")
                .toTime(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(16,9)))
                .fromTime(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(15,50)))
                .build();
        List<String> result=NewEventDtoValidator.validate(input);
        assertThat(result).hasSize(1).contains("Time out of working hours");
    }

    @Test
    void shouldCheckThatProductIsNull() {
        NewEventDto input= NewEventDto.builder().itemName("przyklad")
                .toTime(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(15,39)))
                .fromTime(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(15,30)))
                .itemName(null)
                .build();
        List<String> result=NewEventDtoValidator.validate(input);
        assertThat(result).hasSize(1).contains("Product is null");
    }


    }
