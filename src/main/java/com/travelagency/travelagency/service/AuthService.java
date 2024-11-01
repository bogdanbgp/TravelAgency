package com.travelagency.travelagency.service;

import com.travelagency.travelagency.dto.request.auth.LoginRequest; // Ensure this class exists
import com.travelagency.travelagency.dto.request.auth.RegisterRequest; // Ensure this class exists
import com.travelagency.travelagency.dto.request.auth.SuperAdminLoginRequest; // Include this class for super admin login
import com.travelagency.travelagency.dto.request.auth.SuperAdminRegisterRequest; // Ensure this class exists
import com.travelagency.travelagency.dto.response.auth.LoginResponse; // Ensure this class exists
import com.travelagency.travelagency.dto.response.auth.RegisterResponse; // Ensure this class exists
import com.travelagency.travelagency.dto.response.auth.SuperAdminLoginResponse; // Include this class for super admin login response
import com.travelagency.travelagency.dto.response.auth.SuperAdminRegisterResponse; // Ensure this class exists
import com.travelagency.travelagency.exception.auth.AuthenticationFailedException; // Ensure this class exists
import com.travelagency.travelagency.exception.user.UserAlreadyExistsException; // Ensure this class exists
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AuthService {
    // -------------------------------------------------------------------
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException; // Load user by username
    // -------------------------------------------------------------------
    SuperAdminRegisterResponse registerSuperAdmin(SuperAdminRegisterRequest superAdminRegisterRequest) throws UserAlreadyExistsException; // Register a new super admin
    // -------------------------------------------------------------------
    SuperAdminLoginResponse loginSuperAdmin(SuperAdminLoginRequest loginRequest) throws AuthenticationFailedException; // Log in a super admin and return a JWT token
    // -------------------------------------------------------------------
    RegisterResponse register(RegisterRequest registerRequest) throws UserAlreadyExistsException; // Register a new user
    // -------------------------------------------------------------------
    LoginResponse login(LoginRequest loginRequest) throws AuthenticationFailedException; // Log in a user and return a JWT token
    // -------------------------------------------------------------------
    void logout(String token); // Log out a user
}
