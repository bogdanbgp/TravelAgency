package com.travelagency.travelagency.mapper;

import com.travelagency.travelagency.dto.request.country.AddCountryRequest;
import com.travelagency.travelagency.dto.request.country.UpdateCountryRequest;
import com.travelagency.travelagency.dto.response.country.CountryResponse;
import com.travelagency.travelagency.dto.response.country.UpdateCountryResponse;
import com.travelagency.travelagency.entity.Country;
import org.springframework.stereotype.Component;

@Component
public class CountryMapper {

    // Mapping from AddCountryRequest to Country entity
    public Country fromRequest(AddCountryRequest addCountryRequest) {
        Country country = new Country();
        country.setCountryName(addCountryRequest.getCountryName());
        return country;
    }
                // Mapping from Country entity to CountryResponse DTO
                public CountryResponse toResponse(Country country) {
                    return new CountryResponse(
                            country.getId(),
                            country.getCountryName()
                    );
                }




    // Mapping from UpdateCountryRequest to Country entity
    public void fromUpdateRequest(UpdateCountryRequest updateCountryRequest, Country country) {
        country.setCountryName(updateCountryRequest.getCountryName());
        // Update other fields as necessary
    }
                // Mapping from Country entity to UpdateCountryResponse DTO
                public UpdateCountryResponse toUpdateResponse(Country country) {
                    return new UpdateCountryResponse(
                            country.getId(),
                            country.getCountryName()
                            // Include other fields as necessary
                    );
                }
}
