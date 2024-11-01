package com.travelagency.travelagency.configuration;

import com.travelagency.travelagency.entity.Role;
import com.travelagency.travelagency.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * UserRoleInitializer ensures that the "USER" role exists in the database when the application starts.
 */
@Component
public class UserRoleInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Autowired
    public UserRoleInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Ensure USER role exists
        Optional<Role> userRoleOptional = roleRepository.findRoleByRoleName("USER");
        if (userRoleOptional.isEmpty()) {
            Role userRole = new Role();
            userRole.setRoleName("USER");
            roleRepository.save(userRole);
            System.out.println("USER role created.");
        } else {
            System.out.println("USER role already exists.");
        }
    }
}
