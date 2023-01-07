package com.example.rentalservice.mapper;

import com.example.rentalservice.dto.VehicleCreateDto;
import com.example.rentalservice.dto.VehicleDto;
import com.example.rentalservice.model.Vehicle;
import com.example.rentalservice.model.VehicleType;
import com.example.rentalservice.repository.VehicleTypeRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class VehicleMapper {
    private VehicleTypeRepository vehicleTypeRepository;

    private VehicleTypeMapper vehicleTypeMapper;

    public VehicleMapper(VehicleTypeRepository vehicleTypeRepository, VehicleTypeMapper vehicleTypeMapper) {
        this.vehicleTypeRepository = vehicleTypeRepository;
        this.vehicleTypeMapper = vehicleTypeMapper;
    }

    public VehicleDto vehicleToVehicleDto(Vehicle vehicle) {
        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setModel(vehicle.getModel());
        vehicleDto.setTypeDto(vehicleTypeMapper.vehicleTypeToVehicleTypeDto(vehicle.getType()));
        vehicleDto.setId(vehicle.getId());
        return vehicleDto;
    }

    public Vehicle vehicleCreateDtoToVehicle(VehicleCreateDto vehicleCreateDto) {
        Vehicle vehicle = new Vehicle();
        vehicle.setModel(vehicleCreateDto.getModel());
        Optional<VehicleType> vehicleType = vehicleTypeRepository.findById(vehicleCreateDto.getTypeid());
        System.out.println(vehicleType.get().getName());
        vehicle.setType(vehicleType.get());
        return vehicle;
    }
}
