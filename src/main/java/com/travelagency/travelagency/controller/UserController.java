package com.travelagency.travelagency.controller;

import com.travelagency.travelagency.dto.response.user.UserResponse;
import com.travelagency.travelagency.entity.Review;
import com.travelagency.travelagency.entity.User;
import com.travelagency.travelagency.exception.user.UserNotFoundException;
import com.travelagency.travelagency.mapper.UserMapper;
import com.travelagency.travelagency.repository.UserRepository;
import com.travelagency.travelagency.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final UserMapper userMapper; // Declare UserMapper as a dependency

    @Autowired
    public UserController(UserService userService, UserRepository userRepository, UserMapper userMapper) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.userMapper = userMapper; // Initialize UserMapper
    }


    // ========================================================================================================
    // General Endpoints
    // ========================================================================================================

    // Endpoint to submit a review
    @PostMapping("/review")
    public ResponseEntity<String> submitReview(@RequestBody Review review) {
        if (review.getMessage().isEmpty()) {
            return ResponseEntity.badRequest().body("Message cannot be empty!");
        }

        // Save the review to the database
        userService.saveReview(review);

        // Respond with a success message
        return ResponseEntity.ok("Thank you for your review!");
    }

    // Endpoint to get all reviews
    @GetMapping("/reviews")
    public List<Review> getAllReviews() {
        return userService.getAllReviews();  // Fetch all reviews from the database
    }

    // Endpoint to delete a review by ID
    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable Long id) {
        boolean isDeleted = userService.deleteReview(id);

        if (isDeleted) {
            return ResponseEntity.ok("Review deleted successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Review not found!");
        }
    }

    // ------------------------------------------------------------------------------------------------------

    @GetMapping("/testUserFetch")   // http://localhost:8080/api/user/testUserFetch?username=rabbit
    public ResponseEntity<User> testUserFetch(@RequestParam String username) {
        try {
            User user = userRepository.findUserByUsername(username)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            System.err.println("Error fetching user: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    // ------------------------------------------------------------------------------------------------------

}
