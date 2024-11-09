package com.travelagency.travelagency.dto.response.city;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCityResponse {
    private Long id;
    private String cityName;
    private String countryName;
}
