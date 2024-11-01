package com.travelagency.travelagency.dto.response.tour;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddTourToUserResponse {

    private Long userId;           // ID of the user to whom the tour has been added
    private String userName;      // Name of the user
    private Long tourId;           // ID of the added tour
    private String tourName;       // Name of the tour
    private String message;        // A message indicating success or status
}
