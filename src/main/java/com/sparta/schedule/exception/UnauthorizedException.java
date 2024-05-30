package com.sparta.schedule.exception;

public class UnauthorizedException  extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
