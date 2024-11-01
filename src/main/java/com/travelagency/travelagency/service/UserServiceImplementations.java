package com.travelagency.travelagency.service;

import com.travelagency.travelagency.entity.User;
import com.travelagency.travelagency.entity.Tour;
import com.travelagency.travelagency.exception.user.UserNotAuthenticatedException;
import com.travelagency.travelagency.exception.user.UserNotFoundException;
import com.travelagency.travelagency.repository.UserRepository;
import com.travelagency.travelagency.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImplementations implements UserService {

    private final UserRepository userRepository;
    private final TourRepository tourRepository;

    @Autowired
    public UserServiceImplementations(UserRepository userRepository,
                                      TourRepository tourRepository) {
        this.userRepository = userRepository;
        this.tourRepository = tourRepository;
    }

    // -----------------------------------------------------------------------------------------------
    // Utility method to retrieve the currently authenticated user's ID
    @Override
    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userRepository.findUserByUsername(userDetails.getUsername())
                    .map(User::getId)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));
        }
        throw new UserNotAuthenticatedException("User is not authenticated");
    }

    // Retrieve the currently authenticated user
    @Override
    public User getCurrentUser() {
        Long userId = getCurrentUserId(); // Retrieve authenticated user's ID
        // Check if the userId is null or invalid
        if (userId == null) {
            throw new UserNotAuthenticatedException("User is not authenticated");
        }

        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    // Retrieve all available tours
    @Override
    public List<Tour> getAllTours() {
        return tourRepository.findAll();
    }
}