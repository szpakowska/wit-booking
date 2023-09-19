package validator;

import pl.sdacademy.booking.model.NewEventDto;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class NewEventDtoValidator {
    public static List<String> validate(NewEventDto newEventDto) {
        List<String> result = new ArrayList<>();

        if (newEventDto.getFromTime() == null) {
            result.add("From is null");
        }
        if (newEventDto.getToTime() == null) {
            result.add("To is null");
        }
        //ZROBIĆ TE 3 JESZCZE + TESTY
        //date in the future
        //date from 8 to 16
        //how long is event
        if(newEventDto.getFromTime() != null && newEventDto.getToTime() != null) {
            Duration duration = Duration.between(newEventDto.getFromTime(), newEventDto.getToTime());
            if (duration.isNegative()) {
                result.add("To is before from");
            }
            if (duration.toMinutes() > 30) {
                result.add("Too long event");
            }
        }
        //item name is null
        return result;
    }
}
