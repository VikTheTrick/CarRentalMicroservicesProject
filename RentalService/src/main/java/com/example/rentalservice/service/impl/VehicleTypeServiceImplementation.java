package com.example.rentalservice.service.impl;

import com.example.rentalservice.dto.VehicleTypeCreateDto;
import com.example.rentalservice.dto.VehicleTypeDto;
import com.example.rentalservice.mapper.VehicleTypeMapper;
import com.example.rentalservice.model.VehicleType;
import com.example.rentalservice.repository.VehicleTypeRepository;
import com.example.rentalservice.service.VehicleTypeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class VehicleTypeServiceImplementation implements VehicleTypeService {
    private VehicleTypeMapper vehicleTypeMapper;
    private VehicleTypeRepository vehicleTypeRepository;

    public VehicleTypeServiceImplementation(VehicleTypeMapper vehicleTypeMapper, VehicleTypeRepository vehicleTypeRepository) {
        this.vehicleTypeMapper = vehicleTypeMapper;
        this.vehicleTypeRepository = vehicleTypeRepository;
    }

    @Override
    public VehicleTypeDto addVehicleType(VehicleTypeCreateDto vehicleTypeCreateDto) {
        VehicleType vehicleType = vehicleTypeMapper.vehicleTypeCreateDtoToVehicle(vehicleTypeCreateDto);
        vehicleTypeRepository.save(vehicleType);
        return vehicleTypeMapper.vehicleTypeToVehicleTypeDto(vehicleType);
    }

    @Override
    public Page<VehicleTypeDto> findAll(Pageable pageable) {
        return vehicleTypeRepository.findAll(pageable).map(vehicleTypeMapper::vehicleTypeToVehicleTypeDto);
    }

    @Override
    public void deleteVehicleType(Long id) {
        vehicleTypeRepository.deleteById(id);
    }
}
