package com.travelagency.travelagency.dto.response.role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddRoleToUserResponse {

    private Long userId;           // ID of the user to whom the role has been added
    private String userName;      // Name of the user
    private Long roleId;          // ID of the added role
    private String roleName;      // Name of the role
    private String message;       // A message indicating success or status
}
