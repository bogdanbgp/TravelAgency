package com.travelagency.travelagency.dto.response.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuperAdminRegisterResponse {

    private String message;   // Registration success/failure message
    private String username;  // Registered username
}
