package com.example.rentalservice.service;

import com.example.rentalservice.dto.VehicleCreateDto;
import com.example.rentalservice.dto.VehicleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VehicleService {
    VehicleDto addVehicle(VehicleCreateDto reservationCreateDto);
    void deleteVehicle(Long id);
    Page<VehicleDto> findAll(Pageable pageable);
}
