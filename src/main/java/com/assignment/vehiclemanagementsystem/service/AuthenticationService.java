
package com.assignment.vehiclemanagementsystem.service;


import com.assignment.vehiclemanagementsystem.payload.request.RegisterRequest;
import com.assignment.vehiclemanagementsystem.payload.request.SignInRequest;
import com.assignment.vehiclemanagementsystem.payload.respone.TokenResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;


public interface AuthenticationService {
    TokenResponse authenticate(SignInRequest signInRequest);

    long register(RegisterRequest registerRequest);

    TokenResponse refreshToken(HttpServletRequest accessToken);
}
