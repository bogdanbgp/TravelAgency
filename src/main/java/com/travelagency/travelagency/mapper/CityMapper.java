package com.travelagency.travelagency.mapper;

import com.travelagency.travelagency.dto.request.city.AddCityRequest;
import com.travelagency.travelagency.dto.response.city.CityResponse;
import com.travelagency.travelagency.entity.City;
import org.springframework.stereotype.Component;

@Component
public class CityMapper {

    // Mapping from AddCityRequest to City entity
    public City fromRequest(AddCityRequest addCityRequest) {
        City city = new City();
        city.setCityName(addCityRequest.getCityName());

        return city;
    }

    // Mapping from City entity to CityResponse DTO
    public CityResponse toResponse(City city) {
        String countryName = city.getCountry() != null ? city.getCountry().getCountryName() : null; // Get country name if country exists

        return new CityResponse(
                city.getId(),                     // City ID
                city.getCityName(),               // City Name
                countryName                        // Country Name
        );
    }
}
