package com.example.rentalservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationCreateDto {
    private Long vehicleid;
    private Long companyid;

    private String from;
    private String to;

    public ReservationCreateDto() {
    }

    public ReservationCreateDto(Long vehicleid, Long companyid,String from, String to) {
        this.vehicleid = vehicleid;
        this.companyid = companyid;
        this.from = from;
        this.to = to;
    }
}
