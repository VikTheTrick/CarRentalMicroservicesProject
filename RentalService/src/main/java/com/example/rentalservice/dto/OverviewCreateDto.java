package com.example.rentalservice.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
public class OverviewCreateDto {
    private Long companyid;
    private Long userid;
    @Min(1)
    @Max(5)
    private int grade;
    private String comment;

    public OverviewCreateDto() {
    }

    public OverviewCreateDto(Long companyid, Long userid, int grade, String comment) {
        this.companyid = companyid;
        this.userid = userid;
        this.grade = grade;
        this.comment = comment;
    }
}
