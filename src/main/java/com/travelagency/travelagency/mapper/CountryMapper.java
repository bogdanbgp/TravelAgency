package com.travelagency.travelagency.mapper;

import com.travelagency.travelagency.dto.request.country.AddCountryRequest;
import com.travelagency.travelagency.dto.response.country.CountryResponse;
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
                country.getId(),                     // Country ID
                country.getCountryName()             // Country Name
        );
    }
}
