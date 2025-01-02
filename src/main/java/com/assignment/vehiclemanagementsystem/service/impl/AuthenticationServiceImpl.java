package com.assignment.vehiclemanagementsystem.service.impl;

import com.assignment.vehiclemanagementsystem.constant.Role;
import com.assignment.vehiclemanagementsystem.entity.RedisToken;
import com.assignment.vehiclemanagementsystem.entity.User;
import com.assignment.vehiclemanagementsystem.exection.InvalidDataException;
import com.assignment.vehiclemanagementsystem.payload.request.RegisterRequest;
import com.assignment.vehiclemanagementsystem.payload.request.SignInRequest;
import com.assignment.vehiclemanagementsystem.payload.respone.TokenResponse;
import com.assignment.vehiclemanagementsystem.repository.UserRepository;
import com.assignment.vehiclemanagementsystem.service.AuthenticationService;
import com.assignment.vehiclemanagementsystem.service.JWTService;
import com.assignment.vehiclemanagementsystem.service.RedisTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpHeaders.REFERER;

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
    private final RedisTokenService redisTokenService;
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


        ;

        // save token to db
        redisTokenService.save(RedisToken.builder().id(user.getUsername()).accessToken(accessToken).refreshToken(refreshToken).build());
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
        log.info("---------- refreshToken ----------");

        final String refreshToken = request.getHeader(REFERER);


        if (StringUtils.isBlank(refreshToken)) {
            throw new InvalidDataException("Token must be not blank");
        }
        final String userName = jwtService.extractUsername(refreshToken);
        Optional<User> user = userRepository.findByUsername(userName);

        if (!jwtService.validateToken(refreshToken, user.get())) {
            throw new IllegalArgumentException("Invalid token");
        }

        String accessToken = jwtService.generateToken(user.get());

        redisTokenService.save(RedisToken.builder().id(user.get().getUsername()).accessToken(accessToken).refreshToken(refreshToken).build());

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.get().getId()).build();
    }


    public String removeToken(HttpServletRequest request) {
        log.info("---------- removeToken ----------");

        final String token = request.getHeader(REFERER);
        if (StringUtils.isBlank(token)) {
            throw new InvalidDataException("Token must be not blank");
        }

        final String userName = jwtService.extractUsername(token);
        // tokenService.delete(userName);
        redisTokenService.remove(userName);

        return "Removed!";
    }
}
