package com.plantationhub.wesesta.authentication.exception;

public class AppRuntimeException extends RuntimeException{
    private final String message;

    public AppRuntimeException(String message) {
        super(message);
        this.message = message;
    }
    @Override
    public String getMessage() {
        return message;
    }
}
