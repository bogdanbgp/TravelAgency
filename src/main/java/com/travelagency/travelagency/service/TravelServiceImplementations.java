package com.travelagency.travelagency.service;

import com.travelagency.travelagency.dto.request.airport.AddAirportRequest;
import com.travelagency.travelagency.dto.request.airport.UpdateAirportRequest;
import com.travelagency.travelagency.dto.request.city.AddCityRequest;
import com.travelagency.travelagency.dto.request.city.UpdateCityRequest;
import com.travelagency.travelagency.dto.request.country.AddCountryRequest;
import com.travelagency.travelagency.dto.request.country.UpdateCountryRequest;
import com.travelagency.travelagency.dto.request.hotel.AddHotelRequest;
import com.travelagency.travelagency.dto.request.hotel.UpdateHotelRequest;
import com.travelagency.travelagency.dto.request.role.AddRoleRequest;
import com.travelagency.travelagency.dto.request.role.AddRoleToUserRequest;
import com.travelagency.travelagency.dto.request.role.UpdateRoleRequest;
import com.travelagency.travelagency.dto.request.tour.AddTourRequest;
import com.travelagency.travelagency.dto.request.tour.UpdateTourRequest;
import com.travelagency.travelagency.dto.request.user.AddUserRequest;
import com.travelagency.travelagency.dto.request.user.UpdateUserRequest;
import com.travelagency.travelagency.dto.response.airport.AirportResponse;
import com.travelagency.travelagency.dto.response.airport.UpdateAirportResponse;
import com.travelagency.travelagency.dto.response.city.CityResponse;
import com.travelagency.travelagency.dto.response.city.UpdateCityResponse;
import com.travelagency.travelagency.dto.response.country.CountryResponse;
import com.travelagency.travelagency.dto.response.country.UpdateCountryResponse;
import com.travelagency.travelagency.dto.response.hotel.HotelResponse;
import com.travelagency.travelagency.dto.response.hotel.UpdateHotelResponse;
import com.travelagency.travelagency.dto.response.role.RoleResponse;
import com.travelagency.travelagency.dto.response.role.UpdateRoleResponse;
import com.travelagency.travelagency.dto.response.tour.AddTourToUserResponse;
import com.travelagency.travelagency.dto.response.tour.TourResponse;
import com.travelagency.travelagency.dto.response.tour.UpdateTourResponse;
import com.travelagency.travelagency.dto.response.user.UpdateUserResponse;
import com.travelagency.travelagency.dto.response.user.UserResponse;
import com.travelagency.travelagency.entity.*;
import com.travelagency.travelagency.exception.role.RoleAlreadyExistsException;
import com.travelagency.travelagency.exception.role.RoleNotFoundException;
import com.travelagency.travelagency.exception.tour.*;
import com.travelagency.travelagency.exception.user.UserAlreadyExistsException;
import com.travelagency.travelagency.exception.user.UserNotFoundException;
import com.travelagency.travelagency.mapper.*;
import com.travelagency.travelagency.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TravelServiceImplementations implements TravelService {

    private final RoleRepository roleRepository;
    private final TourRepository tourRepository;
    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final AirportRepository airportRepository;
    private final HotelRepository hotelRepository;
    private final UserMapper userMapper;
    private final TourMapper tourMapper;
    private final RoleMapper roleMapper;
    private final CityMapper cityMapper;
    private final CountryMapper countryMapper;
    private final AirportMapper airportMapper;
    private final HotelMapper hotelMapper;

    @Autowired
    public TravelServiceImplementations(RoleRepository roleRepository,
                                        TourRepository tourRepository,
                                        UserRepository userRepository,
                                        CityRepository cityRepository,
                                        CountryRepository countryRepository,
                                        AirportRepository airportRepository,
                                        HotelRepository hotelRepository,
                                        CityMapper cityMapper,
                                        CountryMapper countryMapper,
                                        AirportMapper airportMapper,
                                        UserMapper userMapper,
                                        TourMapper tourMapper,
                                        RoleMapper roleMapper,
                                        HotelMapper hotelMapper) {
        this.roleRepository = roleRepository;
        this.tourRepository = tourRepository;
        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.airportRepository = airportRepository;
        this.hotelRepository = hotelRepository;
        this.userMapper = userMapper;
        this.tourMapper = tourMapper;
        this.roleMapper = roleMapper;
        this.cityMapper = cityMapper;
        this.countryMapper = countryMapper;
        this.airportMapper = airportMapper;
        this.hotelMapper = hotelMapper;
    }


//-----------------------------------------------------------------------------------------------------
    // ROLE-RELATED METHODS

    // Add Role
    @Override
    public RoleResponse addRole(AddRoleRequest addRoleRequest) {
        // Check if a role with the same name already exists
        Optional<Role> existingRole = roleRepository.findRoleByRoleName(addRoleRequest.getRoleName());
        if (existingRole.isPresent()) {
            throw new RoleAlreadyExistsException("A role with this name already exists");
        }
        // Use RoleMapper to convert DTO to Entity
        Role role = roleMapper.fromRequest(addRoleRequest);
        // Save the role
        Role savedRole = roleRepository.save(role);
        // Return the response DTO
        return roleMapper.toResponse(savedRole); // Assume you have a toResponse method in RoleMapper
    }
                @Override
                public List<Role> findAllRoles() {
                    // This method retrieves and returns a list of all roles from the roleRepository.
                    return roleRepository.findAll();
                }
                @Override
                public Role getRoleById(Long id) {
                    // This method retrieves a specific role by its ID.
                    // If the role with the provided ID is not found, it throws a RoleNotFoundException.
                    return roleRepository.findById(id)
                            .orElseThrow(() -> new RoleNotFoundException("Role not found for id: " + id));
                }
                // Update Role
                @Override
                public UpdateRoleResponse updateRole(Long id, UpdateRoleRequest updateRoleRequest) {
                    Role existingRole = roleRepository.findById(id)
                            .orElseThrow(() -> new RoleNotFoundException("Role not found"));

                    roleMapper.fromUpdateRequest(updateRoleRequest, existingRole);
                    Role updatedRole = roleRepository.save(existingRole);
                    return roleMapper.toUpdateResponse(updatedRole); // Return UpdateRoleResponse
                }
                // Delete Role
                @Override
                public void deleteRoleById(Long id) {
                    if (!roleRepository.existsById(id)) {
                        throw new RoleNotFoundException("Role not found for ID: " + id);
                    }
                    roleRepository.deleteById(id);
                }

                // USER-ROLE RELATED METHODS:

                @Override
                public User addRoleToUser(AddRoleToUserRequest addRoleToUserRequest) {
                    User user = userRepository.findById(addRoleToUserRequest.getUserId())
                            .orElseThrow(() -> new UserNotFoundException("User with ID " + addRoleToUserRequest.getUserId() + " not found"));

                    Role role = roleRepository.findById(addRoleToUserRequest.getRoleId())
                            .orElseThrow(() -> new RoleNotFoundException("Role with ID " + addRoleToUserRequest.getRoleId() + " not found"));

                    user.addRole(role);
                    return userRepository.save(user);
                }

                @Override
                public void removeRoleFromUser(Long userId, Long roleId) {
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));

                    Role role = roleRepository.findById(roleId)
                            .orElseThrow(() -> new RoleNotFoundException("Role with ID " + roleId + " not found"));

                    user.removeRole(role);
                    userRepository.save(user);
                }


