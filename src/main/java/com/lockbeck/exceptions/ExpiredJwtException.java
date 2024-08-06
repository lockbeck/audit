package com.lockbeck.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ExpiredJwtException extends RuntimeException {
    private static final long serialVerisionUID = 3;

    public ExpiredJwtException(String message) {
        super(message);
    }
}
