package com.assignment.vehiclemanagementsystem.controller;

import com.assignment.vehiclemanagementsystem.entity.MaintenanceRecord;
import com.assignment.vehiclemanagementsystem.payload.request.MaintenanceRecordRequest;
import com.assignment.vehiclemanagementsystem.payload.respone.ResponseData;
import com.assignment.vehiclemanagementsystem.service.MaintenanceRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/maintenance")
@Slf4j
@RequiredArgsConstructor
public class MaintenanceRecordController {

    private final MaintenanceRecordService manMaintenanceRecordService;


    @PostMapping("")
    public ResponseData<Long> create(@RequestBody MaintenanceRecordRequest request) throws BadRequestException {
        return new ResponseData<> (HttpStatus.OK.value(), "Create maintenance record successful", manMaintenanceRecordService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseData<MaintenanceRecord> update(@PathVariable Long id, @RequestBody MaintenanceRecordRequest request) throws BadRequestException {
        manMaintenanceRecordService.update(id, request);
        return new ResponseData<>(HttpStatus.OK.value(), "Update maintenance record successful");
    }
}
