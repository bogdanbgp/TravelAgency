package com.travelagency.travelagency.dto.request.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {
    private String username;
    private String firstName;
    private String lastName;
    private Integer age;
    private String email;
    private String mobile;
    private String password; // Include password to allow updating
}
