//package com.dsatrackeradv.dsa_tracker_adv.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebMvcConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**") // Apply to all endpoints
//                .allowedOrigins("http://localhost:5173") // Allow your frontend origin
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow HTTP methods
//                .allowedHeaders("*") // Allow all headers
//                .allowCredentials(true); // Allow credentials (e.g., cookies, Authorization header)
//    }
//}