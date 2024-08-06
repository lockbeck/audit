package com.lockbeck.exceptions;

public class BadRequestException extends RuntimeException {
    private static final long serialVerisionUID = 1;

    public BadRequestException(String message) {
        super(message);
    }
}
