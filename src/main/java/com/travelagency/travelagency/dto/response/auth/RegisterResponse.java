package com.travelagency.travelagency.dto.response.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse {

    private String message;   // Registration success/failure message
    private String username;  // Registered username
    private String firstName;
    private String lastName;
    private int age;          // Registered age
    private String email;     // Registered email
    private String mobile;    // Registered mobile number
}
