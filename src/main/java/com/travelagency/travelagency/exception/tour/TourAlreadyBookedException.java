package com.travelagency.travelagency.exception.tour;

public class TourAlreadyBookedException extends RuntimeException {
    public TourAlreadyBookedException(String message) {
        super(message);
    }
}
