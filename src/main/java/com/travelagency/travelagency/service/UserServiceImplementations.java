package com.travelagency.travelagency.service;

import com.travelagency.travelagency.entity.Review;
import com.travelagency.travelagency.entity.User;
import com.travelagency.travelagency.entity.Tour;
import com.travelagency.travelagency.exception.user.UserNotAuthenticatedException;
import com.travelagency.travelagency.exception.user.UserNotFoundException;
import com.travelagency.travelagency.repository.ReviewRepository;
import com.travelagency.travelagency.repository.UserRepository;
import com.travelagency.travelagency.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementations implements UserService {

    private final UserRepository userRepository;
    private final TourRepository tourRepository;
    private final ReviewRepository reviewRepository;

    @Autowired
    public UserServiceImplementations(UserRepository userRepository,
                                      TourRepository tourRepository,
                                      ReviewRepository reviewRepository) {
        this.userRepository = userRepository;
        this.tourRepository = tourRepository;
        this.reviewRepository = reviewRepository;
    }


    //-----------------------------------------------------------------------------------------------------
    // REVIEW METHODS:

    // Save a review to the database
    @Override
    public Review saveReview(Review review) {
        return reviewRepository.save(review);  // Save the review to the database
    }

    // Get all reviews from the database
    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();  // Fetch all reviews
    }

    // Delete a review by ID
    @Override
    public boolean deleteReview(Long id) {
        Optional<Review> review = reviewRepository.findById(id);
        if (review.isPresent()) {
            reviewRepository.delete(review.get());  // Delete the review from the database
            return true;  // Review successfully deleted
        }
        return false;  // Review not found
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

}