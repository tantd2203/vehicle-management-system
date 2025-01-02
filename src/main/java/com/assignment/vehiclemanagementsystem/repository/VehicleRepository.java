package com.assignment.vehiclemanagementsystem.repository;

import com.assignment.vehiclemanagementsystem.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Optional<Vehicle> findById(Long id);


    List<Vehicle> findByEnabled(boolean b);

    Vehicle findVehicleByEnabledAndAndId(boolean b, Long id);
}
