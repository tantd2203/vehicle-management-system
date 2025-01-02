package com.assignment.vehiclemanagementsystem.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class MaintenanceRecordRequest {
    @NotNull
    private Long vehicleId;

    @NotNull
    private LocalDate serviceDate;

    private String description;

    @NotNull
    private Double cost;
}
