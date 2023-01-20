package com.example.rentalservice.repository;

import com.example.rentalservice.model.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle,Long> {
    Page<Vehicle> findByType_id(Long typeid, Pageable pageable);
}
