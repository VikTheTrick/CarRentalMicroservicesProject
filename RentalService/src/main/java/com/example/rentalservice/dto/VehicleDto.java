package com.example.rentalservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleDto {

    private Long id;
    private String model;
    @JsonProperty("type")
    private VehicleTypeDto typeDto;

    public VehicleDto() {
    }

    public VehicleDto(Long id, String model, VehicleTypeDto typeDto) {
        this.id = id;
        this.model = model;
        this.typeDto = typeDto;
    }
}
