//package com.travelagency.travelagency.configuration;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * JwtUtil is a utility class for managing JSON Web Tokens (JWT).
// * It provides methods for generating, validating, and extracting information
// * from JWT tokens. This class uses a secret key to sign the tokens, ensuring
// * their integrity and authenticity.
// *
// * The secret key should be a strong, unpredictable value known only to the server.
// * It is used to create a digital signature for the tokens, preventing tampering.
// */
//@Component
//public class JwtUtil {
//
//    private final String SECRET_KEY = "your_secret_key"; // Replace with your actual secret key
//
//    // Generate token for user with a single role
//    public String generateToken(UserDetails userDetails) {
//        Map<String, Object> claims = new HashMap<>();
//
//        // Get the first role as a single role for the claim
//        String role = userDetails.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .findFirst() // Get only the first role
//                .orElse("USER_UNVERIFIED"); // Default role is USER_UNVERIFIED
//
//        claims.put("role", role); // Add the single role to claims
//        return createToken(claims, userDetails.getUsername());
//    }
//
//    private String createToken(Map<String, Object> claims, String subject) {
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(subject)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
//                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // Sign the token with the secret key
//                .compact();
//    }
//
//    // Validate token
//    public Boolean validateToken(String token, String username) {
//        final String extractedUsername = extractUsername(token);
//        return (extractedUsername.equals(username) && !isTokenExpired(token));
//    }
//
//    // Extract username from token
//    public String extractUsername(String token) {
//        return extractAllClaims(token).getSubject();
//    }
//
//    private Claims extractAllClaims(String token) {
//        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
//    }
//
//    // Check if token is expired
//    private Boolean isTokenExpired(String token) {
//        return extractAllClaims(token).getExpiration().before(new Date());
//    }
//
//    // Extract single role from token
//    public String extractRole(String token) {
//        return (String) extractAllClaims(token).get("role");
//    }
//}
