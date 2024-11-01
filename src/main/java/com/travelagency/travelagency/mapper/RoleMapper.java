package com.travelagency.travelagency.mapper;

import com.travelagency.travelagency.dto.request.role.AddRoleRequest;
import com.travelagency.travelagency.dto.response.role.RoleResponse;
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
        role.setUsers(new ArrayList<>());

        return role;
    }

    // Mapping from Role entity to RoleResponse DTO
    public RoleResponse toResponse(Role role) {
        List<String> usernames = role.getUsers().stream()
                .map(user -> user.getUsername())
                .collect(Collectors.toList());

        return new RoleResponse(
                role.getId(),                     // Role ID
                role.getRoleName()                // Role Name
        );
    }
}
