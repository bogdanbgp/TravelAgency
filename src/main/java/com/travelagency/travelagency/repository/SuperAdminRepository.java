package com.travelagency.travelagency.repository;

import com.travelagency.travelagency.entity.SuperAdmin; // Import the SuperAdmin entity
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SuperAdminRepository extends JpaRepository<SuperAdmin, Long> {

    // Find a SuperAdmin by its username
    Optional<SuperAdmin> findSuperAdminByUsername(String username);

    // Check if a SuperAdmin exists by its username
    boolean existsByUsername(String username);
}
