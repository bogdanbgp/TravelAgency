package com.travelagency.travelagency.dto.response.airport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAirportResponse {
    private Long id;
    private String airportName;
    private String cityName; // Include city name if needed
}
