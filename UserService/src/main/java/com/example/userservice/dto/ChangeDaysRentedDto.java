package com.example.userservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ChangeDaysRentedDto {
    private double daysRented;
    private Long userId;

    public ChangeDaysRentedDto(double daysRented, Long userId) {
        this.daysRented = daysRented;
        this.userId = userId;
    }

    public ChangeDaysRentedDto() {
    }
}
