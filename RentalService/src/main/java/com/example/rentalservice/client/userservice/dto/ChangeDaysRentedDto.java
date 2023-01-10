package com.example.rentalservice.client.userservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeDaysRentedDto {
    private double daysRented;
    private Long userid;

    public ChangeDaysRentedDto() {
    }

    public ChangeDaysRentedDto(double daysRented, Long userid) {
        this.daysRented = daysRented;
        this.userid = userid;
    }
}
