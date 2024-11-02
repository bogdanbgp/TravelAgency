package com.travelagency.travelagency.dto.response.role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRoleResponse {
    private Long id;
    private String roleName;
}
