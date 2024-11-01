package com.travelagency.travelagency.mapper;

import com.travelagency.travelagency.dto.request.auth.RegisterRequest;
import com.travelagency.travelagency.dto.response.auth.RegisterResponse;
import com.travelagency.travelagency.entity.User;
import org.springframework.stereotype.Component;

@Component
public class RegisterMapper {
    // Mapping from RegisterRequest to User entity (for registration)
    public User fromRequest(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setAge(registerRequest.getAge());
        user.setEmail(registerRequest.getEmail());
        user.setMobile(registerRequest.getMobile());
        user.setPassword(registerRequest.getPassword()); // Password encoding happens in the service
        return user;
    }

    // Mapping from User entity to RegisterResponse (after successful registration)
    public RegisterResponse toResponse(User user) {
        return new RegisterResponse(
                "Registration successful",    // Success message
                user.getUsername(),          // Username
                user.getFirstName(),
                user.getLastName(),
                user.getAge(),               // Age
                user.getEmail(),             // Email
                user.getMobile()            // Mobile
        );
    }
}
