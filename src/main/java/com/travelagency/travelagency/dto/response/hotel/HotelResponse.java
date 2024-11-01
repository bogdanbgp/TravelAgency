package com.travelagency.travelagency.dto.response.hotel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelResponse {

    private Long id;                  // Hotel ID
    private String hotelName;        // Hotel Name
}
