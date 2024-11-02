package com.travelagency.travelagency.mapper;

import com.travelagency.travelagency.dto.request.auth.LoginRequest;
import com.travelagency.travelagency.dto.response.auth.LoginResponse;
import com.travelagency.travelagency.entity.User;
import org.springframework.stereotype.Component;

@Component
public class LoginMapper {

    // Mapping from LoginRequest to User entity (if needed)
    public User fromRequest(LoginRequest loginRequest) {
        User user = new User();
        user.setUsername(loginRequest.getUsername());
        user.setPassword(loginRequest.getPassword()); // Password handling can be done in the service
        return user;
    }
                // Mapping from User entity to LoginResponse (after successful login)
                public LoginResponse toResponse(User user) {
                    String message = "Login successful"; // Success message
                    return new LoginResponse(message, user.getUsername());
                }
}
