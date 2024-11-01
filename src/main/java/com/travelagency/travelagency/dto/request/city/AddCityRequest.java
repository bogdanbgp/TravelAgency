package com.travelagency.travelagency.dto.request.city;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddCityRequest {
    @NotEmpty(message = "City name should not be empty")
    private String cityName;

    @NotNull(message = "Country ID should not be null")
    private Long countryId;  // Reference to the Country associated with this city
}
