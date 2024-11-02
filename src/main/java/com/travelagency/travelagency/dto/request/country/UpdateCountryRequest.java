package com.travelagency.travelagency.dto.request.country;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCountryRequest {

    @NotEmpty(message = "Country name should not be empty")
    private String countryName;

}
