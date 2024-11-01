package com.travelagency.travelagency.repository;

import com.travelagency.travelagency.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportRepository extends JpaRepository<Airport, Long> {
    boolean existsByAirportNameAndCityId(String name, Long cityId);
}
