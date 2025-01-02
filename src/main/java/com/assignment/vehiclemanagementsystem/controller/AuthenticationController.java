package com.assignment.vehiclemanagementsystem.controller;

import com.assignment.vehiclemanagementsystem.config.Translator;
import com.assignment.vehiclemanagementsystem.payload.request.RegisterRequest;
import com.assignment.vehiclemanagementsystem.payload.request.SignInRequest;
import com.assignment.vehiclemanagementsystem.payload.respone.ResponseData;
import com.assignment.vehiclemanagementsystem.payload.respone.TokenResponse;
import com.assignment.vehiclemanagementsystem.service.AuthenticationService;
import com.assignment.vehiclemanagementsystem.service.TokenRedisService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping(value = "/register", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseData<Long> register(
            @Valid
            @RequestBody RegisterRequest request
    ) {
        log.info("Registering user with userName: {}", request.getUsername());
        try {
            Long result = service.register(request);
            return new ResponseData<>(HttpStatus.CREATED.value(), "Register success", result);
        } catch (Exception e) {
            return new ResponseData<>(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @PostMapping(value = "/login", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseData<TokenResponse> authenticate(
            @Valid @RequestBody SignInRequest request
    ) {
        log.info("Authenticating user with userName: {}", request.getUsername());
        return new ResponseData<>(HttpStatus.OK.value(), "Login successfully", service.authenticate(request));

    }


    @PostMapping(value = "/refreshToken", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseData<TokenResponse> refreshToken(
            HttpServletRequest request

    ) {
        return new ResponseData<>(HttpStatus.OK.value(), "refresh", service.refreshToken(request));

    }


}