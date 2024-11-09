package com.travelagency.travelagency.controller;

import com.travelagency.travelagency.dto.request.airport.AddAirportRequest;
import com.travelagency.travelagency.dto.request.airport.UpdateAirportRequest;
import com.travelagency.travelagency.dto.response.airport.AirportResponse;
import com.travelagency.travelagency.dto.response.airport.UpdateAirportResponse;
import com.travelagency.travelagency.dto.request.city.AddCityRequest;
import com.travelagency.travelagency.dto.request.city.UpdateCityRequest;
import com.travelagency.travelagency.dto.response.city.CityResponse;
import com.travelagency.travelagency.dto.response.city.UpdateCityResponse;
import com.travelagency.travelagency.dto.request.country.AddCountryRequest;
import com.travelagency.travelagency.dto.request.country.UpdateCountryRequest;
import com.travelagency.travelagency.dto.response.country.CountryResponse;
import com.travelagency.travelagency.dto.response.country.UpdateCountryResponse;
import com.travelagency.travelagency.dto.request.hotel.AddHotelRequest;
import com.travelagency.travelagency.dto.request.hotel.UpdateHotelRequest;
import com.travelagency.travelagency.dto.response.hotel.HotelResponse;
import com.travelagency.travelagency.dto.response.hotel.UpdateHotelResponse;
import com.travelagency.travelagency.dto.request.role.AddRoleRequest;
import com.travelagency.travelagency.dto.request.role.UpdateRoleRequest;
import com.travelagency.travelagency.dto.response.role.RoleResponse;
import com.travelagency.travelagency.dto.response.role.UpdateRoleResponse;
import com.travelagency.travelagency.dto.request.tour.AddTourRequest;
import com.travelagency.travelagency.dto.request.tour.UpdateTourRequest;
import com.travelagency.travelagency.dto.response.tour.AddTourToUserResponse;
import com.travelagency.travelagency.dto.response.tour.TourResponse;
import com.travelagency.travelagency.dto.response.tour.UpdateTourResponse;
import com.travelagency.travelagency.dto.request.user.AddUserRequest;
import com.travelagency.travelagency.dto.request.user.UpdateUserRequest;
import com.travelagency.travelagency.dto.response.user.UserResponse;
import com.travelagency.travelagency.dto.response.user.UpdateUserResponse;
import com.travelagency.travelagency.entity.Role;
import com.travelagency.travelagency.exception.role.RoleNotFoundException;
import com.travelagency.travelagency.exception.tour.*;
import com.travelagency.travelagency.exception.user.UserNotFoundException;
import com.travelagency.travelagency.mapper.RoleMapper;
import com.travelagency.travelagency.mapper.TourMapper;
import com.travelagency.travelagency.mapper.UserMapper;
import com.travelagency.travelagency.mapper.AirportMapper;
import com.travelagency.travelagency.mapper.CountryMapper;
import com.travelagency.travelagency.mapper.CityMapper;
import com.travelagency.travelagency.mapper.HotelMapper;
import com.travelagency.travelagency.service.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/")
public class TravelController {

    private final TravelService service;
    private final UserMapper userMapper;
    private final TourMapper tourMapper;
    private final RoleMapper roleMapper;
    private final AirportMapper airportMapper;
    private final CountryMapper countryMapper;
    private final CityMapper cityMapper;
    private final HotelMapper hotelMapper;

    @Autowired
    public TravelController(TravelService service, UserMapper userMapper, TourMapper tourMapper,
                            RoleMapper roleMapper, AirportMapper airportMapper,
                            CountryMapper countryMapper, CityMapper cityMapper,
                            HotelMapper hotelMapper) {
        this.service = service;
        this.userMapper = userMapper;
        this.tourMapper = tourMapper;
        this.roleMapper = roleMapper;
        this.airportMapper = airportMapper;
        this.countryMapper = countryMapper;
        this.cityMapper = cityMapper;
        this.hotelMapper = hotelMapper;
    }

    // ========================================================================================================
    // General Endpoints
    // ========================================================================================================

