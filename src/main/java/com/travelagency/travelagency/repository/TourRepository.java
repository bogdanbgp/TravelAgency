package com.travelagency.travelagency.repository;

import com.travelagency.travelagency.entity.Tour;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TourRepository extends JpaRepository<Tour, Long> {

// Find a Tour by its tourName.
// Used in TravelServiceImplementation to add new Tour (making sure it doesn't already exist)
    Optional<Tour> findTourByTourName(String tourName);
}
