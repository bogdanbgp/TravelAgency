package com.travelagency.travelagency.dto.request.hotel;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddHotelRequest {

    @NotEmpty(message = "Hotel name should not be empty")
    private String hotelName;
}
