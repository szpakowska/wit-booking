package pl.sdacademy.booking.validator;

import org.junit.jupiter.api.Test;
import pl.sdacademy.booking.model.NewEventDto;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class NewEventDtoValidatorTest {
    @Test
    void shouldCheckThatFromIsNull() {
        NewEventDto input= NewEventDto.builder().itemName("przyklad")
                .fromTime(null)
                .toTime(LocalDateTime.of(2023,9,19,19,56))
                .build();
        List<String> result=NewEventDtoValidator.validate(input);
        assertThat(result).hasSize(2).contains("From is null");
    }
    @Test
    void shouldCheckThatToIsNull() {
        NewEventDto input= NewEventDto.builder().itemName("przyklad")
                .toTime(null)
                .fromTime(LocalDateTime.of(2023,9,19,19,56))
                .build();
        List<String> result=NewEventDtoValidator.validate(input);
        assertThat(result).hasSize(2).contains("To is null");
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
    void shouldCheckThatFromIsPast(){
        NewEventDto input= NewEventDto.builder().itemName("przyklad")
                .toTime(LocalDateTime.now().plusSeconds(1L))
                .fromTime(LocalDateTime.now().minusSeconds(1L))
                .build();
        List<String> result=NewEventDtoValidator.validate(input);
        assertThat(result).hasSize(1).contains("From date is from the past");
    }
    @Test
    void shouldCheckThatToIsPast(){
        NewEventDto input= NewEventDto.builder().itemName("przyklad")
                .fromTime(LocalDateTime.now().plusSeconds(1L))
                .toTime(LocalDateTime.now().minusSeconds(1L))
                .build();
        List<String> result=NewEventDtoValidator.validate(input);
        assertThat(result).hasSize(2).contains("To date is from the past");
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


}