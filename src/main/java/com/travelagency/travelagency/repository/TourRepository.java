package com.travelagency.travelagency.repository;

import com.travelagency.travelagency.entity.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TourRepository extends JpaRepository<Tour, Long> {

    // Find a Tour by its tourName.
    Optional<Tour> findTourByTourName(String tourName);

    // Custom query to find tours by user ID using a many-to-many relationship
    @Query("SELECT t FROM Tour t JOIN t.users u WHERE u.id = :userId")
    List<Tour> findByUserId(@Param("userId") Long userId);
}
