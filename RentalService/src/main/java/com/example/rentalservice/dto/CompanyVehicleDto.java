package com.example.rentalservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyVehicleDto {
    private Long id;

    @JsonProperty("vehicle")
    private VehicleDto vehicleDto;
    @JsonProperty("company")
    private CompanyDto companyDto;
    private int numberOfVehicle;
    private double price;

    public CompanyVehicleDto() {
    }

    public CompanyVehicleDto(Long id, VehicleDto vehicleDto, CompanyDto companyDto, int numberOfVehicle, double price) {
        this.id = id;
        this.vehicleDto = vehicleDto;
        this.companyDto = companyDto;
        this.numberOfVehicle = numberOfVehicle;
        this.price = price;
    }
}
