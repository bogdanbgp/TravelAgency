package com.travelagency.travelagency.configuration;

import com.travelagency.travelagency.entity.Role;
import com.travelagency.travelagency.entity.SuperAdmin; // Use the SuperAdmin entity
import com.travelagency.travelagency.repository.RoleRepository;
import com.travelagency.travelagency.repository.SuperAdminRepository; // Import the new repository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

/**
 * SuperAdminInitializer class implements CommandLineRunner to perform initialization tasks
 * when the application starts. It checks for the existence of the SUPER_ADMIN role and a
 * super admin user. If they do not exist, it creates them with default credentials.
 * This ensures that there is always a super admin user with full privileges for
 * managing the application.
 */

//  What It Does:
//  When the Spring application starts, this class checks if a super admin user already exists. If not, it creates one with the username "superadmin" and a password of "superpassword", which is then encoded.
//  This class ensures that there is always a super admin user present in the database when the application is initialized.
@Component
public class SuperAdminInitializer implements CommandLineRunner {

    private final SuperAdminRepository superAdminRepository; // Use the SuperAdminRepository
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;  // Use PasswordEncoder interface

    @Autowired
    public SuperAdminInitializer(SuperAdminRepository superAdminRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.superAdminRepository = superAdminRepository; // Initialize with SuperAdminRepository
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Check if the SUPER_ADMIN role exists, if not, create it
        Optional<Role> superAdminRoleOptional = roleRepository.findRoleByRoleName("SUPER_ADMIN");
        Role superAdminRole;
        if (superAdminRoleOptional.isEmpty()) {
            superAdminRole = new Role();
            superAdminRole.setRoleName("SUPER_ADMIN");
            roleRepository.save(superAdminRole);
        } else {
            superAdminRole = superAdminRoleOptional.get();
        }

        // Check if the super admin user exists, if not, create the user
        Optional<SuperAdmin> superAdminUserOptional = superAdminRepository.findSuperAdminByUsername("superadmin");
        if (superAdminUserOptional.isEmpty()) {
            SuperAdmin superAdminUser = new SuperAdmin(); // Create a new SuperAdmin instance
            superAdminUser.setUsername("superadmin");
            superAdminUser.setPassword(passwordEncoder.encode("superpassword")); // Ensure this is secure
            superAdminUser.setRoles(Collections.singletonList(superAdminRole)); // Assign the SUPER_ADMIN role
            superAdminRepository.save(superAdminUser); // Save using SuperAdminRepository

            System.out.println("Super Admin user created with username: superadmin and full privileges.");
        } else {
            System.out.println("Super Admin user already exists.");
        }
    }
}
