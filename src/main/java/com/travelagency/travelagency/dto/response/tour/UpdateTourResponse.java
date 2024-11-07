package com.travelagency.travelagency.dto.response.tour;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTourResponse {
    private Long id;
    private String tourName;
    private LocalDate departureDate; // LocalDate
    private LocalDate returnDate; // LocalDate
    private String description;
    private double price;
    private String fromCountry;
    private String toCountry;
    private String fromCity;
    private String toCity;
    private String fromAirport;
    private String toAirport;
    private String toHotel;
}
