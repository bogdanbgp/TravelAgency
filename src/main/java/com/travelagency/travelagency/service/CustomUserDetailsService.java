package com.travelagency.travelagency.service;

import com.travelagency.travelagency.entity.SuperAdmin;
import com.travelagency.travelagency.entity.User;
import com.travelagency.travelagency.repository.SuperAdminRepository;
import com.travelagency.travelagency.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository; // Repository for accessing User data
    private final SuperAdminRepository superAdminRepository; // Repository for accessing SuperAdmin data

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository, SuperAdminRepository superAdminRepository) {
        this.userRepository = userRepository;
        this.superAdminRepository = superAdminRepository;
    }

//----------------------------------------------------------------------------------------------------------------
    /**
     * Loads UserDetails by username.
     * Checks the SuperAdmin repository first and then the User repository if not found.
     * User details, including roles, are fetched from the respective repositories.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // First, check if the user is a SuperAdmin
        Optional<SuperAdmin> superAdminOptional = superAdminRepository.findSuperAdminByUsername(username);
        if (superAdminOptional.isPresent()) {
            // If a SuperAdmin is found, load and return their details
            return loadSuperAdmin(superAdminOptional.get());
        }

        // If not a SuperAdmin, check if it's a regular User
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Load and return the User's details
        return loadUser(user);
    }
//----------------------------------------------------------------------------------------------------------------

                /**
                 * Loads and returns details for a SuperAdmin,
                 * including their roles as GrantedAuthority.
                 * User details are loaded from the SuperAdmin entity.
                 */
                private UserDetails loadSuperAdmin(SuperAdmin superAdmin) {
                    // Map each role to a GrantedAuthority for security checks
                    Collection<GrantedAuthority> authorities = superAdmin.getRoles().stream()
                            .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                            .collect(Collectors.toList());

                    // Create and return a UserDetails object for the SuperAdmin
                    return new org.springframework.security.core.userdetails.User(
                            superAdmin.getUsername(),
                            superAdmin.getPassword(),
                            authorities);
                }
//----------------------------------------------------------------------------------------------------------------

                /**
                 * Loads and returns details for a regular User,
                 * including their roles as GrantedAuthority.
                 * User details are loaded from the User entity.
                 */
                private UserDetails loadUser(User user) {
                    // Map each role to a GrantedAuthority for security checks
                    Collection<GrantedAuthority> authorities = user.getRoles().stream()
                            .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                            .collect(Collectors.toList());

                    // Create and return a UserDetails object for the User
                    return new org.springframework.security.core.userdetails.User(
                            user.getUsername(),
                            user.getPassword(),
                            authorities);
                }
//----------------------------------------------------------------------------------------------------------------

}
