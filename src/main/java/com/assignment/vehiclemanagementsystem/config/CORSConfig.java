package com.assignment.vehiclemanagementsystem.config;

import io.micrometer.common.lang.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
//public class CORSConfig {
//
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(@NonNull CorsRegistry registry) {
//                registry.addMapping("**")
//                        .allowedOrigins("http://localhost:8500")
//                        .allowedMethods("GET", "POST", "PUT", "DELETE") // Allowed HTTP methods
//                        .allowedHeaders("*") // Allowed request headers
//                        .allowCredentials(false)
//                        .maxAge(3600);
//            }
//        };
//    }
//}
