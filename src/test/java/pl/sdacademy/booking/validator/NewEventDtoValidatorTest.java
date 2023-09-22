package pl.sdacademy.booking.validator;

import org.junit.jupiter.api.Test;
import pl.sdacademy.booking.model.NewEventDto;
import pl.sdacademy.booking.util.TimeNowStub;


import java.time.LocalDateTime;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class NewEventDtoValidatorTest {
    @Test
    void shouldCheckThatFromIsNull() {
        NewEventDto input = NewEventDto.builder().itemName("przykad")
                .fromTime(null)
                .toTime(LocalDateTime.of(2023,9,19,19,56))
                .build();
        List<String> result = NewEventDtoValidator.validate(input, new TimeNowStub());
        assertThat(result).hasSize(1).contains("From is null");

    }

    @Test
    void shouldCheckThatToIsNull() {
        NewEventDto input = NewEventDto.builder().itemName("przykad")
                .toTime(null)
                .fromTime(LocalDateTime.of(2023,9,19,19,56))
                .build();
        List<String> result = NewEventDtoValidator.validate(input, new TimeNowStub());
        assertThat(result).hasSize(1).contains("To is null");

    }

    @Test
    void shouldCheckThatToAndFromIsNull() {
        NewEventDto input = NewEventDto.builder().itemName("przykad")
                .toTime(null)
                .fromTime(null)
                .build();
        List<String> result = NewEventDtoValidator.validate(input, new TimeNowStub());
        assertThat(result).hasSize(2).contains("To is null");

    }

    @Test
    void shouldPositivelyValidateNewEvent(){
        NewEventDto input = NewEventDto.builder()
                .itemName("Henna")
                .fromTime(LocalDateTime.of(2023,10,12,10,30,00))
                .toTime(LocalDateTime.of(2023,10,12,10,45,00))
                .build();

        List<String> result = NewEventDtoValidator.validate(input, new TimeNowStub());

        assertThat(result).hasSize(0); //zero ponieważ nie ma komunikatów o błędach

    }

    @Test
    void shouldCheckNewEventNull(){
        List<String> result = NewEventDtoValidator.validate(null, new TimeNowStub());

        assertThat(result).hasSize(1).contains("Event is null");
    }

    @Test
    void shouldCheckDuration(){
        NewEventDto input = NewEventDto.builder()
                .itemName("Henna")
                .fromTime(LocalDateTime.of(2023,10,12,10,10,00))
                .toTime(LocalDateTime.of(2023,10,12,10,45,00))
                .build();

        List<String> result = NewEventDtoValidator.validate(input, new TimeNowStub());

        assertThat(result).hasSize(1).contains("Event too long");
    }

    @Test
    void shouldCheckFromToOrder(){
        NewEventDto input = NewEventDto.builder()
                .itemName("Henna")
                .toTime(LocalDateTime.of(2023,10,12,10,10,00))
                .fromTime(LocalDateTime.of(2023,10,12,10,45,00))
                .build();

        List<String> result = NewEventDtoValidator.validate(input, new TimeNowStub());

        assertThat(result).hasSize(1).contains("To is before from");
    }

    @Test
    void shouldCheckNameIsNull(){
        NewEventDto input = NewEventDto.builder()
                .itemName(null)
                .fromTime(LocalDateTime.of(2023,10,12,10,30,00))
                .toTime(LocalDateTime.of(2023,10,12,10,45,00))
                .build();

        List<String> result = NewEventDtoValidator.validate(input, new TimeNowStub());

        assertThat(result).hasSize(1).contains("Item name is not set");
    }

    @Test
    void shouldCheckNameIsEmpty(){
        NewEventDto input = NewEventDto.builder()
                .itemName("")
                .fromTime(LocalDateTime.of(2023,10,12,10,30,00))
                .toTime(LocalDateTime.of(2023,10,12,10,45,00))
                .build();

        List<String> result = NewEventDtoValidator.validate(input, new TimeNowStub());

        assertThat(result).hasSize(1).contains("Item name is not set");
    }

    }
