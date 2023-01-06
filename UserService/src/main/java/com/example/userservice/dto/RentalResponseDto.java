package com.example.userservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RentalResponseDto {
    private String email;
    private Double discount;

    public RentalResponseDto(String email, Double discount) {
        this.email = email;
        this.discount = discount;
    }

    public RentalResponseDto() {
    }
}
