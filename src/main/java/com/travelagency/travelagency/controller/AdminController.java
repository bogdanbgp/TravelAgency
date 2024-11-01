//package com.travelagency.travelagency.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/admin")
//public class AdminController {
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Autowired
//    private JwtService jwtService;
//
//    // Admin login
//    @PostMapping("/login")
//    public ResponseEntity<?> adminLogin(@RequestBody AdminLoginRequest loginRequest) {
//        try {
//            // Authenticate the admin
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//            // Load user details
//            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
//            // Generate JWT token
//            String jwtToken = jwtService.generateToken(userDetails.getUsername());
//            return ResponseEntity.ok(new JwtResponse(jwtToken));
//        } catch (Exception e) {
//            return ResponseEntity.status(401).body("Invalid credentials");
//        }
//    }
//
//    // Admin-specific functionalities can be added here
//
//    // Class to handle login requests
//    public static class AdminLoginRequest {
//        private String username;
//        private String password;
//
//        // Getters and Setters
//        public String getUsername() {
//            return username;
//        }
//
//        public void setUsername(String username) {
//            this.username = username;
//        }
//
//        public String getPassword() {
//            return password;
//        }
//
//        public void setPassword(String password) {
//            this.password = password;
//        }
//    }
//}
