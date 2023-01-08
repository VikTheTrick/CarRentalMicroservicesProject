package com.example.rentalservice.service.impl;

import com.example.rentalservice.dto.VehicleCreateDto;
import com.example.rentalservice.dto.VehicleDto;
import com.example.rentalservice.mapper.VehicleMapper;
import com.example.rentalservice.model.Vehicle;
import com.example.rentalservice.repository.VehicleRepository;
import com.example.rentalservice.service.VehicleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class VehicleServiceImplementation implements VehicleService {

    private VehicleRepository vehicleRepository;
    private VehicleMapper vehicleMapper;

    public VehicleServiceImplementation(VehicleRepository vehicleRepository, VehicleMapper vehicleMapper) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleMapper = vehicleMapper;
    }

    @Override
    public VehicleDto addVehicle(VehicleCreateDto vehicleCreateDto) {
        Vehicle vehicle = vehicleMapper.vehicleCreateDtoToVehicle(vehicleCreateDto);
        vehicleRepository.save(vehicle);
        return vehicleMapper.vehicleToVehicleDto(vehicle);
    }

    @Override
    public void deleteVehicle(Long id) {
        vehicleRepository.deleteById(id);
    }

    @Override
    public Page<VehicleDto> findAll(Pageable pageable) {
        return vehicleRepository.findAll(pageable).map(vehicleMapper::vehicleToVehicleDto);
    }
}
