package com.assignment.vehiclemanagementsystem.service;


import com.assignment.vehiclemanagementsystem.entity.MaintenanceRecord;
import com.assignment.vehiclemanagementsystem.entity.Vehicle;
import com.assignment.vehiclemanagementsystem.payload.request.MaintenanceRecordRequest;
import com.assignment.vehiclemanagementsystem.repository.MaintenanceRecordRepository;
import com.assignment.vehiclemanagementsystem.repository.VehicleRepository;
import com.assignment.vehiclemanagementsystem.service.impl.MaintenanceRecordServiceImpl;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class MaintenanceRecordServiceImplTest {

    @Mock
    private MaintenanceRecordRepository maintenanceRecordRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private MaintenanceRecordServiceImpl maintenanceRecordService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate_Maintenance_Success() throws BadRequestException {
        MaintenanceRecordRequest request = MaintenanceRecordRequest.builder()
                .vehicleId(1L)
                .cost(100.0)
                .description("Oil change")
                .serviceDate(LocalDate.now())
                .build();

        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);

        MaintenanceRecord savedRecord = new MaintenanceRecord();
        savedRecord.setId(1L); // Ensure the ID is set

        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));
        when(maintenanceRecordRepository.save(any(MaintenanceRecord.class))).thenReturn(savedRecord);

        Long recordId = maintenanceRecordService.create(request);

        assertEquals(1L, recordId);
    }


    /*
    * @author: TanTD1
    * @since: 8/7/2024 9:55 PM
    * @description:  Test create maintenance record with missing vehicle id
    * @update:
    *
    * */
    @Test
    public void testCreate_VehicleNotFound() {
        MaintenanceRecordRequest request =  MaintenanceRecordRequest.builder()
                .vehicleId(1L).build();
        when(vehicleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> maintenanceRecordService.create(request));
    }

    @Test
    public void testUpdate_Success() throws BadRequestException {
        MaintenanceRecordRequest request =  MaintenanceRecordRequest.builder()
                .vehicleId(1L)
                .cost(100.0)
                .description("Oil change")
                .serviceDate(LocalDate.now())
                .build();

        MaintenanceRecord record = new MaintenanceRecord();
        record.setId(1L);

        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);

        when(maintenanceRecordRepository.findById(1L)).thenReturn(Optional.of(record));
        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));

        maintenanceRecordService.update(1L, request);
    }

    @Test
    public void testUpdate_RecordNotFound() {
        MaintenanceRecordRequest request =  MaintenanceRecordRequest.builder()
                .vehicleId(1L)
                .build();

        when(maintenanceRecordRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> maintenanceRecordService.update(1L, request));
    }

    @Test
    public void testUpdate_VehicleNotFound() {
        MaintenanceRecordRequest request =  MaintenanceRecordRequest.builder()
                .vehicleId(1L)
                .build();
        MaintenanceRecord record = new MaintenanceRecord();
        record.setId(1L);

        when(maintenanceRecordRepository.findById(1L)).thenReturn(Optional.of(record));
        when(vehicleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> maintenanceRecordService.update(1L, request));
    }
}