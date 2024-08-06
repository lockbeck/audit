package com.lockbeck.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorObject> handlePokemonNotFoundException(BadRequestException ex, WebRequest request) {

        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorObject.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());

        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)

    public ResponseEntity<ErrorObject> handleReviewNotFoundException(NotFoundException ex, WebRequest request) {

        ErrorObject errorObject = new ErrorObject();
        errorObject.setError(HttpStatus.NOT_FOUND.getReasonPhrase());

        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.NOT_FOUND);
    }



//    @ExceptionHandler(ExpiredJwtException.class)
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    public ResponseEntity<ErrorObject> handleExpiredJwtException(ExpiredJwtException ex) {
//        ErrorObject errorObject = new ErrorObject();
//        errorObject.setError(HttpStatus.FORBIDDEN.getReasonPhrase());
//
//        errorObject.setStatusCode(HttpStatus.FORBIDDEN.value());
//        errorObject.setMessage(ex.getMessage());
//        errorObject.setTimestamp(new Date());
//
//        return new ResponseEntity<>(errorObject, HttpStatus.FORBIDDEN);
//    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorObject> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorObject.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());

        errorObject.setMessage(ex.getBindingResult().getFieldError().getDefaultMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = ExpiredJwtException.class)
    public ResponseEntity<String> handleExpiredJwtException(ExpiredJwtException e) {
        // Customize the message you want to return when the token expires
        String message = "The JWT token has expired. Please log in again.";
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
    }
}
