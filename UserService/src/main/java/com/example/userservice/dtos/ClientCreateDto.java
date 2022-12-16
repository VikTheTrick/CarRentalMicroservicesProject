package com.example.userservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
@Getter
@Setter
public class ClientCreateDto extends UserCreateDto{
    private Integer passportNumber;
    private Double daysRented;

    public ClientCreateDto(String username, String password, String email, Date dateOfBirth, String name, String surname
    , Integer passportNumber, Double daysRented) {
        super(username, password, email, dateOfBirth, name, surname);
        this.daysRented = daysRented;
        this.passportNumber = passportNumber;
    }
}
