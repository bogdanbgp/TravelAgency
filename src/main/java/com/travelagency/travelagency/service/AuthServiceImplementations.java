package com.travelagency.travelagency.service;

import com.travelagency.travelagency.dto.request.auth.LoginRequest;
import com.travelagency.travelagency.dto.request.auth.RegisterRequest;
import com.travelagency.travelagency.dto.request.auth.SuperAdminLoginRequest;
import com.travelagency.travelagency.dto.request.auth.SuperAdminRegisterRequest;
import com.travelagency.travelagency.dto.response.auth.LoginResponse;
import com.travelagency.travelagency.dto.response.auth.RegisterResponse;
import com.travelagency.travelagency.dto.response.auth.SuperAdminLoginResponse;
import com.travelagency.travelagency.dto.response.auth.SuperAdminRegisterResponse;
import com.travelagency.travelagency.entity.Role;
import com.travelagency.travelagency.entity.SuperAdmin;
import com.travelagency.travelagency.entity.User;
import com.travelagency.travelagency.exception.auth.AuthenticationFailedException;
import com.travelagency.travelagency.exception.role.RoleNotFoundException;
import com.travelagency.travelagency.exception.user.EmailAlreadyExistsException;
import com.travelagency.travelagency.exception.user.UserAlreadyExistsException;
import com.travelagency.travelagency.mapper.LoginMapper;
import com.travelagency.travelagency.mapper.RegisterMapper;
import com.travelagency.travelagency.mapper.SuperAdminLoginMapper;
import com.travelagency.travelagency.mapper.SuperAdminRegisterMapper;
import com.travelagency.travelagency.repository.RoleRepository;
import com.travelagency.travelagency.repository.SuperAdminRepository;
import com.travelagency.travelagency.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import jakarta.servlet.http.HttpServletRequest; // Import for HttpServletRequest
import jakarta.servlet.http.HttpSession; // Import for HttpSession
import java.util.Collections;

@Service
public class AuthServiceImplementations implements AuthService {

    private final SuperAdminRepository superAdminRepository;
    private final UserRepository userRepository;
    private final RegisterMapper registerMapper;
    private final LoginMapper loginMapper;
    private final SuperAdminRegisterMapper superAdminRegisterMapper;
    private final SuperAdminLoginMapper superAdminLoginMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final HttpServletRequest request; // To handle HTTP requests

    @Autowired
    public AuthServiceImplementations(SuperAdminRepository superAdminRepository,
                                      UserRepository userRepository,
                                      RegisterMapper registerMapper,
                                      LoginMapper loginMapper,
                                      SuperAdminRegisterMapper superAdminRegisterMapper,
                                      SuperAdminLoginMapper superAdminLoginMapper,
                                      PasswordEncoder passwordEncoder,
                                      RoleRepository roleRepository,
                                      CustomUserDetailsService customUserDetailsService,
                                      HttpServletRequest request) { // Inject HttpServletRequest
        this.superAdminRepository = superAdminRepository;
        this.userRepository = userRepository;
        this.registerMapper = registerMapper;
        this.loginMapper = loginMapper;
        this.superAdminRegisterMapper = superAdminRegisterMapper;
        this.superAdminLoginMapper = superAdminLoginMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.customUserDetailsService = customUserDetailsService;
        this.request = request; // Initialize request
    }

    // ------------------------------------------------------------------------------------------------------------

