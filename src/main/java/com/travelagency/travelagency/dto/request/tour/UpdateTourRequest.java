package com.travelagency.travelagency.dto.request.tour;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTourRequest {

    private String tourName;

    @NotNull(message = "Departure date should not be null")
    private LocalDate departureDate; // Using LocalDate

    @NotNull(message = "Return date should not be null")
    private LocalDate returnDate; // Using LocalDate

    @NotNull(message = "Number of days should not be null")
    private int numberOfDays;

    private String description;

    @NotNull(message = "Price should not be null")
    private double price;

    @NotNull(message = "From country ID should not be null")
    private Long fromCountryId;

    @NotNull(message = "To country ID should not be null")
    private Long toCountryId;

    @NotNull(message = "From city ID should not be null")
    private Long fromCityId;

    @NotNull(message = "To city ID should not be null")
    private Long toCityId;

    @NotNull(message = "From airport ID should not be null")
    private Long fromAirportId;

    @NotNull(message = "To airport ID should not be null")
    private Long toAirportId;

    private Long hotelId;
}
