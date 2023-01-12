package com.example.rentalservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
public class OverviewDto {
    private Long id;
    @JsonProperty("company")
    private CompanyDto companyDto;
    private Long userid;
    @Min(1)
    @Max(5)
    private int grade;
    private String comment;

    public OverviewDto() {
    }

    public OverviewDto(Long id, CompanyDto companyDto, Long userid, int grade, String comment) {
        this.id = id;
        this.companyDto = companyDto;
        this.userid = userid;
        this.grade = grade;
        this.comment = comment;
    }
}
