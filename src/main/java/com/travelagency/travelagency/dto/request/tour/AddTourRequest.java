package com.travelagency.travelagency.dto.request.tour;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddTourRequest {

    private Long id; // This may be set by the database

    @NotEmpty(message = "Tour name should not be empty")
    private String tourName;

    @NotNull(message = "From city ID should not be null")
    private Long fromCityId;

    @NotNull(message = "To city ID should not be null")
    private Long toCityId;

    @NotNull(message = "From country ID should not be null")
    private Long fromCountryId;

    @NotNull(message = "To country ID should not be null")
    private Long toCountryId;

    @NotNull(message = "From airport ID should not be null")
    private Long fromAirportId;

    @NotNull(message = "To airport ID should not be null")
    private Long toAirportId;

    private Long hotelId; // Hotel is optional

    @NotEmpty(message = "Departure date should not be empty")
    private String departureDate;

    @NotEmpty(message = "Return date should not be empty")
    private String returnDate;

    @NotNull(message = "Number of days should not be null")
    private int numberOfDays;

    private String description; // Optional

    @NotNull(message = "Price should not be null")
    private double price;
}