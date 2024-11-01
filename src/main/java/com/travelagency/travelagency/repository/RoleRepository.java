package com.travelagency.travelagency.repository;

import com.travelagency.travelagency.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

// Find a Role by its name.
// Used in TravelServiceImplementation for addRole method (making sure it doesn't already exist)
    Optional<Role> findRoleByRoleName(String roleName);

}
