package com.example.rentalservice.repository;

import com.example.rentalservice.model.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleTypeRepository extends JpaRepository<VehicleType,Long> {
}
