package com.sparta.schedule.exception;

public class FileException extends RuntimeException{

    public FileException() {
        super("File processing error");
    }
    public FileException(String message) {
        super(message);
    }

}
