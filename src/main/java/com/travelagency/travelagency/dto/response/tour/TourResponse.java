package com.travelagency.travelagency.dto.response.tour;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourResponse {
    private Long id;
    private String tourName;

    private LocalDate departureDate; // Changed to LocalDate
    private LocalDate returnDate; // Changed to LocalDate

    private int numberOfDays;

    private String description;

    private double price;

    private String fromCountryName;
    private String toCountryName;

    private String fromCityName;
    private String toCityName;

    private String fromAirportName;
    private String toAirportName;

    private String hotelName;


}
