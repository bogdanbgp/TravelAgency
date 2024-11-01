package com.travelagency.travelagency.mapper;

import com.travelagency.travelagency.dto.request.hotel.AddHotelRequest;
import com.travelagency.travelagency.dto.response.hotel.HotelResponse;
import com.travelagency.travelagency.entity.Hotel;
import org.springframework.stereotype.Component;

@Component
public class HotelMapper {

    // Mapping from AddHotelRequest to Hotel entity
    public Hotel fromRequest(AddHotelRequest addHotelRequest) {
        Hotel hotel = new Hotel();
        hotel.setHotelName(addHotelRequest.getHotelName());

        return hotel;
    }

    // Mapping from Hotel entity to HotelResponse DTO
    public HotelResponse toResponse(Hotel hotel) {
        return new HotelResponse(
                hotel.getId(),                     // Hotel ID
                hotel.getHotelName()               // Hotel Name
        );
    }
}
