package com.sparta.schedule.exception;

public class PasswordMismatchException extends RuntimeException {

    public PasswordMismatchException(){
        super("Password Mismatch Exception");
    }
    public PasswordMismatchException(String message) {
        super(message);
    }

}
