package com.sparta.schedule.exception;

public class ScheduleNotFoundException extends RuntimeException {

    public ScheduleNotFoundException() {
        super("Schedule Not Found Exception");
    }

    public ScheduleNotFoundException(String message) {
        super(message);
    }
}
