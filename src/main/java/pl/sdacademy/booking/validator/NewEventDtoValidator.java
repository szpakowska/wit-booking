package pl.sdacademy.booking.validator;

import pl.sdacademy.booking.model.NewEventDto;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

public class NewEventDtoValidator {

    private static final LocalTime open = LocalTime.of(8, 0);
    private static final LocalTime close = LocalTime.of(16, 0);
    private static final LocalTime maxSessionTime = LocalTime.of(0, 30);
    private static final LocalTime minSessionTime = LocalTime.of(0, 10);


    public static List<String> validate(NewEventDto newEventDto) {
        List<String> result = new ArrayList<>();

        // validate event :

        //validate itemName- can be null or empty
        //validate timeFrom and to:
        //duration- to can be before from, session can be too long session can be too short
        // from not in open hours, not enough time for session
        //validate timeTo null, past
        // duration- to can be before from, session can be too long session can be too short
        // from not in open hours, not enough time for session

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
            if (isEventTooLong(durationBetween)) {
                result.add("Too long event");
            }
            if (isEventTooShort(durationBetween)) {
                result.add("Too short event");
            }

            //date in the future
            if (isFromTimePastDate(newEventDto)) {
                result.add("From date is from the past");
            }
            if (isToTimePastDate(newEventDto)) {
                result.add("To date is from the past");
            }

            //date from 8 to 16
//        zalozylam,ze najdluzsza sesja trwa 30 minut, najkrotsza 15 minut, co wplywa na graniczne wartosci godzin

//
//            if (newEventDto.getFromTime().getHour() < open.getHour()) {
//                result.add("From is before opening");
//            }
//            if (newEventDto.getToTime().getHour() > close.getHour()) {
//                result.add("To is after closing");
//            }
//
//            if (newEventDto.getFromTime().getHour() > close.getHour() - maxSession.getHour()) {
//                result.add("To is more than maximum session maxSessionTime before closing");
//            }
//            if (newEventDto.getToTime().getHour() < open.getHour() - minSession.getHour()) {
//                result.add("To is less than minimum session maxSessionTime after opening");
//            }
        }

        //item name is null
        if (isItemWithoutName(newEventDto)) {
            result.add("Item has no name");
        }
        return result;
    }

    private static boolean isEventTooLong(Duration durationBetween) {
        return durationBetween.toMinutes() > maxSessionTime.getMinute();
    }

    private static boolean isEventTooShort(Duration durationBetween) {
        return durationBetween.toMinutes() < minSessionTime.getMinute();
    }

    private static boolean isToTimePastDate(NewEventDto newEventDto) {
        return newEventDto.getToTime().isBefore(LocalDateTime.now(Clock.systemDefaultZone()));
    }

    private static boolean isFromTimePastDate(NewEventDto newEventDto) {
        return newEventDto.getFromTime().isBefore(LocalDateTime.now(Clock.systemDefaultZone()));
    }

    private static boolean isItemWithoutName(NewEventDto newEventDto) {
        return newEventDto.getItemName() == null || newEventDto.getItemName().isEmpty();
    }

    private static boolean isToTimeNull(NewEventDto newEventDto) {
        return newEventDto.getToTime() == null;
    }

    private static boolean isFromTimeNull(NewEventDto newEventDto) {
        return newEventDto.getFromTime() == null;
    }

    //todo dopisac testy do TimeFrom oraz TimeTo uwzledniajace min i max dlugosc sesji

    //todo refactoring metody validate- podzial na validate timeFrom, validate timeTo,
    // validate Duration Bettween,


}
