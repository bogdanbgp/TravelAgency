package com.travelagency.travelagency.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // User ID

    @NotBlank(message = "Username is required") // Ensures username is not blank
    @Column(nullable = false, unique = true)
    private String username; // Username should be unique and not null

    @NotBlank(message = "First name is required") // Ensures first name is not blank
    @Column(name = "first_name", nullable = false)
    private String firstName; // User's first name

    @NotBlank(message = "Last name is required") // Ensures last name is not blank
    @Column(name = "last_name", nullable = false)
    private String lastName; // User's last name

    @Min(value = 18, message = "Age must be at least 18")
    private int age; // User's age

    @NotBlank(message = "Email is required") // Ensures email is not blank
    @Email(message = "Email should be valid") // Validates email format
    @Column(nullable = false, unique = true)
    private String email; // Email should be unique and not null

    @Pattern(regexp = "\\d{10}", message = "Mobile number must be 10 digits")
    private String mobile; // User's mobile number

    @NotBlank(message = "Password is required") // Ensures password is not blank
    @Column(nullable = false)
    private String password; // Consider removing for security purposes after hashing
// ---------------------------------------------------------------------------------------------------------------
    // Many-to-Many relationship with Tour
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER) // Set to EAGER loading
    @JoinTable( name = "users_tours",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "tour_id")} )
    private List<Tour> tours = new ArrayList<>(); // List of tours booked by the user
// ---------------------------------------------------------------------------------------------------------------
    // Many-to-Many relationship with Role
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER) // Set to EAGER loading
    @JoinTable( name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")} )
    private List<Role> roles = new ArrayList<>(); // User's roles
// ---------------------------------------------------------------------------------------------------------------
                // METHODS FOR SUPER-ADMIN MANAGEMENT:
                public void addRole(Role role) {
                    this.roles.add(role);
                    role.getUsers().add(this);
                }

                public void addTour(Tour tour) {
                    this.tours.add(tour); // Update to add the tour to the list
                    tour.getUsers().add(this); // Set the user for the tour
                }

                // Remove a role from the user
                public void removeRole(Role role) {
                    this.roles.remove(role); // Remove the role from the user's list
                    role.getUsers().remove(this); // Remove the user from the role's list
                }

                // Remove a tour from the user
                public void removeTour(Tour tour) {
                    this.tours.remove(tour); // Remove the tour from the user's list
                    tour.getUsers().remove(this); // Remove the user from the tour's list
                }
}
