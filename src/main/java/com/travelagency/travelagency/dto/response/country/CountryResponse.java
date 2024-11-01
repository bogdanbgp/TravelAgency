package com.travelagency.travelagency.dto.response.country;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryResponse {

    private Long id;                  // Country ID
    private String countryName;      // Country Name
}
