package com.travelagency.travelagency.mapper;

import com.travelagency.travelagency.dto.request.auth.SuperAdminRegisterRequest;
import com.travelagency.travelagency.dto.response.auth.SuperAdminRegisterResponse;
import com.travelagency.travelagency.entity.SuperAdmin; // Import SuperAdmin entity
import org.springframework.stereotype.Component;

@Component
public class SuperAdminRegisterMapper {

    // Converts SuperAdminRegisterRequest to SuperAdmin entity
    public SuperAdmin fromRequest(SuperAdminRegisterRequest superAdminRegisterRequest) {
        SuperAdmin superAdmin = new SuperAdmin(); // Change to SuperAdmin
        superAdmin.setUsername(superAdminRegisterRequest.getUsername());
        superAdmin.setPassword(superAdminRegisterRequest.getPassword()); // Password should be encoded later in the service layer
        return superAdmin; // Return the constructed SuperAdmin entity
    }
                // Mapping from SuperAdmin entity to SuperAdminRegisterResponse
                public SuperAdminRegisterResponse toResponse(SuperAdmin superAdmin) { // Change parameter type to SuperAdmin
                    return new SuperAdminRegisterResponse(
                            "Super Admin registered successfully", // Registration success message
                            superAdmin.getUsername()                        // Registered username
                    );
    }
}
