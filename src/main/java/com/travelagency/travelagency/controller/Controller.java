package com.travelagency.travelagency.controller;

import com.travelagency.travelagency.dto.request.role.AddRoleRequest;
import com.travelagency.travelagency.dto.request.role.AddRoleToUserRequest;
import com.travelagency.travelagency.dto.request.tour.AddTourRequest;
import com.travelagency.travelagency.dto.request.tour.UpdateTourRequest;
import com.travelagency.travelagency.dto.request.user.AddUserRequest;
import com.travelagency.travelagency.dto.request.airport.AddAirportRequest;
import com.travelagency.travelagency.dto.request.country.AddCountryRequest;
import com.travelagency.travelagency.dto.request.city.AddCityRequest;
import com.travelagency.travelagency.dto.request.hotel.AddHotelRequest;
import com.travelagency.travelagency.dto.request.user.UpdateUserRequest;
import com.travelagency.travelagency.dto.response.tour.UpdateTourResponse;
import com.travelagency.travelagency.dto.response.user.UpdateUserResponse;
import com.travelagency.travelagency.dto.response.user.UserResponse;
import com.travelagency.travelagency.dto.response.role.RoleResponse;
import com.travelagency.travelagency.dto.response.tour.TourResponse;
import com.travelagency.travelagency.dto.response.airport.AirportResponse;
import com.travelagency.travelagency.dto.response.country.CountryResponse;
import com.travelagency.travelagency.dto.response.city.CityResponse;
import com.travelagency.travelagency.dto.response.hotel.HotelResponse;
import com.travelagency.travelagency.entity.Role;
import com.travelagency.travelagency.entity.User;
import com.travelagency.travelagency.entity.Airport;
import com.travelagency.travelagency.entity.Country;
import com.travelagency.travelagency.entity.City;
import com.travelagency.travelagency.entity.Hotel;
import com.travelagency.travelagency.exception.tour.TourNotFoundException;
import com.travelagency.travelagency.exception.user.UserAlreadyExistsException;
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
public class Controller {

    private final TravelService service;
    private final UserMapper userMapper;
    private final TourMapper tourMapper;
    private final RoleMapper roleMapper;
    private final AirportMapper airportMapper;
    private final CountryMapper countryMapper;
    private final CityMapper cityMapper;
    private final HotelMapper hotelMapper;

    @Autowired
    public Controller(TravelService service, UserMapper userMapper, TourMapper tourMapper,
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

    // ==========================
    // General Endpoints
    // ==========================

    @GetMapping("/")  // Mapping for root URL
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Welcome to the Travel Agency!");
    }

