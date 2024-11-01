package com.travelagency.travelagency.exception.user;

// for an admin adding users to the system (addUsed method in Service class)
public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
