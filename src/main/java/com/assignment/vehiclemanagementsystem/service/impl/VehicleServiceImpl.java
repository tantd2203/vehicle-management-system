package com.assignment.vehiclemanagementsystem.service.impl;

import com.assignment.vehiclemanagementsystem.entity.User;
import com.assignment.vehiclemanagementsystem.entity.Vehicle;
import com.assignment.vehiclemanagementsystem.payload.request.VehicleRequest;
import com.assignment.vehiclemanagementsystem.payload.respone.VehicleResponse;
import com.assignment.vehiclemanagementsystem.repository.UserRepository;
import com.assignment.vehiclemanagementsystem.repository.VehicleRepository;
import com.assignment.vehiclemanagementsystem.service.VehicleService;
import com.assignment.vehiclemanagementsystem.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * @author: TanTD1
 * @since: 8/7/2024 8:41 AM
 * @description:  VehicleServiceImpl implements VehicleService
 * @update:
 *
 * */
@Service
@RequiredArgsConstructor
@Slf4j
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    private final UserRepository userRepository;


    @Override
    public long saveVehicle(VehicleRequest request) throws BadRequestException {
        String username = SecurityUtils.getCurrentUsername();
        log.info("Saving vehicle for user: {}", username);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new BadRequestException("User not found"));
        var vehicle = Vehicle.builder()
                .color(request.getColor())
                .fuelType(request.getFuelType())
                .type(request.getType())
                .identificationNumber(request.getIdentificationNumber())
                .licensePlate(request.getLicensePlate())
                .make(request.getMake())
                .model(request.getModel())
                .year(request.getYear())
                .status(request.getStatus())
                .owner(user)
                .enabled(Boolean.TRUE)
                .build();
        vehicleRepository.save(vehicle);
        log.info("Vehicle saved successfully with id: {}", vehicle.getId());
        return vehicle.getId();
    }

    @Override
    public void updateVehicle(Long vehicleId, VehicleRequest request) throws BadRequestException {
        validateVehicleId(vehicleId);
        var vehicle = vehicleRepository.findById(vehicleId).orElseThrow(() -> new BadRequestException("Vehicle not found"));
        vehicle.setColor(request.getColor());
        vehicle.setFuelType(request.getFuelType());
        vehicle.setType(request.getType());
        vehicle.setIdentificationNumber(request.getIdentificationNumber());
        vehicle.setLicensePlate(request.getLicensePlate());
        vehicle.setMake(request.getMake());
        vehicle.setModel(request.getModel());
        vehicle.setYear(request.getYear());
        vehicle.setStatus(request.getStatus());
        vehicleRepository.save(vehicle);
        log.info("Vehicle updated successfully with id: {}", vehicle.getId());

    }


    @Override
    public void deleteVehicle(Long vehicleId) {
        log.info("Deleting vehicle with id: {}", vehicleId);
        vehicleRepository.findById(vehicleId).ifPresent(vehicle -> {
            vehicle.setEnabled(Boolean.TRUE);
            vehicleRepository.save(vehicle);
        });
        log.info("Vehicle deleted successfully with id: {}", vehicleId);
    }

    @Override
    public List<VehicleResponse> getAllVehicles() {
        List<Vehicle> vehicles = vehicleRepository.findByEnabled(true);
        log.info("Retrieved all vehicles successfully");
        return vehicles.stream().map(vehicle -> VehicleResponse.builder()
                .color(vehicle.getColor())
                .fuelType(vehicle.getFuelType())
                .type(vehicle.getType())
                .identificationNumber(vehicle.getIdentificationNumber())
                .licensePlate(vehicle.getLicensePlate())
                .make(vehicle.getMake())
                .model(vehicle.getModel())
                .year(vehicle.getYear())
                .status(vehicle.getStatus())
                .ownerUsername(vehicle.getOwner().getUsername())
                .build()).toList();
    }
    @Override
    public VehicleResponse getVehicleById(Long id) throws BadRequestException {
        Vehicle vehicle = vehicleRepository.findVehicleByEnabledAndAndId(true, id);
        if (vehicle == null) {
            throw new BadRequestException("Vehicle not found");
        }
        log.info("Retrieved vehicle successfully with id: {}", vehicle.getId());
        return VehicleResponse.builder()
                .color(vehicle.getColor())
                .fuelType(vehicle.getFuelType())
                .type(vehicle.getType())
                .identificationNumber(vehicle.getIdentificationNumber())
                .licensePlate(vehicle.getLicensePlate())
                .make(vehicle.getMake())
                .model(vehicle.getModel())
                .year(vehicle.getYear())
                .status(vehicle.getStatus())
                .ownerUsername(vehicle.getOwner().getUsername())
                .build();
    }


    private void validateVehicleId(Long vehicleId) throws BadRequestException {
        if (vehicleId <= 0) {
            throw new BadRequestException("Invalid vehicle id");
        }
        if (!vehicleRepository.existsById(vehicleId)) {
            throw new BadRequestException("Vehicle not found");
        }

    }
}
