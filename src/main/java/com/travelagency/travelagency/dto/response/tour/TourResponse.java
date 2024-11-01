package com.travelagency.travelagency.dto.response.tour;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourResponse {
    private Long id;
    private String tourName;

    private String fromCountryName;
    private String toCountryName;

    private String fromCityName;
    private String toCityName;

    private String fromAirportName;
    private String toAirportName;

    private String hotelName;

    private String departureDate;
    private String returnDate;

    private int numberOfDays;

    private String description;

    private double price;
}
