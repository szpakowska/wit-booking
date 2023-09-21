package pl.sdacademy.booking.validator;

import org.junit.jupiter.api.Test;
import pl.sdacademy.booking.model.NewEventDto;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class NewEventDtoValidatorTest {
    @Test
    void shouldCheckThatFromIsNull() {
        NewEventDto input = NewEventDto.builder().itemName("przyklad")
                .fromTime(null)
                .toTime(LocalDateTime.of(2023, 9, 19, 19, 56))
                .build();
        List<String> result = NewEventDtoValidator.validate(input);
        assertThat(result).hasSize(1).contains("From is null");
    }

    @Test
    void shouldCheckThatToIsNull() {
        NewEventDto input = NewEventDto.builder().itemName("przyklad")
                .toTime(null)
                .fromTime(LocalDateTime.of(2023, 9, 19, 19, 56))
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
    void shouldCheckMaximumSessionDuration(){
        NewEventDto input = NewEventDto.builder().itemName("przyklad")
                .fromTime(LocalDateTime.of(2023, 9, 19, 12, 00))
                .toTime(LocalDateTime.of(2023, 9, 19, 12, 31))
                .build();
        List<String> result = NewEventDtoValidator.validate(input);
        assertThat(result).hasSize(1).contains("Too long event");
    }

    @Test
    void shouldCheckMinimumSessionDuration(){
        NewEventDto input = NewEventDto.builder().itemName("przyklad")
                .fromTime(LocalDateTime.of(2023, 9, 19, 12, 00))
                .toTime(LocalDateTime.of(2023, 9, 19, 12, 5))
                .build();
        List<String> result = NewEventDtoValidator.validate(input);
        assertThat(result).hasSize(1).contains("Too short event");
    }
    @Test
    void shouldCheckThatFromIsPast() {
        NewEventDto input = NewEventDto.builder().itemName("przyklad")
                .toTime(LocalDateTime.now().plusMinutes(20))
                .fromTime(LocalDateTime.of(2022, 9, 21, 10, 30))
                .build();
        List<String> result = NewEventDtoValidator.validate(input);
        assertThat(result).hasSize(2).contains("From date is from the past");
    }
    @Test
    void shouldCheckThatToIsPast(){

        NewEventDto input= NewEventDto.builder().itemName("przyklad")
                .fromTime(LocalDateTime.now())
                .toTime(LocalDateTime.of(2022, 9, 21, 10, 30))
                .build();
        List<String> result=NewEventDtoValidator.validate(input);
        assertThat(result).hasSize(3).contains("To date is from the past");
    }
    @Test
    void shouldCheckThatToAndFromIsPast(){
        NewEventDto input= NewEventDto.builder().itemName("przyklad")
                .fromTime(LocalDateTime.now().minusSeconds(1L))
                .toTime(LocalDateTime.now().minusSeconds(1L))
                .build();
        List<String> result=NewEventDtoValidator.validate(input);
        assertThat(result).hasSize(2).contains("To date is from the past");
    }

    @Test
    void shouldCheckThatItemNameIsEmpty() {
        NewEventDto input = NewEventDto.builder().itemName("")
                .fromTime(LocalDateTime.of(2023, 9, 19, 12, 00))
                .toTime(LocalDateTime.of(2023, 9, 19, 12, 25))
                .build();
        List<String> result = NewEventDtoValidator.validate(input);
        assertThat(result).hasSize(1).contains("Item has no name");
    }

    @Test
    void shouldCheckThatItemNameIsNull() {
        NewEventDto input = NewEventDto.builder().itemName(null)
                .fromTime(LocalDateTime.of(2023, 9, 19, 12, 00))
                .toTime(LocalDateTime.of(2023, 9, 19, 12, 25))
                .build();
        List<String> result = NewEventDtoValidator.validate(input);
        assertThat(result).hasSize(1).contains("Item has no name");
    }
}