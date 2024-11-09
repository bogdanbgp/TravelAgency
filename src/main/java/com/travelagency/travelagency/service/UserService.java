package com.travelagency.travelagency.service;

import com.travelagency.travelagency.entity.Review;
import com.travelagency.travelagency.entity.User;

import java.util.List;

public interface UserService {
// -----------------------------------------------------------------------------------------------

    Review saveReview(Review review);
    List<Review> getAllReviews();
    boolean deleteReview(Long id);

// -----------------------------------------------------------------------------------------------

//    Long getCurrentUserId();
//    User getCurrentUser ();

// -----------------------------------------------------------------------------------------------


}