//-----------------------------------------------------------------------------------------------------

    // TOUR-RELATED METHODS:

    @Override
    public TourResponse addTour(AddTourRequest addTourRequest) {
        // Check if a tour with the same name already exists
        Optional<Tour> existingTour = tourRepository.findTourByTourName(addTourRequest.getTourName());
        if (existingTour.isPresent()) {
            throw new TourAlreadyExistsException("A tour with this name already exists");
        }
        // Fetch related entities (countries, cities, airports, hotels) based on IDs provided in AddTourRequest
        Country fromCountry = countryRepository.findById(addTourRequest.getFromCountryId())
                .orElseThrow(() -> new CountryNotFoundException("Country not found"));
        Country toCountry = countryRepository.findById(addTourRequest.getToCountryId())
                .orElseThrow(() -> new CountryNotFoundException("Country not found"));
        City fromCity = cityRepository.findById(addTourRequest.getFromCityId())
                .orElseThrow(() -> new CityNotFoundException("City not found"));
        City toCity = cityRepository.findById(addTourRequest.getToCityId())
                .orElseThrow(() -> new CityNotFoundException("City not found"));
        Airport fromAirport = airportRepository.findById(addTourRequest.getFromAirportId())
                .orElseThrow(() -> new AirportNotFoundException("Airport not found"));
        Airport toAirport = airportRepository.findById(addTourRequest.getToAirportId())
                .orElseThrow(() -> new AirportNotFoundException("Airport not found"));
        Hotel toHotel = addTourRequest.getHotelId() != null ?
                hotelRepository.findById(addTourRequest.getHotelId())
                        .orElseThrow(() -> new HotelNotFoundException("Hotel not found")) : null;

        // Map AddTourRequest to Tour using the TourMapper
        Tour tour = tourMapper.fromRequest(addTourRequest);

        // Set the fetched related entities to the tour
        tour.setFromCountry(fromCountry);
        tour.setToCountry(toCountry);
        tour.setFromCity(fromCity);
        tour.setToCity(toCity);
        tour.setFromAirport(fromAirport);
        tour.setToAirport(toAirport);
        tour.setToHotel(toHotel); // Set the hotel if available

        // Save the new tour to the repository and return the response
        Tour savedTour = tourRepository.save(tour);
        return tourMapper.toResponse(savedTour);
    }

                @Override
                public List<Tour> findAllTours() {
                    return tourRepository.findAll();
                }

                @Override
                public Tour getTourById(Long id) {
                    return tourRepository.findById(id)
                            .orElseThrow(() -> new TourNotFoundException("Tour not found for ID: " + id));
                }

                @Override
                public UpdateTourResponse updateTour(Long id, UpdateTourRequest updateTourRequest) {
                    Tour existingTour = tourRepository.findById(id)
                            .orElseThrow(() -> new TourNotFoundException("Tour not found"));

                    tourMapper.fromUpdateRequest(updateTourRequest, existingTour);
                    Tour updatedTour = tourRepository.save(existingTour);
                    return tourMapper.toUpdateResponse(updatedTour);
                }

                @Override
                public void deleteTourById(Long id) {
                    if (!tourRepository.existsById(id)) {
                        throw new TourNotFoundException("Tour not found for ID: " + id);
                    }
                    tourRepository.deleteById(id);
                }

                @Override
                public void bookTourForUser(Long userId, Long tourId) {
                    User user = getUserById(userId);
                    Tour tour = getTourById(tourId);

                    user.addTour(tour);
                    userRepository.save(user);
                }

                @Override
                public AddTourToUserResponse addTourToUser(Long userId, Long tourId) {
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new UserNotFoundException("User not found"));
                    Tour tour = tourRepository.findById(tourId)
                            .orElseThrow(() -> new TourNotFoundException("Tour not found"));

                    user.addTour(tour);
                    userRepository.save(user);

                    return new AddTourToUserResponse(
                            user.getId(),
                            user.getFullName(),
                            tour.getId(),
                            tour.getTourName(),
                            "Tour added successfully"
                    );
                }

                @Override
                public List<Tour> findToursForUser(Long userId) {
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new UserNotFoundException("User not found"));

                    return tourRepository.findByUserId(userId);
                }

                @Override
                public void removeTourFromUser(Long userId, Long tourId) {
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new UserNotFoundException("User not found"));
                    Tour tour = tourRepository.findById(tourId)
                            .orElseThrow(() -> new TourNotFoundException("Tour not found"));

                    user.removeTour(tour);
                    userRepository.save(user);
                }

