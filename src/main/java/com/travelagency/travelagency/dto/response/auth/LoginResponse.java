package com.travelagency.travelagency.dto.response.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private String message; // Message for login success/failure
    private String username; // Username of the logged-in user
}
