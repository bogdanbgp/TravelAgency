package com.travelagency.travelagency.service;

import com.travelagency.travelagency.entity.Review;
import com.travelagency.travelagency.entity.Tour;
import com.travelagency.travelagency.entity.User;

import java.util.List;

public interface UserService {
    Long getCurrentUserId();
    User getCurrentUser ();


    Review saveReview(Review review);
    List<Review> getAllReviews();
    boolean deleteReview(Long id);
}
