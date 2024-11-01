package com.travelagency.travelagency.dto.request.tour;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddTourToUserRequest {

    @NotNull(message = "User ID should not be null")
    private Long userId; // User ID of the user to whom the tour will be added

    @NotNull(message = "Tour ID should not be null")
    private Long tourId; // ID of the tour to be added
}
