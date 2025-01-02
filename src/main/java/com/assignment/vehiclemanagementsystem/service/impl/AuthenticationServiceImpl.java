package com.assignment.vehiclemanagementsystem.service.impl;

import com.assignment.vehiclemanagementsystem.constant.Role;
import com.assignment.vehiclemanagementsystem.entity.User;
import com.assignment.vehiclemanagementsystem.entity.UserSessionToken;
import com.assignment.vehiclemanagementsystem.payload.request.RegisterRequest;
import com.assignment.vehiclemanagementsystem.payload.request.SignInRequest;
import com.assignment.vehiclemanagementsystem.payload.respone.TokenResponse;
import com.assignment.vehiclemanagementsystem.repository.UserRepository;
import com.assignment.vehiclemanagementsystem.service.AuthenticationService;
import com.assignment.vehiclemanagementsystem.service.JWTService;
import com.assignment.vehiclemanagementsystem.service.TokenRedisService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
 * @author: TanTD1
 * @since: 06/08/2024 11:25
 * @description: AuthenticationServiceImpl handles all business logic related to authentication
 * @update:
 *
 * */
@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public TokenResponse authenticate(SignInRequest signInRequest) {
//       Authentication in UserDetail if success  execute step second
        log.info("Authenticating service with userName: {}", signInRequest.getUsername());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword()));
        var user = userRepository.findByUsername(signInRequest.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);


        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getId()).build();
    }

    @Override
    public long register(RegisterRequest registerRequest) {

        var user = User.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .phoneNumber(registerRequest.getPhoneNumber())
                .fullName(registerRequest.getFullName())
                .email(registerRequest.getEmail())
                .deviceID(registerRequest.getDeviceID())
                .role(Role.User)
                .enabled(Boolean.TRUE)
                .build();
        userRepository.save(user);
        log.info("User has added successfully, userId={}", user.getId());
        return user.getId();
    }

    @Override
    public TokenResponse refreshToken(HttpServletRequest request) {
        String token = request.getHeader("x-token");
        if (StringUtils.isBlank(token)) {
            throw new IllegalArgumentException("Token is required");
        }
        final  String userName = jwtService.extractUsername(token);
        Optional<User> user = userRepository.findByUsername(userName);

        if (!jwtService.validateToken(token, user.get())) {
            throw new IllegalArgumentException("Invalid token");
        }
        String accessToken = jwtService.generateToken(user.get());
        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(token)
                .userId(user.get().getId()).build();
    }
}
