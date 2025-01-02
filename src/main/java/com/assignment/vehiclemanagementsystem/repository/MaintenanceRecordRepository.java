package com.assignment.vehiclemanagementsystem.repository;

import com.assignment.vehiclemanagementsystem.entity.MaintenanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaintenanceRecordRepository extends JpaRepository<MaintenanceRecord, Long> {
    List<MaintenanceRecord> findAllByVehicleId(Long vehicleId);

}
