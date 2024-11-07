package com.travelagency.travelagency.controller;

import com.travelagency.travelagency.dto.request.auth.LoginRequest;
import com.travelagency.travelagency.dto.request.auth.RegisterRequest;
import com.travelagency.travelagency.dto.request.auth.SuperAdminLoginRequest;
import com.travelagency.travelagency.dto.request.auth.SuperAdminRegisterRequest;
import com.travelagency.travelagency.dto.response.auth.RegisterResponse;
import com.travelagency.travelagency.dto.response.auth.LoginResponse;
import com.travelagency.travelagency.dto.response.auth.SuperAdminLoginResponse;
import com.travelagency.travelagency.dto.response.auth.SuperAdminRegisterResponse;
import com.travelagency.travelagency.exception.auth.AuthenticationFailedException;
import com.travelagency.travelagency.exception.role.RoleNotFoundException;
import com.travelagency.travelagency.exception.user.UserAlreadyExistsException;
import com.travelagency.travelagency.service.AuthService;
import com.travelagency.travelagency.mapper.LoginMapper; // Import your LoginMapper
import com.travelagency.travelagency.mapper.SuperAdminLoginMapper; // Import your SuperAdminLoginMapper
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * AuthController handles authentication-related requests for registration, login, and logout.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private LoginMapper loginMapper; // Inject LoginMapper

    @Autowired
    private SuperAdminLoginMapper superAdminLoginMapper; // Inject SuperAdminLoginMapper

    // ----------------------------------------------------------------------------------------------------------------
    // Handles super admin registration
    @PostMapping("/register/superadmin")
    public ResponseEntity<SuperAdminRegisterResponse> registerSuperAdmin(@RequestBody SuperAdminRegisterRequest superAdminRegisterRequest) {
        try {
            // Call the service to register the super admin
            SuperAdminRegisterResponse response = authService.registerSuperAdmin(superAdminRegisterRequest);
            return ResponseEntity.ok(response); // Return 200 OK with successful response
        } catch (UserAlreadyExistsException e) {
            // Handle case when the super admin username already exists
            return buildErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RoleNotFoundException e) {
            // Handle case when the SUPER_ADMIN role does not exist
            return buildErrorResponse("Role 'SUPER_ADMIN' does not exist.", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            // Handle any other unexpected exceptions
            return buildErrorResponse("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    private ResponseEntity<SuperAdminRegisterResponse> buildErrorResponse(String message, HttpStatus status) {
        SuperAdminRegisterResponse errorResponse = new SuperAdminRegisterResponse();
        errorResponse.setMessage(message); // Set the error message in response
        return ResponseEntity.status(status).body(errorResponse); // Return with appropriate status
    }
                    // Handles superadmin login
                    @PostMapping("/login/superadmin")
                    public ResponseEntity<SuperAdminLoginResponse> loginSuperAdmin(@RequestBody SuperAdminLoginRequest superAdminLoginRequest) {
                        try {
                            SuperAdminLoginResponse response = authService.loginSuperAdmin(superAdminLoginRequest); // Call the service layer to log in the super admin
                            return ResponseEntity.ok(response); // Return 200 OK with successful response
                        } catch (AuthenticationFailedException e) {
                            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                    .body(new SuperAdminLoginResponse(e.getMessage(), null)); // Return 401 Unauthorized with error message
                        }
                    }

    // ----------------------------------------------------------------------------------------------------------------
    // Handles user registration
    @PostMapping("/register/user")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest registerRequest) {
        try {
            // Call the service to register the user
            RegisterResponse response = authService.register(registerRequest);
            return ResponseEntity.ok(response); // Return 200 OK with registration success details
        } catch (UserAlreadyExistsException e) {
            // Handle case when the username or email is already taken
            return ResponseEntity.badRequest().body(new RegisterResponse(
                    e.getMessage(), null, null, null, 0, null, null)); // Return 400 Bad Request
        }
    }
                    // Handles user login
                    @PostMapping("/login/user")
                    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
                        try {
                            LoginResponse response = authService.login(loginRequest); // Call the service layer to log in the user
                            return ResponseEntity.ok(response); // Return 200 OK with successful response
                        } catch (AuthenticationFailedException e) {
                            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                    .body(new LoginResponse(e.getMessage())); // Use the new constructor that only takes a message
                        }
                    }



    // ----------------------------------------------------------------------------------------------------------------
    // Handles user logout
    @PostMapping("/api/auth/logout")
    public void logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            // Perform any necessary cleanup (e.g., logging, invalidating sessions, etc.)
            // This can also handle role-specific logout logic if needed
        }

        // Invalidate the session or clear the authentication
        SecurityContextHolder.clearContext();
    }
}
