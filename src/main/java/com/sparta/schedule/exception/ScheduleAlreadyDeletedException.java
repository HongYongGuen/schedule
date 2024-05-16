package com.sparta.schedule.exception;

public class ScheduleAlreadyDeletedException extends RuntimeException {

    public ScheduleAlreadyDeletedException() {
        super("Schedule is already deleted");
    }

    public ScheduleAlreadyDeletedException(String message) {
        super(message);
    }

}