// -------------------------------------------------------------------------------------------------------------

// USER-RELATED METHODS:

    @Override
    public UserResponse addUser(AddUserRequest addUserRequest) {
        Optional<User> existingUserByEmail = userRepository.findUserByEmail(addUserRequest.getEmail());
        if (existingUserByEmail.isPresent()) {
            throw new UserAlreadyExistsException("A user with this email already exists");
        }
        Optional<User> existingUserByUsername = userRepository.findUserByUsername(addUserRequest.getUsername());
        if (existingUserByUsername.isPresent()) {
            throw new UserAlreadyExistsException("A user with this username already exists");
        }

        User user = userMapper.fromRequest(addUserRequest);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(addUserRequest.getPassword());
        user.setPassword(hashedPassword);

        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            Role userRole = roleRepository.findRoleByRoleName("USER")
                    .orElseThrow(() -> new RoleNotFoundException("Default role 'USER' does not exist."));
            user.getRoles().add(userRole);
        }

        if (addUserRequest.getRoleIds() != null && !addUserRequest.getRoleIds().isEmpty()) {
            for (Long roleId : addUserRequest.getRoleIds()) {
                Role fetchedRole = roleRepository.findById(roleId)
                        .orElseThrow(() -> new RoleNotFoundException("Role with ID " + roleId + " not found"));
                user.getRoles().add(fetchedRole);
            }
        }

        if (addUserRequest.getTourIds() != null && !addUserRequest.getTourIds().isEmpty()) {
            List<Tour> tours = new ArrayList<>();
            for (Long tourId : addUserRequest.getTourIds()) {
                Tour fetchedTour = tourRepository.findById(tourId)
                        .orElseThrow(() -> new TourNotFoundException("Tour with ID " + tourId + " not found"));
                tours.add(fetchedTour);
            }
            user.setTours(tours);
        }
        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

                @Override
                public List<UserResponse> findAllUsers() {
                    return userRepository.findAll().stream()
                            .map(userMapper::toResponse)
                            .collect(Collectors.toList());
                }

                @Override
                public User getUserById(Long id) {
                    return userRepository.findById(id)
                            .orElseThrow(() -> new UserNotFoundException("User not found for ID: " + id));
                }

                @Override
                public UpdateUserResponse updateUser(Long id, UpdateUserRequest updateUserRequest) {
                    User existingUser = userRepository.findById(id)
                            .orElseThrow(() -> new UserNotFoundException("User not found"));
                    userMapper.fromUpdateRequest(updateUserRequest, existingUser);

                    if (updateUserRequest.getPassword() != null && !updateUserRequest.getPassword().isEmpty()) {
                        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                        String hashedPassword = passwordEncoder.encode(updateUserRequest.getPassword());
                        existingUser.setPassword(hashedPassword);
                    }
                    User updatedUser = userRepository.save(existingUser);
                    return userMapper.toUpdateResponse(updatedUser);
                }

                @Override
                public void deleteUserById(Long id) {
                    User user = userRepository.findById(id)
                            .orElseThrow(() -> new UserNotFoundException("User not found for ID: " + id));

                    for (Tour tour : user.getTours()) {
                        tour.getUsers().remove(user);
                    }
                    for (Role role : user.getRoles()) {
                        role.getUsers().remove(user);
                    }
                    userRepository.delete(user);
                }


