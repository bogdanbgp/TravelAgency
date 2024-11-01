package com.travelagency.travelagency.exception.auth;

public class AuthenticationFailedException extends Exception {
    public AuthenticationFailedException(String message) {
        super(message); // Pass the message to the superclass (Exception)
    }
}
