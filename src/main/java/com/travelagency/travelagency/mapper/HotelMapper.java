package com.travelagency.travelagency.mapper;

import com.travelagency.travelagency.dto.request.hotel.AddHotelRequest;
import com.travelagency.travelagency.dto.request.hotel.UpdateHotelRequest;
import com.travelagency.travelagency.dto.response.hotel.HotelResponse;
import com.travelagency.travelagency.dto.response.hotel.UpdateHotelResponse;
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
                            hotel.getId(),
                            hotel.getHotelName()
                    );
                }



    // Mapping from UpdateHotelRequest to Hotel entity
    public void fromUpdateRequest(UpdateHotelRequest updateHotelRequest, Hotel hotel) {
        hotel.setHotelName(updateHotelRequest.getHotelName());
        // Update other fields as necessary
    }
                // Mapping from Hotel entity to UpdateHotelResponse DTO
                public UpdateHotelResponse toUpdateResponse(Hotel hotel) {
                    return new UpdateHotelResponse(
                            hotel.getId(),
                            hotel.getHotelName()
                            // Include other fields as necessary
                    );
                }
}
