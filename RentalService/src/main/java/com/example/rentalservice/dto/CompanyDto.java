package com.example.rentalservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDto {
    private Long id;
    private String title;
    private String description;

    public CompanyDto() {
    }

    public CompanyDto(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
}
