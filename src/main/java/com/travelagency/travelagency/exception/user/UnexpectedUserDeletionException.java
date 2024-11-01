package com.travelagency.travelagency.exception.user;

public class UnexpectedUserDeletionException extends RuntimeException {
    public UnexpectedUserDeletionException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnexpectedUserDeletionException(String message) {
        super(message);
    }
}