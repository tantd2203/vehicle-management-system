package com.assignment.vehiclemanagementsystem.service.impl;

import com.assignment.vehiclemanagementsystem.entity.MaintenanceRecord;
import com.assignment.vehiclemanagementsystem.entity.Vehicle;
import com.assignment.vehiclemanagementsystem.payload.request.MaintenanceRecordRequest;
import com.assignment.vehiclemanagementsystem.repository.MaintenanceRecordRepository;
import com.assignment.vehiclemanagementsystem.repository.VehicleRepository;
import com.assignment.vehiclemanagementsystem.service.MaintenanceRecordService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MaintenanceRecordServiceImpl implements MaintenanceRecordService {
    private final MaintenanceRecordRepository maintenanceRecordRepository;

    private final VehicleRepository vehicleRepository;


    @Override
    public List<MaintenanceRecord> getAllByVehicleId(Long vehicleId) {

        return maintenanceRecordRepository.findAllByVehicleId(vehicleId);
    }

    @Override
    public long create(MaintenanceRecordRequest request) throws BadRequestException {
        if (request.getVehicleId() == null) {
            throw new BadRequestException("Request must have vehicle id!");
        }

        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(request.getVehicleId());

        if (optionalVehicle.isEmpty()) {
            throw new BadRequestException("Vehicle is not exits!");
        }

        MaintenanceRecord record = MaintenanceRecord.builder()
                .cost(request.getCost()).description(request.getDescription())
                .serviceDate(request.getServiceDate()).vehicle(optionalVehicle.get()).build();

        return maintenanceRecordRepository.save(record).getId().longValue();
    }

    @Override
    public void update(Long recordId, MaintenanceRecordRequest request) throws BadRequestException {
        Optional<MaintenanceRecord> recordOptional = maintenanceRecordRepository.findById(recordId);

        if (recordOptional.isEmpty()) {
            throw new BadRequestException("Maintenance record does not exist!");
        }

        if (request.getVehicleId() == null) {
            throw new BadRequestException("Request must have vehicle id!");
        }

        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(request.getVehicleId());

        if (optionalVehicle.isEmpty()) {
            throw new BadRequestException("Vehicle does not exist!");
        }

        MaintenanceRecord record = recordOptional.get();

        record.setCost(request.getCost());
        record.setDescription(request.getDescription());
        record.setServiceDate(request.getServiceDate());
        record.setVehicle(optionalVehicle.get());
        maintenanceRecordRepository.save(record);
    }
}
