package com.travelagency.travelagency.repository;

import com.travelagency.travelagency.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    // Method to check if a hotel with the given name already exists
    boolean existsByHotelName(String hotelName);
}
