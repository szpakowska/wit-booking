package pl.sdacademy.booking.validator;

import org.apache.commons.lang3.StringUtils;
import pl.sdacademy.booking.model.NewEventDto;
import pl.sdacademy.booking.util.TimeNow;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NewEventDtoValidatorTimeNow {

    public static List<String> validate(NewEventDto newEventDto, TimeNow timeNow) {
        List<String> result = new ArrayList<>();
        if (newEventDto == null) {
            result.add("Event is null");
            return result;
        }
        if (newEventDto.getFromTime() == null) {
            result.add("From is null");
        }
        if (newEventDto.getToTime() == null) {
            result.add("To is null");
        }
        if (newEventDto.getFromTime() != null && newEventDto.getToTime() != null) {
            Duration duration = Duration.between(newEventDto.getFromTime(), newEventDto.getToTime());
            if (duration.isNegative()) {
                result.add("To is before from");
            }
            if (duration.toMinutes() > 30L) {
                result.add("Event too long");
            }
            if (newEventDto.getFromTime().isBefore(timeNow.now())) {
                result.add("From is in the past");
            }
            if (newEventDto.getToTime().isBefore(LocalDateTime.now())) {
                result.add("To is in the past");
            }
            if ((newEventDto.getFromTime().getHour() >= 16) || (newEventDto.getFromTime().getHour() <= 8)) {
                result.add("NewEvent outside working hours");
            }
        }

        // if (newEventDto.getItemName() == null || newEventDto.getItemName().isEmpty()) {
        if (StringUtils.isBlank(newEventDto.getItemName())) {
            //zawsze najpierw sprawdzamy czy coś jest nullem
            //a potem czy jet puste
            //Poniżej tzw. short-circuit evaluation:
            // true || willNeverExecute();
            //false && willNeverExecute();

            result.add("Item name is not set");
        }

        return result;


    }

}

