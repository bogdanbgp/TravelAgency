package com.travelagency.travelagency.mapper;

import com.travelagency.travelagency.dto.request.auth.SuperAdminLoginRequest;
import com.travelagency.travelagency.dto.response.auth.SuperAdminLoginResponse;
import com.travelagency.travelagency.entity.SuperAdmin;
import org.springframework.stereotype.Component;

@Component
public class SuperAdminLoginMapper {

    // Mapping from SuperAdminLoginRequest to SuperAdmin entity (if needed)
    public SuperAdmin fromRequest(SuperAdminLoginRequest loginRequest) {
        SuperAdmin superAdmin = new SuperAdmin();
        superAdmin.setUsername(loginRequest.getUsername());
        superAdmin.setPassword(loginRequest.getPassword()); // Password handling (encryption) will be done in the service
        return superAdmin;
    }
                // Mapping from SuperAdmin entity to SuperAdminLoginResponse (after successful login)
                public SuperAdminLoginResponse toResponse(SuperAdmin superAdmin) {
                    String message = "SuperAdmin login successful"; // Success message
                    return new SuperAdminLoginResponse(message, superAdmin.getUsername());
                }
            }
