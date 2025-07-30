package com.kce.exception;

public class RequestNotFoundException extends RuntimeException {
    private String message;

    public RequestNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
