package com.travelagency.travelagency.service;

import com.travelagency.travelagency.entity.Tour;
import com.travelagency.travelagency.entity.User;

import java.util.List;

public interface UserService {
    Long getCurrentUserId();
    User getCurrentUser ();
    List<Tour> getAllTours(); // Update to accept BookTourRequest
    }
