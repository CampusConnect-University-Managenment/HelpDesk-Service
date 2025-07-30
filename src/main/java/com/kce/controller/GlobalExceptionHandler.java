package com.kce.controller;

import com.kce.exception.RequestNotFoundException;
import com.kce.modal.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

public class GlobalExceptionHandler {
    // Handle all other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex, WebRequest request) {
        ErrorDetails error = new ErrorDetails(
                new Date(),
                String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle RequestNotFoundException
    @ExceptionHandler(RequestNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleRequestNotFoundException(RequestNotFoundException ex, WebRequest request) {
        ErrorDetails error = new ErrorDetails(
                new Date(),
                String.valueOf(HttpStatus.NOT_FOUND.value()),
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
