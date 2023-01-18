package com.example.rentalservice.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PeriodDto {
    private String from;
    private String to;

    public PeriodDto() {
    }

    public PeriodDto(String from, String to) {
        this.from = from;
        this.to = to;
    }
}
