package com.travelagency.travelagency.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String airportName; // Airport name should be required and unique

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city; // Each airport belongs to one city
}
