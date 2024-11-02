package com.travelagency.travelagency.dto.response.hotel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateHotelResponse {
    private Long id;
    private String hotelName;
}
