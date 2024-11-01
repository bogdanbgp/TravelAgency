package com.travelagency.travelagency.repository;

import com.travelagency.travelagency.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Find a User by its username.
    // Used in TravelServiceImplementations for addUser method (making sure it doesn't already exist).
    // Used in AuthService for registration, to throw an exception in case the username already exists in the system.
    Optional<User> findUserByUsername(String username);

    // Find a User by its email.
    // Used in TravelServiceImplementations for addUser method (making sure it doesn't already exist).
    // Used in AuthService for registration, to throw an exception in case the email already exists in the system.
    Optional<User> findUserByEmail(String email);

    // Check if a User exists by its username.
    // Used in TravelServiceImplementations for addUser method (making sure it doesn't already exist).
    // Used in AuthService for registration, to verify if the username is already in use.
    boolean existsByUsername(String username);

    // Check if a User exists by its email.
    // Used in TravelServiceImplementations for addUser method (making sure it doesn't already exist).
    // Used in AuthService for registration, to verify if the email is already in use.
    boolean existsByEmail(String email);
}