// -------------------------------------------------------------------------------------------------------------

    // COUNTRY-RELATED METHODS:

    @Override
    public CountryResponse addCountry(AddCountryRequest addCountryRequest) {
        // Check if the country already exists
        if (countryRepository.existsByCountryName(addCountryRequest.getCountryName())) {
            throw new CountryAlreadyExistsException("Country already exists: " + addCountryRequest.getCountryName());
        }
        // Convert request to entity using the mapper
        Country country = countryMapper.fromRequest(addCountryRequest);
        // Save the country
        Country savedCountry = countryRepository.save(country);
        // Return the response DTO
        return countryMapper.toResponse(savedCountry); // Assume you have a toResponse method in CountryMapper
    }
                @Override
                public List<Country> findAllCountries() {
                    // Retrieves a list of all countries from the country repository.
                    // This method returns all country records as they are stored in the database
                    return countryRepository.findAll();
                }
                @Override
                public Country getCountryById(Long id) {
                    // Retrieves a specific country by its ID.
                    // If a country with the given ID exists, it is returned; otherwise, a CountryNotFoundException is thrown.
                    return countryRepository.findById(id)
                            .orElseThrow(() -> new CountryNotFoundException("Country not found for ID: " + id));
                }
                // Update Country
                @Override
                public UpdateCountryResponse updateCountry(Long id, UpdateCountryRequest updateCountryRequest) {
                    Country existingCountry = countryRepository.findById(id)
                            .orElseThrow(() -> new CountryNotFoundException("Country not found"));

                    countryMapper.fromUpdateRequest(updateCountryRequest, existingCountry);
                    Country updatedCountry = countryRepository.save(existingCountry);
                    return countryMapper.toUpdateResponse(updatedCountry); // Ensure this returns UpdateCountryResponse
                }
                // Delete Country
                @Override
                public void deleteCountryById(Long id) {
                    if (!countryRepository.existsById(id)) {
                        throw new CountryNotFoundException("Country not found for ID: " + id);
                    }
                    countryRepository.deleteById(id);
                }