    @GetMapping("/")  // Mapping for root URL
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Welcome to the Travel Agency!");
    }

    @GetMapping("/bye")  // Define a GET endpoint
    public ResponseEntity<String> bye() {
        return ResponseEntity.ok("Bye!");
    }

    // ========================================================================================================
    // Role Endpoints
    // ========================================================================================================

    @GetMapping("/roles")
    public ResponseEntity<List<RoleResponse>> getAllRoles() {
        List<Role> roles = service.findAllRoles();
        List<RoleResponse> roleResponses = roles.stream()
                .map(roleMapper::toResponse)
                .toList();
        return ResponseEntity.ok(roleResponses); // Returns 200 OK with the list of roles
    }

    @PostMapping("/roles")
    public ResponseEntity<RoleResponse> createRole(@RequestBody AddRoleRequest addRoleRequest) {
        RoleResponse roleResponse = service.addRole(addRoleRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(roleResponse); // Returns 201 Created with the new role
    }

    @PutMapping("/roles/{id}")
    public ResponseEntity<UpdateRoleResponse> updateRole(@PathVariable Long id, @RequestBody UpdateRoleRequest updateRoleRequest) {
        UpdateRoleResponse roleResponse = service.updateRole(id, updateRoleRequest);
        return ResponseEntity.ok(roleResponse); // Returns 200 OK with the updated role
    }

    @DeleteMapping("/roles/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        try {
            service.deleteRoleById(id);
            return ResponseEntity.noContent().build(); // Returns 204 No Content after successful deletion
        } catch (RoleNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Returns 404 Not Found
        }
    }

    // ========================================================================================================
    // Tour Endpoints
    // ========================================================================================================

    @GetMapping("/tours")
    public ResponseEntity<List<TourResponse>> getAllTours() {
        List<TourResponse> tourResponses = service.findAllTours()
                .stream()
                .map(tourMapper::toResponse)
                .toList();
        return ResponseEntity.ok(tourResponses);
    }

    @PostMapping("/tours")
    public ResponseEntity<TourResponse> createTour(@RequestBody AddTourRequest addTourRequest) {
        TourResponse tourResponse = service.addTour(addTourRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(tourResponse);
    }

    @GetMapping("/tours/{id}")
    public ResponseEntity<TourResponse> getTourById(@PathVariable Long id) {
        TourResponse tourResponse = tourMapper.toResponse(service.getTourById(id));
        return ResponseEntity.ok(tourResponse);
    }

    @PutMapping("/tours/{id}")
    public ResponseEntity<UpdateTourResponse> updateTour(@PathVariable Long id, @RequestBody UpdateTourRequest updateTourRequest) {
        UpdateTourResponse updateTourResponse = service.updateTour(id, updateTourRequest);
        return ResponseEntity.ok(updateTourResponse);
    }

    @DeleteMapping("/tours/{id}")
    public ResponseEntity<Void> deleteTour(@PathVariable Long id) {
        try {
            service.deleteTourById(id);
            return ResponseEntity.noContent().build();
        } catch (TourNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/{userId}/tours/{tourId}")
    public ResponseEntity<String> bookTourForUser(
            @PathVariable Long userId,
            @PathVariable Long tourId) {
        try {
            service.bookTourForUser(userId, tourId);
            return ResponseEntity.ok("Tour booked successfully!");
        } catch (UserNotFoundException | TourNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error booking tour: " + e.getMessage());
        }
    }

    @GetMapping("/users/{userId}/tours")
    public ResponseEntity<List<TourResponse>> getToursForUser(@PathVariable Long userId) {
        List<TourResponse> tourResponses = service.findToursForUser(userId)
                .stream()
                .map(tourMapper::toResponse)
                .toList();
        return ResponseEntity.ok(tourResponses);
    }

    @PostMapping("/users/{userId}/tours/{tourId}")
    public ResponseEntity<AddTourToUserResponse> addTourToUser(
            @PathVariable Long userId,
            @PathVariable Long tourId) {
        try {
            AddTourToUserResponse response = service.addTourToUser(userId, tourId);
            return ResponseEntity.ok(response);
        } catch (UserNotFoundException | TourNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new AddTourToUserResponse(userId, null, tourId, null, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AddTourToUserResponse(userId, null, tourId, null, "Failed to add tour to user"));
        }
    }

    @DeleteMapping("/users/{userId}/tours/{tourId}")
    public ResponseEntity<Void> removeTourFromUser(
            @PathVariable Long userId,
            @PathVariable Long tourId) {
        try {
            service.removeTourFromUser(userId, tourId);
            return ResponseEntity.noContent().build();
        } catch (TourNotFoundException | UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    // ========================================================================================================
    // User Endpoints
    // ========================================================================================================

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> userResponses = service.findAllUsers();
        return ResponseEntity.ok(userResponses);
    }

    @PostMapping("/users")
    public ResponseEntity<UserResponse> createUser(@RequestBody AddUserRequest addUserRequest) {
        UserResponse userResponse = service.addUser(addUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UpdateUserResponse> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest updateUserRequest) {
        UpdateUserResponse updateUserResponse = service.updateUser(id, updateUserRequest);
        return ResponseEntity.ok(updateUserResponse);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            service.deleteUserById(id);
            return ResponseEntity.noContent().build();
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    // ========================================================================================================
    // Airport Endpoints
    // ========================================================================================================

    @GetMapping("/airports")
    public ResponseEntity<List<AirportResponse>> getAllAirports() {
        List<AirportResponse> airportResponses = service.findAllAirports()
                .stream()
                .map(airportMapper::toResponse)
                .toList();
        return ResponseEntity.ok(airportResponses); // Returns 200 OK with the list of airports
    }

    @PostMapping("/airports")
    public ResponseEntity<AirportResponse> createAirport(@RequestBody AddAirportRequest addAirportRequest) {
        AirportResponse airportResponse = service.addAirport(addAirportRequest); // Directly call the service method that returns AirportResponse
        return ResponseEntity.status(HttpStatus.CREATED).body(airportResponse); // Returns 201 Created with the new airport
    }

    @PutMapping("/airports/{id}")
    public ResponseEntity<UpdateAirportResponse> updateAirport(@PathVariable Long id, @RequestBody UpdateAirportRequest updateAirportRequest) {
        UpdateAirportResponse updateAirportResponse = service.updateAirport(id, updateAirportRequest);
        return ResponseEntity.ok(updateAirportResponse); // Returns 200 OK with the updated airport
    }

    @DeleteMapping("/airports/{id}")
    public ResponseEntity<Void> deleteAirport(@PathVariable Long id) {
        try {
            service.deleteAirportById(id);
            return ResponseEntity.noContent().build(); // Returns 204 No Content after successful deletion
        } catch (AirportNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Returns 404 Not Found
        }
    }

    // ========================================================================================================
    // Country Endpoints
    // ========================================================================================================

    @GetMapping("/countries")
    public ResponseEntity<List<CountryResponse>> getAllCountries() {
        List<CountryResponse> countryResponses = service.findAllCountries()
                .stream()
                .map(countryMapper::toResponse)
                .toList();
        return ResponseEntity.ok(countryResponses); // Returns 200 OK with the list of countries
    }

    @PostMapping("/countries")
    public ResponseEntity<CountryResponse> createCountry(@RequestBody AddCountryRequest addCountryRequest) {
        CountryResponse countryResponse = service.addCountry(addCountryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(countryResponse); // Returns 201 Created with the new country
    }

    // Update Country
    @PutMapping("/countries/{id}")
    public ResponseEntity<UpdateCountryResponse> updateCountry(@PathVariable Long id, @RequestBody UpdateCountryRequest updateCountryRequest) {
        UpdateCountryResponse countryResponse = service.updateCountry(id, updateCountryRequest); // Updated to match service return type
        return ResponseEntity.ok(countryResponse);
    }

    // Delete Country
    @DeleteMapping("/countries/{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable Long id) {
        try {
            service.deleteCountryById(id);
            return ResponseEntity.noContent().build(); // Returns 204 No Content after successful deletion
        } catch (CountryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Returns 404 Not Found
        }
    }

    // ========================================================================================================
    // City Endpoints
    // ========================================================================================================

    @GetMapping("/cities")
    public ResponseEntity<List<CityResponse>> getAllCities() {
        List<CityResponse> cityResponses = service.findAllCities()
                .stream()
                .map(cityMapper::toResponse)
                .toList();
        return ResponseEntity.ok(cityResponses); // Returns 200 OK with the list of cities
    }

    @PostMapping("/cities")
    public ResponseEntity<CityResponse> createCity(@RequestBody AddCityRequest addCityRequest) {
        CityResponse cityResponse = service.addCity(addCityRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(cityResponse); // Returns 201 Created with the new city
    }

    @PutMapping("/cities/{id}")
    public ResponseEntity<UpdateCityResponse> updateCity(@PathVariable Long id, @RequestBody UpdateCityRequest updateCityRequest) {
        UpdateCityResponse cityResponse = service.updateCity(id, updateCityRequest);
        return ResponseEntity.ok(cityResponse); // Returns 200 OK with the updated city
    }

    @DeleteMapping("/cities/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable Long id) {
        try {
            service.deleteCityById(id);
            return ResponseEntity.noContent().build(); // Returns 204 No Content after successful deletion
        } catch (CityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Returns 404 Not Found
        }
    }

    // ========================================================================================================
    // Hotel Endpoints
    // ========================================================================================================

    @GetMapping("/hotels")
    public ResponseEntity<List<HotelResponse>> getAllHotels() {
        List<HotelResponse> hotelResponses = service.findAllHotels()
                .stream()
                .map(hotelMapper::toResponse)
                .toList();
        return ResponseEntity.ok(hotelResponses); // Returns 200 OK with the list of hotels
    }

    @PostMapping("/hotels")
    public ResponseEntity<HotelResponse> createHotel(@RequestBody AddHotelRequest addHotelRequest) {
        HotelResponse hotelResponse = service.addHotel(addHotelRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(hotelResponse); // Returns 201 Created with the new hotel
    }

    @PutMapping("/hotels/{id}")
    public ResponseEntity<UpdateHotelResponse> updateHotel(@PathVariable Long id, @RequestBody UpdateHotelRequest updateHotelRequest) {
        UpdateHotelResponse hotelResponse = service.updateHotel(id, updateHotelRequest);
        return ResponseEntity.ok(hotelResponse); // Returns 200 OK with the updated hotel
    }

    @DeleteMapping("/hotels/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable Long id) {
        try {
            service.deleteHotelById(id);
            return ResponseEntity.noContent().build(); // Returns 204 No Content after successful deletion
        } catch (HotelNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Returns 404 Not Found
        }
    }
}
