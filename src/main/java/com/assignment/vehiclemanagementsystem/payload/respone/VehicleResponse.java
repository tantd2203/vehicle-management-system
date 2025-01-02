package com.assignment.vehiclemanagementsystem.payload.respone;

import com.assignment.vehiclemanagementsystem.constant.FuelType;
import com.assignment.vehiclemanagementsystem.constant.VehicleType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class VehicleResponse  implements Serializable {
    private String make;
    private String model;
    private int year;
    private VehicleType type;
    private String licensePlate;
    private String identificationNumber;
    private String color;
    private FuelType fuelType;
    private String status;
    private String ownerUsername;
}
