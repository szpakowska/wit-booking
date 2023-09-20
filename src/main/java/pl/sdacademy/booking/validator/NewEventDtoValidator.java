package pl.sdacademy.booking.validator;

import pl.sdacademy.booking.model.NewEventDto;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
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

        //how long is event
        if (newEventDto.getFromTime() != null && newEventDto.getToTime() != null) {
            Duration duration = Duration.between(newEventDto.getFromTime(), newEventDto.getToTime());
            if (duration.isNegative()) {
                result.add("To is before from");
            }
            if (duration.toMinutes() > 30) {
                result.add("Too long event");
            }
        }

        //date in the future
        //czy Local Date Time bierze pod uwage zmiany czasu?
        if (newEventDto.getFromTime() != null) {
            if (newEventDto.getFromTime().isBefore((LocalDateTime.now()))) {
                result.add("From date is from the past");
            }
        }

        if (newEventDto.getToTime() != null) {
            if (newEventDto.getToTime().isBefore(LocalDateTime.now())) {
                result.add("To date is from the past");
            }
        }

        //date from 8 to 16

        //dopuszczamy from wieksze lub rowne 8:00 i mniejsze niz 15:30
        //dopuszczamy to wieksze od 8:30 i to najpozniej o 16:00

        LocalTime open = LocalTime.of(8, 0);
        LocalTime close = LocalTime.of(16, 0);

        LocalDateTime.now(ZoneId.of("Europe/Warsaw"));


//        int hour = newEventDto.getFromTime().getHour();
//        int minutes = newEventDto.getFromTime().getMinute();


        // godziny otewarcia salonu od osmej czyli mozna sprobowac doubla i zrobic cast hour na doubla.


        //item name is null
        // NewEventDto jest null.
        return result;
    }

//    public static boolean validateNullTimeValues(NewEventDto newEventDto) {
//        boolean timeValuesAreNulls;
//
//
//    }
}
