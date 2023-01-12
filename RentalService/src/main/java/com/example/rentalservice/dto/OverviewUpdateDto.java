package com.example.rentalservice.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
public class OverviewUpdateDto {
    private String comment;
    @Min(1)
    @Max(5)
    private int grade;

    public OverviewUpdateDto() {
    }

    public OverviewUpdateDto(String comment, int grade) {
        this.comment = comment;
        this.grade = grade;
    }
}
