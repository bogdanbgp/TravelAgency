package com.travelagency.travelagency.dto.request.role;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRoleRequest {

    @NotEmpty(message = "Role name should not be empty")
    private String roleName;

}
