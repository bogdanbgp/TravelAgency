package com.travelagency.travelagency.service;

import com.travelagency.travelagency.dto.request.airport.AddAirportRequest;
import com.travelagency.travelagency.dto.request.city.AddCityRequest;
import com.travelagency.travelagency.dto.request.country.AddCountryRequest;
import com.travelagency.travelagency.dto.request.hotel.AddHotelRequest;
import com.travelagency.travelagency.dto.request.role.AddRoleRequest;
import com.travelagency.travelagency.dto.request.role.AddRoleToUserRequest;
import com.travelagency.travelagency.dto.request.tour.AddTourRequest;
import com.travelagency.travelagency.dto.request.tour.AddTourToUserRequest;
import com.travelagency.travelagency.dto.request.tour.UpdateTourRequest;
import com.travelagency.travelagency.dto.request.user.AddUserRequest;
import com.travelagency.travelagency.dto.request.user.UpdateUserRequest;
import com.travelagency.travelagency.dto.response.tour.TourResponse;
import com.travelagency.travelagency.dto.response.tour.UpdateTourResponse;
import com.travelagency.travelagency.dto.response.user.UpdateUserResponse;
import com.travelagency.travelagency.dto.response.user.UserResponse;
import com.travelagency.travelagency.entity.*;

import java.util.List;

public interface TravelService {

    // Role-related methods
    Role addRole(AddRoleRequest addRoleRequest);  // Add a new role based on request DTO
    List<Role> findAllRoles();                     // Retrieve a list of all roles
    Role getRoleById(Long id); // GET api         // Get a specific role by its ID
    void deleteRoleById(Long id);                 // Delete a role by its ID

    // Tour-related methods
    TourResponse addTour(AddTourRequest addTourRequest); // Add a new tour using request DTO
    List<Tour> findAllTours();                    // Retrieve a list of all tours
    Tour getTourById(Long id);    // GET api     // Get a specific tour by its ID
    UpdateTourResponse updateTour(Long id, UpdateTourRequest updateTourRequest);
    void deleteTourById(Long id);                // Delete a tour by its ID

    // User-related methods
    UserResponse addUser(AddUserRequest addUserRequest);  // Add a new user using request DTO
    List<UserResponse> findAllUsers();                     // Retrieve a list of all users
    UserResponse getUserById(Long id); // GET api         // GET a specific user by its ID
    UpdateUserResponse updateUser(Long id, UpdateUserRequest updateUserRequest);
    void deleteUserById(Long id);                 // Delete a user by their ID

    // User-role related methods
    User addRoleToUser(AddRoleToUserRequest addRoleToUserRequest);  // Assign a role to a user
        void removeRoleFromUser(Long userId, Long roleId);
    // User-tour related methods
    User addTourToUser(AddTourToUserRequest addTourToUserRequest);  // Assign a tour to a user
        void removeTourFromUser(Long userId, Long tourId);

    // City-related methods
    City addCity(AddCityRequest addCityRequest);    // Add a new city based on request DTO
    List<City> findAllCities();                      // Retrieve a list of all cities
    City getCityById(Long id);  // GET api          // Get a specific city by its ID
    void deleteCityById(Long id);                   // Delete a city by its ID

    // Country-related methods
    Country addCountry(AddCountryRequest addCountryRequest);  // Add a new country using request DTO
    List<Country> findAllCountries();                          // Retrieve a list of all countries
    Country getCountryById(Long id);   // GET api             // Get a specific country by its ID
    void deleteCountryById(Long id);                          // Delete a country by its ID

    // Airport-related methods
    Airport addAirport(AddAirportRequest addAirportRequest);  // Add a new airport based on request DTO
    List<Airport> findAllAirports();                           // Retrieve a list of all airports
    Airport getAirportById(Long id); // GET api               // Get a specific airport by its ID
    void deleteAirportById(Long id);                          // Delete an airport by its ID

    // Hotel-related methods
    Hotel addHotel(AddHotelRequest addHotelRequest);    // Add a new hotel
    List<Hotel> findAllHotels();                        // Retrieve a list of all hotels
    Hotel getHotelById(Long id); // GET api             // Get a specific airport by its ID
    void deleteHotelById(Long id);                      // Delete a hotel by its ID
}