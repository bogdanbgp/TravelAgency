package com.travelagency.travelagency.mapper;

import com.travelagency.travelagency.dto.request.role.AddRoleRequest;
import com.travelagency.travelagency.dto.request.role.UpdateRoleRequest;
import com.travelagency.travelagency.dto.response.role.RoleResponse;
import com.travelagency.travelagency.dto.response.role.UpdateRoleResponse;
import com.travelagency.travelagency.entity.Role;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoleMapper {

    // Mapping from AddRoleRequest to Role entity
    public Role fromRequest(AddRoleRequest addRoleRequest) {
        Role role = new Role();
        role.setRoleName(addRoleRequest.getRoleName());
        return role;
    }
                // Mapping from Role entity to RoleResponse DTO
                public RoleResponse toResponse(Role role) {
                    return new RoleResponse(
                            role.getId(),
                            role.getRoleName()
                    );
                }

    // Mapping from UpdateRoleRequest to Role entity
    public void fromUpdateRequest(UpdateRoleRequest updateRoleRequest, Role role) {
        role.setRoleName(updateRoleRequest.getRoleName());
        // Update other fields as necessary
    }
                // Mapping from Role entity to UpdateRoleResponse DTO
                public UpdateRoleResponse toUpdateResponse(Role role) {
                    return new UpdateRoleResponse(
                            role.getId(),
                            role.getRoleName()
                    );
                }
}
