package com.example.userservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenRequestDto {
    private String email;
    private String password;

    public TokenRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
