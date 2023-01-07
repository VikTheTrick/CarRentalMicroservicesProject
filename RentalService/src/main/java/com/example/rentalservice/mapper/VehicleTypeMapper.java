package com.example.rentalservice.mapper;

import com.example.rentalservice.dto.VehicleTypeCreateDto;
import com.example.rentalservice.dto.VehicleTypeDto;
import com.example.rentalservice.model.VehicleType;
import org.springframework.stereotype.Component;

@Component
public class VehicleTypeMapper {

    public VehicleTypeDto vehicleTypeToVehicleTypeDto(VehicleType vehicleType) {
        VehicleTypeDto vehicleTypeDto = new VehicleTypeDto();
        vehicleTypeDto.setName(vehicleType.getName());
        vehicleTypeDto.setId(vehicleType.getId());
        return vehicleTypeDto;
    }

    public VehicleType vehicleTypeCreateDtoToVehicle(VehicleTypeCreateDto vehicleTypeCreateDto) {
        VehicleType vehicleType = new VehicleType();
        vehicleType.setName(vehicleTypeCreateDto.getName());
        return vehicleType;
    }
}
