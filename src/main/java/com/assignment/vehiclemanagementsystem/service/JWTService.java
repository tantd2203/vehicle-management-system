package com.assignment.vehiclemanagementsystem.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;

public interface JWTService {
    String generateToken(UserDetails userDetails);
    String generateRefreshToken(UserDetails userDetails);
    boolean validateToken(String token, UserDetails userDetails);
    String extractUsername(String token);
    Key getSigningKey();
}
