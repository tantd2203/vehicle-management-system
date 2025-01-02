package com.assignment.vehiclemanagementsystem.controller;

import com.assignment.vehiclemanagementsystem.payload.request.VehicleRequest;
import com.assignment.vehiclemanagementsystem.payload.respone.ResponseData;
import com.assignment.vehiclemanagementsystem.service.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/*
* @author: TanTD1
* @since: 06/08/2024 17:16
* @description:   Vehicle Controller for handling vehicle request
* @update:
*
* */

@RestController
@RequestMapping("/vehicle")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Vehicle", description = "Vehicle API")
public class VehicleController {

    private final VehicleService vehicleService;

    @Operation(summary = "Get vehicle by id" , description = "Get vehicle by id")
    @GetMapping("/{id}")
    public ResponseData<?> getVehicleById(@PathVariable Long id) {
        try {
            return new ResponseData<>(HttpStatus.OK.value(), "Get vehicle successful", vehicleService.getVehicleById(id));
        }catch (Exception e){
            return new ResponseData<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), "Get vehicle fail");
        }
    }
    @Operation(summary = "Get all vehicle" , description = "Get all vehicle")
    @GetMapping("/list")
    public ResponseData<?> getAllVehicles() {
        try {
            return new ResponseData<>(HttpStatus.OK.value(), "Get all vehicle successful", vehicleService.getAllVehicles());
        }catch (Exception e){
            return new ResponseData<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), "Get all vehicle fail");
        }
    }
    @Operation(summary = "Create vehicle" , description = "Create vehicle")
    @PostMapping("")
    public ResponseData<?> createVehicle(@RequestBody VehicleRequest request) throws BadRequestException {
        log.info("Create vehicle request: {}", request);
        try {
            return new ResponseData<>(HttpStatus.CREATED.value(), "Create vehicle successful", vehicleService.saveVehicle(request));
        }catch (Exception e){
          return  new ResponseData<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(),"Create vehicle fail");
        }
    }

    @Operation(summary = "Update vehicle" , description = "Update vehicle")
    @PutMapping("/{id}")
    public ResponseData<?> updateVehicle(@PathVariable Long id, @Valid @RequestBody VehicleRequest request)  {
        try {
            vehicleService.updateVehicle(id, request);
            return new ResponseData<>(HttpStatus.ACCEPTED.value(), "Update user success");
        }catch (Exception e){
            return new ResponseData<>(HttpStatus.BAD_REQUEST.value(), "Update user fail");
        }
    }
    @DeleteMapping("/{id}")
    public ResponseData<?> deleteVehicle(@PathVariable Long id)  {
        try {
            vehicleService.deleteVehicle(id);
            return new ResponseData<>(HttpStatus.ACCEPTED.value(), "delete user success");
        }catch (Exception e){
            return new ResponseData<>(HttpStatus.BAD_REQUEST.value(), "delete user fail");
        }
    }

}
