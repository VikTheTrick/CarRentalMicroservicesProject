package com.example.rentalservice.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
public class OverviewCreateDto {
    private Long companyid;
    @Min(1)
    @Max(5)
    private int grade;
    private String comment;

    public OverviewCreateDto() {
    }

    public OverviewCreateDto(Long companyid, int grade, String comment) {
        this.companyid = companyid;
        this.grade = grade;
        this.comment = comment;
    }
}
