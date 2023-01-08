package com.example.rentalservice.service;

import com.example.rentalservice.dto.VehicleTypeCreateDto;
import com.example.rentalservice.dto.VehicleTypeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VehicleTypeService {
    VehicleTypeDto addVehicleType(VehicleTypeCreateDto reservationCreateDto);

    Page<VehicleTypeDto> findAll(Pageable pageable);
    void deleteVehicleType(Long id);
}
