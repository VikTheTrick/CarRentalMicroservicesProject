package com.example.rentalservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyCreateDto {
    private String title;
    private String description;

    public CompanyCreateDto() {
    }

    public CompanyCreateDto(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
