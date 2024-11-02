package com.travelagency.travelagency.dto.response.country;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCountryResponse {
    private Long id;
    private String countryName;
}
