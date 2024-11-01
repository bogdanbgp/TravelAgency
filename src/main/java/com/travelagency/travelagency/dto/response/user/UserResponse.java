package com.travelagency.travelagency.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;                    // User ID
    private String username;            // Username
    private String firstName;
    private String lastName;
    private int age;                    // Age
    private String email;               // Email
    private String mobile;              // Mobile

    private List<String> roleNames;     // List of role names
    private List<String> tourNames;     // List of tour names
}
