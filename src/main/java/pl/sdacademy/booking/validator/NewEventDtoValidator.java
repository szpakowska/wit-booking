package pl.sdacademy.booking.validator;

import pl.sdacademy.booking.model.NewEventDto;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

public class NewEventDtoValidator {

    private static final LocalTime open = LocalTime.of(8, 0);
    private static final LocalTime close = LocalTime.of(16, 0);
    private static final LocalTime maxSession = LocalTime.of(0, 30);
    private static final LocalTime minSession = LocalTime.of(0, 15);


    public static List<String> validate(NewEventDto newEventDto) {
        List<String> result = new ArrayList<>();

        // validate event :
        //validate itemName
        //validate timeFrom and to: / duration,
        // validate timefrom/ null, past
        //validate timeTo null, past

        Clock clock = Clock.system(ZoneId.of("Europe/Warsaw"));
        LocalDateTime now = LocalDateTime.now(clock);

        if (isFromTimeNull(newEventDto)) {
            result.add("From is null");
        }
        if (isToTimeNull(newEventDto)) {
            result.add("To is null");
        }

        //how long is event
        if (!isToTimeNull(newEventDto) && !isFromTimeNull(newEventDto)) {
            Duration durationBetween = Duration.between(newEventDto.getFromTime(), newEventDto.getToTime());
            if (durationBetween.isNegative()) {
                result.add("To is before from");
            }
            if (durationBetween.toMinutes() > maxSession.getMinute()) {
                result.add("Too long event");
            }
            if (durationBetween.toMinutes() < minSession.getMinute()) {
                result.add("Too short event");
            }

            //date in the future
            // jezeli from jest przed to,
            if (newEventDto.getFromTime().isBefore(LocalDateTime.now())) {
                result.add("From date is from the past");
            }
            if (newEventDto.getToTime().isBefore(LocalDateTime.now())) {
                result.add("To date is from the past");
            }

            //date from 8 to 16
//        zalozylam,ze najdluzsza sesja trwa 30 minut, najkrotsza 15 minut, co wplywa na graniczne wartosci godzin


            if (newEventDto.getFromTime().getHour() < open.getHour()) {
                result.add("From is before opening");
            }
            if (newEventDto.getFromTime().getHour() > close.getHour() - maxSession.getHour()) {
                result.add("To is more than maximum session durationBetween before closing");
            }
            if (newEventDto.getToTime().getHour() > close.getHour()) {
                result.add("To is after closing");
            }
            if (newEventDto.getToTime().getHour() < open.getHour() - minSession.getHour()) {
                result.add("To is less than minimum session durationBetween after opening");
            }
        }


        //item name is null
        if (newEventDto.getItemName() == null || newEventDto.getItemName().isEmpty()) {
            result.add("Item has no name");
        }
        return result;
    }

    private static boolean isToTimeNull(NewEventDto newEventDto) {

        if (newEventDto.getToTime() == null) {
            return true;
        } else return false;
    }

    private static boolean isFromTimeNull(NewEventDto newEventDto) {

        if (newEventDto.getFromTime() == null) {
            return true;
        } else return false;
    }


    //todo  Refactoring metody validate- kazdy case wydzielic do osobnej metody private zwracajacej boolean,
    // dodawanie do listy komunikatu, wydzielic do osobnej metody ze switchem, rozwazyc Consumera

}
