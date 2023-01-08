package com.example.rentalservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyVehicleCreateDto {
    private Long companyid;
    private Long vehicleid;
    private int numberOfVehicle;
    private double price;

    public CompanyVehicleCreateDto() {
    }

    public CompanyVehicleCreateDto(Long companyid, Long vehicleid, int numberOfVehicle, double price) {
        this.companyid = companyid;
        this.vehicleid = vehicleid;
        this.numberOfVehicle = numberOfVehicle;
        this.price = price;
    }
}
