package com.example.rentalservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleCreateDto {
    private String model;
    private Long typeid;

    public VehicleCreateDto() {
    }

    public VehicleCreateDto(String model, Long typeid) {
        this.model = model;
        this.typeid = typeid;
    }
}
