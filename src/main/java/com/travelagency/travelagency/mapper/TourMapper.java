package com.travelagency.travelagency.mapper;

import com.travelagency.travelagency.dto.request.tour.AddTourRequest;
import com.travelagency.travelagency.dto.request.tour.UpdateTourRequest;
import com.travelagency.travelagency.dto.response.tour.TourResponse;
import com.travelagency.travelagency.dto.response.tour.UpdateTourResponse;
import com.travelagency.travelagency.entity.Tour;
import com.travelagency.travelagency.repository.*;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class TourMapper {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private HotelRepository hotelRepository;

    // Mapping from AddTourRequest to Tour entity
    public Tour fromRequest(AddTourRequest addTourRequest) {
        Tour tour = new Tour();

        // Set scalar fields
        tour.setTourName(addTourRequest.getTourName());
        tour.setDepartureDate(addTourRequest.getDepartureDate());
        tour.setReturnDate(addTourRequest.getReturnDate());
        tour.setNumberOfDays(addTourRequest.getNumberOfDays());
        tour.setDescription(addTourRequest.getDescription());
        tour.setPrice(addTourRequest.getPrice());
        tour.setFromCountry(countryRepository.findById(addTourRequest.getFromCountryId()).orElse(null));
        tour.setToCountry(countryRepository.findById(addTourRequest.getToCountryId()).orElse(null));
        tour.setFromCity(cityRepository.findById(addTourRequest.getFromCityId()).orElse(null));
        tour.setToCity(cityRepository.findById(addTourRequest.getToCityId()).orElse(null));
        tour.setFromAirport(airportRepository.findById(addTourRequest.getFromAirportId()).orElse(null));
        tour.setToAirport(airportRepository.findById(addTourRequest.getToAirportId()).orElse(null));
        tour.setToHotel(hotelRepository.findById(addTourRequest.getHotelId()).orElse(null));

        return tour;
    }
                // Mapping from Tour entity to TourResponse DTO
                public TourResponse toResponse(Tour tour) {
                    return new TourResponse(
                            tour.getId(),
                            tour.getTourName(),
                            tour.getDepartureDate(),
                            tour.getReturnDate(),
                            tour.getNumberOfDays(),
                            tour.getDescription(),
                            tour.getPrice(),
                            tour.getFromCountry() != null ? tour.getFromCountry().getCountryName() : null,
                            tour.getToCountry() != null ? tour.getToCountry().getCountryName() : null,
                            tour.getFromCity() != null ? tour.getFromCity().getCityName() : null,
                            tour.getToCity() != null ? tour.getToCity().getCityName() : null,
                            tour.getFromAirport() != null ? tour.getFromAirport().getAirportName() : null,
                            tour.getToAirport() != null ? tour.getToAirport().getAirportName() : null,
                            tour.getToHotel() != null ? tour.getToHotel().getHotelName() : null
                    );
                }




    // Mapping from UpdateTourRequest to Tour entity
    public void fromUpdateRequest(UpdateTourRequest updateTourRequest, Tour existingTour) {
        existingTour.setTourName(updateTourRequest.getTourName());
        existingTour.setDepartureDate(updateTourRequest.getDepartureDate());
        existingTour.setReturnDate(updateTourRequest.getReturnDate());
        existingTour.setNumberOfDays(updateTourRequest.getNumberOfDays());
        existingTour.setDescription(updateTourRequest.getDescription());
        existingTour.setPrice(updateTourRequest.getPrice());
        existingTour.setFromCountry(countryRepository.findById(updateTourRequest.getFromCountryId()).orElse(null));
        existingTour.setToCountry(countryRepository.findById(updateTourRequest.getToCountryId()).orElse(null));
        existingTour.setFromCity(cityRepository.findById(updateTourRequest.getFromCityId()).orElse(null));
        existingTour.setToCity(cityRepository.findById(updateTourRequest.getToCityId()).orElse(null));
        existingTour.setFromAirport(airportRepository.findById(updateTourRequest.getFromAirportId()).orElse(null));
        existingTour.setToAirport(airportRepository.findById(updateTourRequest.getToAirportId()).orElse(null));
        existingTour.setToHotel(hotelRepository.findById(updateTourRequest.getHotelId()).orElse(null));
    }
                // Mapping from Tour entity to UpdateTourResponse DTO
                public UpdateTourResponse toUpdateResponse(Tour tour) {
                    return new UpdateTourResponse(
                            tour.getId(),
                            tour.getTourName(),
                            tour.getDepartureDate(),
                            tour.getReturnDate(),
                            tour.getNumberOfDays(),
                            tour.getDescription(),
                            tour.getPrice(),
                            tour.getFromCountry() != null ? tour.getFromCountry().getCountryName() : null,
                            tour.getToCountry() != null ? tour.getToCountry().getCountryName() : null,
                            tour.getFromCity() != null ? tour.getFromCity().getCityName() : null,
                            tour.getToCity() != null ? tour.getToCity().getCityName() : null,
                            tour.getFromAirport() != null ? tour.getFromAirport().getAirportName() : null,
                            tour.getToAirport() != null ? tour.getToAirport().getAirportName() : null,
                            tour.getToHotel() != null ? tour.getToHotel().getHotelName() : null
                    );
                }
}
