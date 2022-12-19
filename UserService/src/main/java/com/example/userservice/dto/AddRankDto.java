package com.example.userservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddRankDto {
    private String name;
    private Integer threshold;
    private Double discount;

    public AddRankDto(String name, Integer threshold) {
        this.name = name;
        this.threshold = threshold;
    }
}
