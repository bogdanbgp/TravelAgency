//package com.travelagency.travelagency.configuration;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
///**
// * Configuration class to set up web-related settings for the application.
// * Implements WebMvcConfigurer to provide custom configuration for Spring MVC.
// */
//@Configuration // Indicates that this class contains bean definitions for the application context
//public class WebConfig implements WebMvcConfigurer {
//
//    /**
//     * Configures CORS (Cross-Origin Resource Sharing) settings for the application.
//     * @param registry CorsRegistry object to register CORS mappings
//     */
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**") // Apply CORS settings to all endpoints
//                .allowedOrigins("http://localhost:3000") // Allow requests from this origin (frontend development server)
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow these HTTP methods
//                .allowedHeaders("*") // Allow any headers in the request
//                .allowCredentials(true); // Allow credentials (such as cookies, authorization headers) to be included in the request
//    }
//}
