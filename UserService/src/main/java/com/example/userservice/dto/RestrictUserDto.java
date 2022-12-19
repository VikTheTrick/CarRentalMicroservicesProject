package com.example.userservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestrictUserDto {
    private String email;

    public RestrictUserDto(String email) {
        this.email = email;
    }
}
