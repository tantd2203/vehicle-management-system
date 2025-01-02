package com.assignment.vehiclemanagementsystem.service;

import com.assignment.vehiclemanagementsystem.entity.Vehicle;
import com.assignment.vehiclemanagementsystem.payload.request.VehicleRequest;
import com.assignment.vehiclemanagementsystem.payload.respone.VehicleResponse;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface VehicleService {

    long saveVehicle(VehicleRequest request) throws BadRequestException;

    void updateVehicle(Long vehicleId, VehicleRequest request) throws BadRequestException;

    void deleteVehicle(Long vehicleId);

    List<VehicleResponse>getAllVehicles();

    VehicleResponse getVehicleById(Long id) throws BadRequestException;
}
