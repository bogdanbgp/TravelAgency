package com.travelagency.travelagency.exception.user;

public class CannotDeleteUserException extends RuntimeException {
    public CannotDeleteUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotDeleteUserException(String message) {
        super(message);
    }
}