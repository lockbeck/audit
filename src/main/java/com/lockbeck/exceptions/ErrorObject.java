package com.lockbeck.exceptions;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorObject {
    private Integer statusCode;
    private String message;
    private String error;
    private Date timestamp;
}
