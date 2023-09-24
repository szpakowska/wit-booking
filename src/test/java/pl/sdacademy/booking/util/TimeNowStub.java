package pl.sdacademy.booking.util;

import java.time.LocalDateTime;

public class TimeNowStub extends TimeNow {
    public LocalDateTime now() {
        System.out.println("Calling facade class");
        return LocalDateTime.of(2023, 9, 20, 21, 9, 10);
    }
}



