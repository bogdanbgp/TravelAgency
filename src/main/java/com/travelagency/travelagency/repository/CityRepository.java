package com.travelagency.travelagency.repository;

import com.travelagency.travelagency.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
    boolean existsByCityNameAndCountryId(String cityName, Long countryId);
}
