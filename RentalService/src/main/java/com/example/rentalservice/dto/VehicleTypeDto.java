package com.example.rentalservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleTypeDto {
    private Long id;
    private String name;

    public VehicleTypeDto() {
    }

    public VehicleTypeDto(Long id, String name) {
        this.id = id; this.name = name;
    }
}
