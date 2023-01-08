package com.example.rentalservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleTypeCreateDto {
    private String name;

    public VehicleTypeCreateDto() {
    }

    public VehicleTypeCreateDto(String name) {
        this.name = name;
    }
}
