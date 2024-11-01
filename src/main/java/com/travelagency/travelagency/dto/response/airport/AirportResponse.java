package com.travelagency.travelagency.dto.response.airport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AirportResponse {

    private Long id;                  // Airport ID
    private String airportName;      // Airport Name
    private String cityName;         // show city where the airport is
}