    @GetMapping("/hello")  // Define a GET endpoint
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello, world!");
    }

    @GetMapping("/bye")  // Define a GET endpoint
    public ResponseEntity<String> bye() {
        return ResponseEntity.ok("Bye!");
    }

    // ==========================
    // Role Endpoints
    // ==========================

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
        Role savedRole = service.addRole(addRoleRequest);
        RoleResponse roleResponse = roleMapper.toResponse(savedRole);
        return ResponseEntity.status(HttpStatus.CREATED).body(roleResponse); // Returns 201 Created with the new role
    }

    @DeleteMapping("/roles/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        service.deleteRoleById(id);
        return ResponseEntity.noContent().build(); // Returns 204 No Content after successful deletion
    }

    // ==========================
    // Tour Endpoints
    // ==========================

    @GetMapping("/tours")
    public ResponseEntity<List<TourResponse>> getAllTours() {
        List<TourResponse> tourResponses = service.findAllTours()
                .stream()
                .map(tourMapper::toResponse)
                .toList();
        return ResponseEntity.ok(tourResponses); // Returns 200 OK with the list of tours
    }

    @PostMapping("/tours")
    public ResponseEntity<TourResponse> createTour(@RequestBody AddTourRequest addTourRequest) {
        TourResponse tourResponse = service.addTour(addTourRequest); // Directly call the service method that returns TourResponse
        return ResponseEntity.status(HttpStatus.CREATED).body(tourResponse); // Returns 201 Created with the new tour
    }

    @PutMapping("/tours/{id}")
    public ResponseEntity<UpdateTourResponse> updateTour(@PathVariable Long id, @RequestBody UpdateTourRequest updateTourRequest) {
        UpdateTourResponse updateTourResponse = service.updateTour(id, updateTourRequest);
        return ResponseEntity.ok(updateTourResponse); // Returns 200 OK with the updated tour
    }

    @DeleteMapping("/tours/{id}")
    public ResponseEntity<Void> deleteTour(@PathVariable Long id) {
        try {
            service.deleteTourById(id);
            return ResponseEntity.noContent().build(); // Returns 204 No Content after successful deletion
        } catch (TourNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Returns 404 Not Found
        }
    }

    // ==========================
    // User Endpoints
    // ==========================

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
    public ResponseEntity<UpdateUserResponse> updateUser(
            @PathVariable Long id, @RequestBody UpdateUserRequest updateUserRequest) {
        UpdateUserResponse updateUserResponse = service.updateUser(id, updateUserRequest);
        return ResponseEntity.ok(updateUserResponse);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            service.deleteUserById(id);
            return ResponseEntity.noContent().build(); // Returns 204 No Content after successful deletion
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Returns 404 Not Found
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // Returns 409 Conflict if the user can't be deleted
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Returns 500 Internal Server Error for any other issues
        }
    }


    // ==========================
    // User-Role Mappings
    // ==========================

    @PutMapping("/users/{userId}/roles") // Update user - add role to user
    public ResponseEntity<UserResponse> addRoleToUser(
            @PathVariable Long userId,
            @RequestBody AddRoleToUserRequest addRoleToUserRequest) {
        // Set userId in request object
        addRoleToUserRequest.setUserId(userId);
        User updatedUser = service.addRoleToUser(addRoleToUserRequest);
        UserResponse userResponse = userMapper.toResponse(updatedUser);
        return ResponseEntity.ok(userResponse); // Returns 200 OK with updated user details
    }

    @DeleteMapping("/users/{userId}/roles/{roleId}") // Remove role from user
    public ResponseEntity<Void> deleteRoleFromUser(@PathVariable Long userId, @PathVariable Long roleId) {
        service.removeRoleFromUser(userId, roleId);
        return ResponseEntity.noContent().build(); // Returns 204 No Content after successful deletion
    }

    // ==========================
    // Airport Endpoints
    // ==========================

    @PostMapping("/airports")
    public ResponseEntity<AirportResponse> addAirport(@RequestBody AddAirportRequest request) {
        Airport savedAirport = service.addAirport(request);
        AirportResponse response = airportMapper.toResponse(savedAirport);
        return ResponseEntity.status(HttpStatus.CREATED).body(response); // Returns 201 Created with new airport
    }

    @GetMapping("/airports")
    public ResponseEntity<List<AirportResponse>> getAirports() {
        List<AirportResponse> airports = service.findAllAirports()
                .stream()
                .map(airportMapper::toResponse)
                .toList();
        return ResponseEntity.ok(airports); // Returns 200 OK with airports list
    }

    // ==========================
    // Country Endpoints
    // ==========================

    @PostMapping("/countries")
    public ResponseEntity<CountryResponse> addCountry(@RequestBody AddCountryRequest request) {
        Country savedCountry = service.addCountry(request);
        CountryResponse response = countryMapper.toResponse(savedCountry);
        return ResponseEntity.status(HttpStatus.CREATED).body(response); // Returns 201 Created with new country
    }

    @GetMapping("/countries")
    public ResponseEntity<List<CountryResponse>> getCountries() {
        List<CountryResponse> countries = service.findAllCountries()
                .stream()
                .map(countryMapper::toResponse)
                .toList();
        return ResponseEntity.ok(countries); // Returns 200 OK with countries list
    }

    // ==========================
    // City Endpoints
    // ==========================

    @PostMapping("/cities")
    public ResponseEntity<CityResponse> addCity(@RequestBody AddCityRequest request) {
        City savedCity = service.addCity(request);
        CityResponse response = cityMapper.toResponse(savedCity);
        return ResponseEntity.status(HttpStatus.CREATED).body(response); // Returns 201 Created with new city
    }

    @GetMapping("/cities")
    public ResponseEntity<List<CityResponse>> getCities() {
        List<CityResponse> cities = service.findAllCities()
                .stream()
                .map(cityMapper::toResponse)
                .toList();
        return ResponseEntity.ok(cities); // Returns 200 OK with cities list
    }

    // ==========================
    // Hotel Endpoints
    // ==========================

    @PostMapping("/hotels")
    public ResponseEntity<HotelResponse> addHotel(@RequestBody AddHotelRequest request) {
        Hotel savedHotel = service.addHotel(request);
        HotelResponse response = hotelMapper.toResponse(savedHotel);
        return ResponseEntity.status(HttpStatus.CREATED).body(response); // Returns 201 Created with new hotel
    }

    @GetMapping("/hotels")
    public ResponseEntity<List<HotelResponse>> getHotels() {
        List<HotelResponse> hotels = service.findAllHotels()
                .stream()
                .map(hotelMapper::toResponse)
                .toList();
        return ResponseEntity.ok(hotels); // Returns 200 OK with hotels list
    }
}
