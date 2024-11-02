package com.travelagency.travelagency.mapper;

import com.travelagency.travelagency.dto.request.city.AddCityRequest;
import com.travelagency.travelagency.dto.request.city.UpdateCityRequest;
import com.travelagency.travelagency.dto.response.city.CityResponse;
import com.travelagency.travelagency.dto.response.city.UpdateCityResponse;
import com.travelagency.travelagency.entity.City;
import org.springframework.stereotype.Component;

@Component
public class CityMapper {

    // Mapping from AddCityRequest to City entity
    public City fromRequest(AddCityRequest addCityRequest) {
        City city = new City();
        city.setCityName(addCityRequest.getCityName());
        // Do not set the country here; it's done in the service layer
        return city;
    }
                // Mapping from City entity to CityResponse DTO
                public CityResponse toResponse(City city) {
                    String countryName = city.getCountry() != null ? city.getCountry().getCountryName() : null;
                    return new CityResponse(
                            city.getId(),
                            city.getCityName(),
                            countryName
                    );
                }



    // Mapping from UpdateCityRequest to City entity
    public void fromUpdateRequest(UpdateCityRequest updateCityRequest, City city) {
        if (updateCityRequest.getCityName() != null) {
            city.setCityName(updateCityRequest.getCityName());
        }
        // Do not set the country here; it's done in the service layer
    }
                // Mapping from City entity to UpdateCityResponse DTO
                public UpdateCityResponse toUpdateResponse(City city) {
                    return new UpdateCityResponse(
                            city.getId(),
                            city.getCityName(),
                            city.getCountry() != null ? city.getCountry().getCountryName() : null
                    );
                }

}
