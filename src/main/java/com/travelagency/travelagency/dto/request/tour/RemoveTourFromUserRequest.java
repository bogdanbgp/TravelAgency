package com.travelagency.travelagency.dto.request.tour;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemoveTourFromUserRequest {
    @NotNull(message = "User ID should not be null")
    private Long userId;

    @NotNull(message = "Tour ID should not be null")
    private Long tourId;
}