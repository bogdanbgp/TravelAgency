//package com.travelagency.travelagency.configuration;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
///*
// * JwtRequestFilter is a filter that intercepts incoming requests to validate the JWT token.
// * It extracts the token from the request header, verifies its authenticity,
// * and sets the authentication in the security context if valid.
// */
//@Component
//public class JwtRequestFilter extends OncePerRequestFilter {
//
//    private final JwtUtil jwtUtil; // Utility class for JWT operations
//    private final UserDetailsService userDetailsService; // Service to load user-specific data
//
//    @Autowired
//    public JwtRequestFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
//        this.jwtUtil = jwtUtil;
//        this.userDetailsService = userDetailsService;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain chain) throws ServletException, IOException {
//        final String authorizationHeader = request.getHeader("Authorization");
//
//        String username = null;
//        String jwt = null;
//
//        // Check if the Authorization header is present and starts with "Bearer "
//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            jwt = authorizationHeader.substring(7); // Extract JWT token
//            username = jwtUtil.extractUsername(jwt); // Extract username from the token
//        }
//
//        // If a valid username is extracted from the token
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            // Load user details using the UserDetailsService
//            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
//            // Validate the token
//            if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
//                // Create an authentication token and set it in the security context
//                UsernamePasswordAuthenticationToken authToken =
//                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//            }
//        }
//
//        // Proceed with the filter chain
//        chain.doFilter(request, response);
//    }
//}
