package com.travelagency.travelagency.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String cityName; // City name should be required and unique

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country; // Each city belongs to one country

    @OneToMany(mappedBy = "city")
    private List<Airport> airports; // A city can have multiple airports
}
