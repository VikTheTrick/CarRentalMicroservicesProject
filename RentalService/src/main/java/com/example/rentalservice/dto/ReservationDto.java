package com.example.rentalservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ReservationDto {
    private Long id;
    private Long userid;
    @JsonProperty("company_vehicle")
    private CompanyVehicleDto companyVehicleDto;
    private Date from;
    private Date to;
    private double price;
    private boolean reminder;

}
