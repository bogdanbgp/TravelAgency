package com.travelagency.travelagency.mapper;

import com.travelagency.travelagency.dto.request.airport.AddAirportRequest;
import com.travelagency.travelagency.dto.response.airport.AirportResponse;
import com.travelagency.travelagency.entity.Airport;
import org.springframework.stereotype.Component;

@Component
public class AirportMapper {

    // Mapping from AddAirportRequest to Airport entity
    public Airport fromRequest(AddAirportRequest addAirportRequest) {
        Airport airport = new Airport();
        airport.setAirportName(addAirportRequest.getAirportName());

        return airport;
    }

    // Mapping from Airport entity to AirportResponse DTO
    public AirportResponse toResponse(Airport airport) {
        String cityName = airport.getCity() != null ? airport.getCity().getCityName() : null; // Get city name if city exists

        return new AirportResponse(
                airport.getId(),                     // Airport ID
                airport.getAirportName(),            // Airport Name
                cityName                              // City Name
        );
    }
}
