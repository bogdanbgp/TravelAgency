package com.travelagency.travelagency.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tourName; // Tour name should be required

    @Column(nullable = false)
    private LocalDate departureDate; // Departure date is required

    @Column(nullable = false)
    private LocalDate returnDate; // Return date is required

    @Column(nullable = false)
    private int numberOfDays; // Number of days should not be null

    private String description; // Description can be optional

    @Column(nullable = false)
    private double price; // Price should be required

    @ManyToOne // many tours from the same country
    @JoinColumn(name = "from_country_id", nullable = false)
    private Country fromCountry;

    @ManyToOne // many tours to the same country
    @JoinColumn(name = "to_country_id", nullable = false)
    private Country toCountry;

    @ManyToOne // many tours from the same city
    @JoinColumn(name = "from_city_id", nullable = false)
    private City fromCity;

    @ManyToOne // many tours to the same city
    @JoinColumn(name = "to_city_id", nullable = false)
    private City toCity;

    @ManyToOne // many tours from the same airport
    @JoinColumn(name = "from_airport_id", nullable = false)
    private Airport fromAirport;

    @ManyToOne // many tours to the same airport
    @JoinColumn(name = "to_airport_id", nullable = false)
    private Airport toAirport;

    @ManyToOne // many tours to the same hotel
    @JoinColumn(name = "hotel_id")
    private Hotel toHotel;

    //-------------------------------------

    @ManyToMany(mappedBy = "tours") // Reference the tours from User
    private List<User> users = new ArrayList<>();
}
