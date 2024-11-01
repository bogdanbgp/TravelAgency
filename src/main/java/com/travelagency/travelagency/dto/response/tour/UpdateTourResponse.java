package com.travelagency.travelagency.dto.response.tour;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTourResponse {
    private Long id;
    private String tourName;
    private String departureDate;
    private String returnDate;
    private int numberOfDays;
    private String description;
    private double price;
}