// -------------------------------------------------------------------------------------------------------------

    // CITY-RELATED METHODS:

    @Override
    public CityResponse addCity(AddCityRequest addCityRequest) {
        // Check if the country associated with the city exists.
        Country country = countryRepository.findById(addCityRequest.getCountryId())
                .orElseThrow(() -> new CountryNotFoundException("Country not found"));
        // Check if the city name already exists in the specified country.
        if (cityRepository.existsByCityNameAndCountryId(addCityRequest.getCityName(), country.getId())) {
            throw new CityAlreadyExistsException("City already exists.");
        }
        // Convert the AddCityRequest DTO to a City entity using the cityMapper.
        City city = cityMapper.fromRequest(addCityRequest);
        // Associate the newly created city with the found country.
        city.setCountry(country); // Set the country here
        // Save the city to the repository.
        City savedCity = cityRepository.save(city);
        // Return the response DTO using the mapper.
        return cityMapper.toResponse(savedCity);
    }
                @Override
                public List<City> findAllCities() {
                    // Retrieves a list of all cities from the city repository.
                    // This method returns all city records as they are stored in the database
                    return cityRepository.findAll();
                }
                @Override
                public City getCityById(Long id) {
                    // Retrieves a specific city by its ID.
                    // If a city with the given ID exists, it is returned; otherwise, a CityNotFoundException is thrown.
                    return cityRepository.findById(id)
                            .orElseThrow(() -> new CityNotFoundException("City not found for ID: " + id));
                }
                // Update City
                @Override
                public UpdateCityResponse updateCity(Long id, UpdateCityRequest updateCityRequest) {
                    // Find the existing city by ID
                    City existingCity = cityRepository.findById(id)
                            .orElseThrow(() -> new CityNotFoundException("City not found"));

                    // Check if a country ID is provided in the request
                    if (updateCityRequest.getCountryId() != null) {
                        // Fetch the country and set it on the existing city
                        Country country = countryRepository.findById(updateCityRequest.getCountryId())
                                .orElseThrow(() -> new CountryNotFoundException("Country not found"));
                        existingCity.setCountry(country); // Set the country reference here
                    }

                    // Update the city name using the mapper
                    cityMapper.fromUpdateRequest(updateCityRequest, existingCity);
                    // Save the updated city back to the repository
                    City updatedCity = cityRepository.save(existingCity);
                    // Return the response DTO
                    return cityMapper.toUpdateResponse(updatedCity);
                }
                // Delete City
                @Override
                public void deleteCityById(Long id) {
                    if (!cityRepository.existsById(id)) {
                        throw new CityNotFoundException("City not found for ID: " + id);
                    }
                    cityRepository.deleteById(id);
                }

