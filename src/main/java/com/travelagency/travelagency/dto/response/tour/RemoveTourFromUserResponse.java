package com.travelagency.travelagency.dto.response.tour;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemoveTourFromUserResponse {
    private Long userId;
    private Long tourId;
    private String message;
}