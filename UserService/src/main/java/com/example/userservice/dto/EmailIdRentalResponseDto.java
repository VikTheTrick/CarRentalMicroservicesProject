package com.example.userservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailIdRentalResponseDto {
    private String email;
    private Long rank_id;

    public EmailIdRentalResponseDto(String email, Long rank_id) {
        this.email = email;
        this.rank_id = rank_id;
    }

    public EmailIdRentalResponseDto() {
    }
}
