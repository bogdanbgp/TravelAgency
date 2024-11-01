package com.travelagency.travelagency.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final UserDetailsService userDetailsService;

    public WebSecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Enable CORS configuration
                .csrf(csrf -> csrf.disable()) // Disable CSRF (for simplicity, adjust as needed)
                .authorizeHttpRequests(authorize -> authorize
                        // Public access to certain GET routes
                        .requestMatchers(HttpMethod.GET, "/api/tours").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/hello").permitAll()

                        // Permit user registration and login for everyone
                        .requestMatchers(HttpMethod.POST, "/api/auth/register/user").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/login/user").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/register/superadmin").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/login/superadmin").permitAll()

                        // Routes requiring authentication
                        .requestMatchers(HttpMethod.POST, "/api/auth/logout").permitAll()

                        // Routes for SUPER_ADMIN
                        .requestMatchers("/api/admin/**").permitAll()

                        // User-specific routes
                        .requestMatchers(HttpMethod.POST, "/api/user/tours/book").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/user/me").permitAll()

                        // Ensure all other requests require authentication
                        .anyRequest().permitAll() // Change to require authentication
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // Allow frontend origin
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Allow methods
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With"));
        configuration.setAllowCredentials(true); // Allow credentials like cookies

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Apply the configuration globally
        return source;
    }
}
