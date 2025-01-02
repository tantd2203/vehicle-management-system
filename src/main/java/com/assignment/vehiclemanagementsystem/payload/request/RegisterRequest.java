package com.assignment.vehiclemanagementsystem.payload.request;

import com.assignment.vehiclemanagementsystem.annotion.PhoneNumber;
import com.assignment.vehiclemanagementsystem.constant.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@Builder
public class RegisterRequest implements Serializable {
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Password is required")
    private String password;
    @PhoneNumber
    private String phoneNumber;
    @NotBlank(message = "Full name is required")
    private String fullName;
    @Email
    private String email;
    @NotBlank(message = "DeviceID is required")
    private String deviceID;

}