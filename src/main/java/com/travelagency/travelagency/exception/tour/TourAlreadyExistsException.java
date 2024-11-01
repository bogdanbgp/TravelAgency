package com.travelagency.travelagency.exception.tour;

public class TourAlreadyExistsException extends RuntimeException {
    public TourAlreadyExistsException(String message) {
        super(message);
    }
}
