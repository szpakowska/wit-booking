package pl.sdacademy.booking.util;

import java.time.LocalDateTime;

public class TimeNow {

    public LocalDateTime now() {

        System.out.println("Calling base class for passingTimeControl");

        return LocalDateTime.now();
    }
}

