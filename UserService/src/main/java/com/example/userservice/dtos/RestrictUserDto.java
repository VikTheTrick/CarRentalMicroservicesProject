package com.example.userservice.dtos;

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
