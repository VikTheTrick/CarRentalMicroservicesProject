package com.example.userservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscountResponseDto {
    private double discount;

    public DiscountResponseDto(double discount) {
        this.discount = discount;
    }

    public DiscountResponseDto() {
    }
}
