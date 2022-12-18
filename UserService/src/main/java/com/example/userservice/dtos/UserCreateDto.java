package com.example.userservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public abstract class UserCreateDto {
    private String username;
    private String password;
    private String email;
    private Date dateOfBirth;
    private String name;
    private String surname;

    public UserCreateDto(String username, String password, String email, Date dateOfBirth, String name, String surname) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.name = name;
        this.surname = surname;
    }

    public UserCreateDto() {

    }
}
