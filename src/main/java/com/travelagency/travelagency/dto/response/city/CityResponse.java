package com.travelagency.travelagency.dto.response.city;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityResponse {
    private Long id;                  // City ID
    private String cityName;         // City Name
    private String countryName;      // show country where the city is
}
