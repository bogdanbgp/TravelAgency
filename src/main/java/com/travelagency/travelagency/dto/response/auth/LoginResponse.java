package com.travelagency.travelagency.dto.response.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private Long id; // User ID
    private String message; // Message for login success/failure
    private String username; // Username of the logged-in user

    public LoginResponse(String message) {
        this.message = message;
        this.id = null; // Default to null for error responses
        this.username = null; // Default to null for error responses
    }
}