    // Loads user details for authentication based on username.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // This method is responsible for retrieving user details for both SuperAdmin and regular users.
        return customUserDetailsService.loadUserByUsername(username);
    }

    // --------------------------------------------------------------------------------------------------------------

    // Registers a new SuperAdmin.
    @Override
    public SuperAdminRegisterResponse registerSuperAdmin(SuperAdminRegisterRequest superAdminRegisterRequest) {
        String username = superAdminRegisterRequest.getUsername();
        // Check if the username already exists
        if (superAdminRepository.existsByUsername(username)) {
            throw new UserAlreadyExistsException("A user with this username already exists.");
        }
        // Map the request to a SuperAdmin entity
        SuperAdmin superAdmin = superAdminRegisterMapper.fromRequest(superAdminRegisterRequest);
        superAdmin.setPassword(passwordEncoder.encode(superAdminRegisterRequest.getPassword())); // Encode the password
        // Fetch and assign the SUPER_ADMIN role
        Role superAdminRole = roleRepository.findRoleByRoleName("SUPER_ADMIN")
                .orElseThrow(() -> new RoleNotFoundException("Role 'SUPER_ADMIN' does not exist."));
        superAdmin.setRoles(Collections.singletonList(superAdminRole));
        // Save the SuperAdmin user
        SuperAdmin savedSuperAdmin = superAdminRepository.save(superAdmin);
        // Return the new response object
        return superAdminRegisterMapper.toResponse(savedSuperAdmin);
    }
                    // Logs in a SuperAdmin by validating credentials.
                    @Override
                    public SuperAdminLoginResponse loginSuperAdmin(SuperAdminLoginRequest loginRequest) throws AuthenticationFailedException {
                        UserDetails userDetails;
                        try {
                            // Attempt to load super admin details
                            userDetails = customUserDetailsService.loadUserByUsername(loginRequest.getUsername());
                        } catch (UsernameNotFoundException e) {
                            throw new AuthenticationFailedException("Authentication failed: SuperAdmin not found.");
                        }

                        // Check if the user has the 'SUPER_ADMIN' role
                        if (!userDetails.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("SUPER_ADMIN"))) {
                            throw new AuthenticationFailedException("Authentication failed: Only SuperAdmins can login using this form.");
                        }

                        // Verify the password
                        if (!passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword())) {
                            throw new AuthenticationFailedException("Authentication failed: Invalid password.");
                        }

                        // Store super admin information in the HTTP session
                        HttpSession session = request.getSession();
                        session.setAttribute("superadmin", userDetails.getUsername());

                        // Create and return the response for SuperAdmin login
                        SuperAdmin superAdmin = new SuperAdmin(); // Create a SuperAdmin instance for the response
                        superAdmin.setUsername(userDetails.getUsername()); // Set the username
                        return superAdminLoginMapper.toResponse(superAdmin); // Map to SuperAdminLoginResponse
                    }

    // --------------------------------------------------------------------------------------------------------------

    // Registers a new regular user.
    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        // Ensure that UserRepository exists and is used to save normal users
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new UserAlreadyExistsException("A user with this username already exists.");
        }
        // Check if the email is already used
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new EmailAlreadyExistsException("A user with this email already exists.");
        }
        User user = registerMapper.fromRequest(registerRequest);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword())); // Encode the password
        // Fetch and assign the "USER" role
        Role userRole = roleRepository.findRoleByRoleName("USER")
                .orElseThrow(() -> new RoleNotFoundException("Default role 'USER' does not exist."));
        user.setRoles(Collections.singletonList(userRole));
        // Save the user
        User savedUser = userRepository.save(user);
        // Return the newly registered user-specific response
        return registerMapper.toResponse(savedUser);
    }
                    // Logs in a regular user by validating credentials.
                    @Override
                    public LoginResponse login(LoginRequest loginRequest) throws AuthenticationFailedException {
                        UserDetails userDetails;
                        try {
                            // Attempt to load user details
                            userDetails = customUserDetailsService.loadUserByUsername(loginRequest.getUsername());
                        } catch (UsernameNotFoundException e) {
                            throw new AuthenticationFailedException("Authentication failed: User not found.");
                        }

                        // Check if the user has the 'SUPER_ADMIN' role
                        if (userDetails.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("SUPER_ADMIN"))) {
                            throw new AuthenticationFailedException("Authentication failed: SuperAdmins can not login using this form.");
                        }

                        // Verify the password
                        if (!passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword())) {
                            throw new AuthenticationFailedException("Authentication failed: Invalid password.");
                        }

                        // Store user information in the HTTP session
                        HttpSession session = request.getSession();
                        session.setAttribute("user", userDetails.getUsername());

                        // Create and return the response for user login
                        User user = new User(); // Assuming you want to create a User instance for the response
                        user.setUsername(userDetails.getUsername()); // Set the username
                        return loginMapper.toResponse(user); // Map to LoginResponse
                    }


    // --------------------------------------------------------------------------------------------------------------

    // Handles user logout. Actual logic can be managed in the client (React component).
    @Override
    public void logout(String username) {
        // Invalidate the session or remove the user attribute
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // End the session
        }
    }
}
