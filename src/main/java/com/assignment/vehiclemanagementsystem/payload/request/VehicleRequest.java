package com.assignment.vehiclemanagementsystem.payload.request;

import com.assignment.vehiclemanagementsystem.constant.FuelType;
import com.assignment.vehiclemanagementsystem.constant.VehicleType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VehicleRequest {
    @NotBlank(message = "Make is required")
    private String make;
    @NotBlank(message = "Model is required")
    private String model;
    @Min(value = 1886, message = "Year should not be less than 1886")
    private int year;
    @NotBlank(message = "Type is required")
    private VehicleType type;
    @NotBlank(message = "License Plate is required")
    private String licensePlate;
    @NotBlank(message = "Identification Number is required")
    private String identificationNumber;
    @NotBlank(message = "Color is required")
    private String color;
    @NotBlank(message = "Fuel Type is required")
    private FuelType fuelType;
    @NotBlank(message = "Status is required")
    private String status;
}
