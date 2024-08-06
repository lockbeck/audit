package com.lockbeck.exceptions;

public class NotFoundException extends RuntimeException {
    private static final long serialVerisionUID = 2;
    public NotFoundException(String message) {
        super(message);
    }
}
