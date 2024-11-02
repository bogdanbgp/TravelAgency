package com.travelagency.travelagency.dto.request.hotel;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateHotelRequest {

    @NotEmpty(message = "Hotel name should not be empty")
    private String hotelName;

}
