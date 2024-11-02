package com.travelagency.travelagency.dto.request.airport;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAirportRequest {

    @NotEmpty(message = "Airport name should not be empty")
    private String airportName;

    @NotNull(message = "City ID should not be null")
    private Long cityId;

}
