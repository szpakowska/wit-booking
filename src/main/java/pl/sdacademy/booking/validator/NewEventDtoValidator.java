package pl.sdacademy.booking.validator;

import org.apache.commons.lang3.StringUtils;
import pl.sdacademy.booking.model.NewEventDto;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class NewEventDtoValidator {

    private static final LocalTime open = LocalTime.of(8, 0);
    private static final LocalTime close = LocalTime.of(16, 0);
    private static final LocalTime maxSessionTime = LocalTime.of(0, 30);
    private static final LocalTime minSessionTime = LocalTime.of(0, 10);


    public static List<String> validate(NewEventDto newEventDto) {

        List<String> result = new ArrayList<>();

        if (!isEventNull(newEventDto, result)) {
            if (!isTimeValueNull(newEventDto, result) && !isItemWithoutName(newEventDto, result)) {
                if (!isEventTimePastDate(newEventDto, result)) {
                    if (!isEventTimeAfterClosing(newEventDto, result) && !isEventTimeBeforeOpening(newEventDto, result) && !isEnoughTimeForEvent(newEventDto, result)) {
                        if (!isEventDurationProper(newEventDto, result)) {
                            result.add("Validation process completed");
                        }
                    }
                }
            }
        }
        return result;
    }


    private static boolean isEnoughTimeForEvent(NewEventDto newEventDto, List<String> result) {

        if (newEventDto.getToTime().getHour() < open.getHour() - minSessionTime.getHour()
                || newEventDto.getFromTime().getHour() > close.getHour() - maxSessionTime.getHour()) {
            result.add("There is not enough time for this event");
            return true;
        } else return false;
    }

    private static boolean isEventTimeAfterClosing(NewEventDto newEventDto, List<String> result) {

        if (newEventDto.getToTime().getHour() > close.getHour()
                || newEventDto.getFromTime().getHour() >= close.getHour()
                || newEventDto.getFromTime().getHour() >= close.getHour()) {
            result.add("Event can't be set out of the working hours - from 08:00 to 16:00");
            return true;
        } else return false;
    }

    private static boolean isEventTimeBeforeOpening(NewEventDto newEventDto, List<String> result) {

        if (newEventDto.getFromTime().getHour() < open.getHour()
                || newEventDto.getToTime().getHour() < open.getHour()) {
            result.add("Event can't be set out of the working hours - from 08:00 to 16:00");
            return true;
        } else return false;
    }

    private static boolean isEventDurationProper(NewEventDto newEventDto, List<String> result) {
        Duration durationBetween = Duration.between(newEventDto.getFromTime(), newEventDto.getToTime());
        if (durationBetween.toMinutes() > maxSessionTime.getMinute() ||
                durationBetween.toMinutes() < minSessionTime.getMinute()) {
            result.add("Event's length must be from 10-30 minutes");
            return true;
        } else return false;
    }

    private static boolean isEventTimePastDate(NewEventDto newEventDto, List<String> result) {
        if (newEventDto.getFromTime().isBefore(LocalDateTime.now(Clock.systemDefaultZone()))
                || newEventDto.getToTime().isBefore(LocalDateTime.now(Clock.systemDefaultZone()))) {
            result.add("Event cannot be set in the past");
            return true;
        } else return false;
    }

    private static boolean isItemWithoutName(NewEventDto newEventDto, List<String> result) {
//        if (newEventDto.getItemName() == null || newEventDto.getItemName().isEmpty()) {
//            result.add("Item name have to be set");
//        }
        if (StringUtils.isAllBlank(newEventDto.getItemName())) {
            result.add("Item name have to be set");
            return true;
        } else return false;
    }

    private static boolean isTimeValueNull(NewEventDto newEventDto, List<String> result) {
        if (newEventDto.getFromTime() == null || newEventDto.getFromTime() == null) {
            result.add("Time of event must be set");
            return true;
        } else return false;
    }

    private static boolean isEventNull(NewEventDto newEventDto, List<String> result) {
        if (newEventDto == null) {
            result.add("Event must be set");
            return true;
        } else return false;
    }
}
