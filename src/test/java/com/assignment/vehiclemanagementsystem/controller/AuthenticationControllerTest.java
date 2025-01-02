package com.assignment.vehiclemanagementsystem.controller;

import com.assignment.vehiclemanagementsystem.exection.ErrorResponse;
import com.assignment.vehiclemanagementsystem.exection.GlobalExceptionHandler;
import com.assignment.vehiclemanagementsystem.payload.request.RegisterRequest;
import com.assignment.vehiclemanagementsystem.payload.request.SignInRequest;
import com.assignment.vehiclemanagementsystem.payload.respone.ResponseData;
import com.assignment.vehiclemanagementsystem.payload.respone.TokenResponse;
import com.assignment.vehiclemanagementsystem.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/*
* @author: TanTD1
* @since: 8/7/2024 4:35 PM
* @description:   Test AuthenticationController
* @update:
*
* */
@Slf4j
public class AuthenticationControllerTest {



    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthenticationController authenticationController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAuthenticate_Success() {
        SignInRequest request = new SignInRequest();
        request.setUsername("testuser");
        request.setPassword("password");

        var tokenResponse = TokenResponse.builder().accessToken("cute").build();

        when(authenticationService.authenticate(any(SignInRequest.class))).thenReturn(tokenResponse);

        ResponseData<TokenResponse> response = authenticationController.authenticate(request);

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("Login successfully", response.getMessage());
        assertEquals(tokenResponse, response.getData());
    }



    @Test
    public void testAuthenticate_EmptyUsername() {
        SignInRequest request = new SignInRequest();
        request.setUsername("");
        request.setPassword("password");

        when(authenticationService.authenticate(any(SignInRequest.class))).thenThrow(new RuntimeException("userName must be not blank"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authenticationController.authenticate(request);
        });

        System.out.println(exception.toString());
        assertEquals("userName must be not blank", exception.getMessage());
    }

//    @Test
//    public void testAuthenticate_EmptyPassword() {
//        SignInRequest request = new SignInRequest();
//        request.setUsername("testuser");
//        request.setPassword("");
//
////        when(authenticationService.authenticate(any(SignInRequest.class))).thenThrow(new RuntimeException("userName must be not blank"));
//
//        ResponseData<TokenResponse> response = authenticationController.authenticate(request);
//
//        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
//        assertEquals("userName must be not blank", response.getMessage());
//    }

//    @Test
//    public void testAuthenticate_NullRequest() {
//        when(authenticationService.authenticate(any(SignInRequest.class))).thenThrow(new RuntimeException("Request cannot be null"));
//
//        ResponseData<TokenResponse> response = authenticationController.authenticate(null);
//
//        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
//        assertEquals("Request cannot be null", response.getMessage());
//    }
//
//    //  register test cases

    @Test
    public void testRegister_Success() {
        RegisterRequest request = RegisterRequest.builder()
                .username("tancqute")
                .password("123")
                .phoneNumber("0379691867")
                .fullName("tan")
                .email("chien@example.com")
                .deviceID("ABC123456")
                .build();

        when(authenticationService.register(any(RegisterRequest.class))).thenReturn(1L);

        ResponseData<Long> response = authenticationController.register(request);


        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals("Register success", response.getMessage());
        assertEquals(1L, response.getData());
    }

    @Test
    public void testRegister_InvalidData() {
        RegisterRequest request  = RegisterRequest.builder()
                .username("tan")
                .password("password")
                .email("trnaudsa@gmail")
                .fullName("Tan")
                .phoneNumber("0379691862")
                .deviceID("123")
                .build();
        when(authenticationService.register(any(RegisterRequest.class))).thenThrow(new RuntimeException("Invalid data"));

        ResponseData<Long> response = authenticationController.register(request);


        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("Invalid data", response.getMessage());
    }

    @Test
    public void testRegister_Exception() {
        RegisterRequest request  = RegisterRequest.
                builder()
                .username("tan")
                .password("password")
                .build();

        when(authenticationService.register(any(RegisterRequest.class))).thenThrow(new RuntimeException("Exception occurred"));

        ResponseData<Long> response = authenticationController.register(request);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("Exception occurred", response.getMessage());
    }
}
