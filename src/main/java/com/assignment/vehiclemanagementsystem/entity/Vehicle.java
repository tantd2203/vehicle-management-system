package com.assignment.vehiclemanagementsystem.entity;

import com.assignment.vehiclemanagementsystem.constant.FuelType;
import com.assignment.vehiclemanagementsystem.constant.VehicleType;
import com.assignment.vehiclemanagementsystem.entity.auth.AbstractEntityAuth;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_vehicle")
public class Vehicle extends AbstractEntity<Long> {

    @Column(name = "make")
    private String make;

    @Column(name = "model")
    private String model;

    @Column(name = "year")
    private int year;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private VehicleType type;

    @CreationTimestamp
    @Column(name = "created_date")
    private Date createdDate;

    @UpdateTimestamp
    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name = "license_plate", unique = true)
    private String licensePlate;

    //Vehicle Identification Number
    @Column(name = "identification_number", unique = true)
    private String identificationNumber;

    @Column(name = "color")
    private String color;

    @Column(name = "fuel_type")
    @Enumerated(EnumType.STRING)
    private FuelType fuelType;

    @Column(name = "status")
    private String status;

    @Column(name = "enabled")
    private Boolean enabled;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MaintenanceRecord> maintenanceRecords = new ArrayList<>();
}
