package com.travelagency.travelagency.repository;

import com.travelagency.travelagency.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
    // Method to check if a country with the given name already exists
    boolean existsByCountryName(String countryName);
}
