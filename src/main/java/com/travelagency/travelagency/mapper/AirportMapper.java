package com.travelagency.travelagency.mapper;

import com.travelagency.travelagency.dto.request.airport.AddAirportRequest;
import com.travelagency.travelagency.dto.request.airport.UpdateAirportRequest;
import com.travelagency.travelagency.dto.response.airport.AirportResponse;
import com.travelagency.travelagency.dto.response.airport.UpdateAirportResponse;
import com.travelagency.travelagency.entity.Airport;
import org.springframework.stereotype.Component;

@Component
public class AirportMapper {

    // Mapping from AddAirportRequest to Airport entity
    public Airport fromRequest(AddAirportRequest addAirportRequest) {
        Airport airport = new Airport();
        airport.setAirportName(addAirportRequest.getAirportName());
        // Do not set the city here; it's done in the service layer
        return airport;
    }
                // Mapping from Airport entity to AirportResponse DTO
                public AirportResponse toResponse(Airport airport) {
                    String cityName = airport.getCity() != null ? airport.getCity().getCityName() : null;
                    return new AirportResponse(
                            airport.getId(),
                            airport.getAirportName(),
                            cityName
                    );
                }



    // Mapping from UpdateAirportRequest to Airport entity
    public void fromUpdateRequest(UpdateAirportRequest updateAirportRequest, Airport airport) {
        if (updateAirportRequest.getAirportName() != null) {
            airport.setAirportName(updateAirportRequest.getAirportName());
        }
        // Do not set the city here; it's done in the service layer
    }
                // Mapping from Airport entity to UpdateAirportResponse DTO
                public UpdateAirportResponse toUpdateResponse(Airport airport) {
                    return new UpdateAirportResponse(
                            airport.getId(),
                            airport.getAirportName(),
                            airport.getCity() != null ? airport.getCity().getCityName() : null
                    );
                }
}