// -------------------------------------------------------------------------------------------------------------

    // AIRPORT-RELATED METHODS:

    // Add Airport
    @Override
    public AirportResponse addAirport(AddAirportRequest addAirportRequest) {
        // Check if the city associated with the airport exists.
        City city = cityRepository.findById(addAirportRequest.getCityId())
                .orElseThrow(() -> new CityNotFoundException("City not found with ID: " + addAirportRequest.getCityId()));
        // Check if the airport name already exists in the specified city.
        if (airportRepository.existsByAirportNameAndCityId(addAirportRequest.getAirportName(), city.getId())) {
            throw new AirportAlreadyExistsException("Airport already exists in city: " + city.getCityName());
        }
        // Convert the AddAirportRequest DTO to an Airport entity using the airportMapper.
        Airport airport = airportMapper.fromRequest(addAirportRequest);
        // Associate the newly created airport with the found city.
        airport.setCity(city); // Set the city here
        // Save the airport to the repository.
        Airport savedAirport = airportRepository.save(airport);
        // Return the response DTO using the mapper.
        return airportMapper.toResponse(savedAirport);
    }
                @Override
                public List<Airport> findAllAirports() {
                    // Retrieves a list of all airports from the airport repository.
                    // This method returns all airport records as they are stored in the database
                    return airportRepository.findAll();
                }
                @Override
                public Airport getAirportById(Long id) {
                    // Retrieves a specific airport by its ID.
                    // If an airport with the given ID exists, it is returned; otherwise, an AirportNotFoundException is thrown.
                    return airportRepository.findById(id)
                            .orElseThrow(() -> new AirportNotFoundException("Airport not found for ID: " + id));
                }
                // Update Airport
                @Override
                public UpdateAirportResponse updateAirport(Long id, UpdateAirportRequest updateAirportRequest) {
                    // Find the existing airport by ID
                    Airport existingAirport = airportRepository.findById(id)
                            .orElseThrow(() -> new AirportNotFoundException("Airport not found"));
                    // Check if a city ID is provided in the request
                    if (updateAirportRequest.getCityId() != null) {
                        // Fetch the city and set it on the existing airport
                        City city = cityRepository.findById(updateAirportRequest.getCityId())
                                .orElseThrow(() -> new CityNotFoundException("City not found"));
                        existingAirport.setCity(city); // Set the city reference here
                    }
                    // Update the airport name using the mapper
                    airportMapper.fromUpdateRequest(updateAirportRequest, existingAirport);
                    // Save the updated airport back to the repository
                    Airport updatedAirport = airportRepository.save(existingAirport);
                    // Return the response DTO
                    return airportMapper.toUpdateResponse(updatedAirport);
                }
                // Delete Airport
                @Override
                public void deleteAirportById(Long id) {
                    if (!airportRepository.existsById(id)) {
                        throw new AirportNotFoundException("Airport not found for ID: " + id);
                    }
                    airportRepository.deleteById(id);
                }

// -------------------------------------------------------------------------------------------------------------

    // HOTEL-RELATED METHODS:

    @Override
    public HotelResponse addHotel(AddHotelRequest addHotelRequest) {
        // Check if the hotel already exists
        if (hotelRepository.existsByHotelName(addHotelRequest.getHotelName())) {
            throw new HotelAlreadyExistsException("Hotel already exists: " + addHotelRequest.getHotelName());
        }
        // Use mapper to convert request to entity
        Hotel hotel = hotelMapper.fromRequest(addHotelRequest);
        // Save the hotel
        Hotel savedHotel = hotelRepository.save(hotel);
        // Return the response DTO
        return hotelMapper.toResponse(savedHotel); // Assume you have a toResponse method in HotelMapper
    }
                @Override
                public List<Hotel> findAllHotels() {
                    // Retrieves a list of all hotels from the hotel repository.
                    return hotelRepository.findAll(); // Return all hotel entities directly
                }
                @Override
                public Hotel getHotelById(Long id) {
                    // Retrieves a specific hotel by its ID.
                    return hotelRepository.findById(id)
                            .orElseThrow(() -> new HotelNotFoundException("Hotel not found for ID: " + id));
                }
                // Update Hotel
                @Override
                public UpdateHotelResponse updateHotel(Long id, UpdateHotelRequest updateHotelRequest) {
                    Hotel existingHotel = hotelRepository.findById(id)
                            .orElseThrow(() -> new HotelNotFoundException("Hotel not found"));

                    hotelMapper.fromUpdateRequest(updateHotelRequest, existingHotel);
                    Hotel updatedHotel = hotelRepository.save(existingHotel);
                    return hotelMapper.toUpdateResponse(updatedHotel); // Return UpdateHotelResponse
                }
                // Delete Hotel
                @Override
                public void deleteHotelById(Long id) {
                    if (!hotelRepository.existsById(id)) {
                        throw new HotelNotFoundException("Hotel not found for ID: " + id); // Enhance error handling
                    }
                    hotelRepository.deleteById(id);
    }
}
