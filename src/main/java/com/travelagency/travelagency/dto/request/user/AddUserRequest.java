package com.travelagency.travelagency.dto.request.user;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddUserRequest {

    @NotEmpty(message = "Username should not be empty")
    private String username;

    @NotEmpty(message = "First name is required") // Ensures first name is not blank
    private String firstName; // User's first name

    @NotEmpty(message = "Last name is required") // Ensures last name is not blank
    private String lastName; // User's last name

    @Min(value = 18, message = "Age must be at least 18")
    private int age;

    @Email
    @NotEmpty(message = "Email should not be empty")
    private String email;

    @Pattern(regexp = "\\d{10}", message = "Mobile number must be 10 digits") // regex for 10-digit phone number
    private String mobile;

    @NotEmpty(message = "Password should not be empty")
    private String password;

    private List<Long> roleIds; // Optional list of role IDs
    private List<Long> tourIds; // Optional list of Tour IDs
}
