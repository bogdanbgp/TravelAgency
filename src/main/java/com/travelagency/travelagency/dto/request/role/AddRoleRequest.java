package com.travelagency.travelagency.dto.request.role;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddRoleRequest {

    @NotEmpty(message = "Role name should not be empty")
    private String roleName; // Role name cannot be empty
}
