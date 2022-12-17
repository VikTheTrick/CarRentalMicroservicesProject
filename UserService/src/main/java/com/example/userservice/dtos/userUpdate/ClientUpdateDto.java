package com.example.userservice.dtos.userUpdate;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
@Getter
@Setter
public class ClientUpdateDto extends UserUpdateDto{
    private Integer passportNumber;
    private Double daysRented;

    public ClientUpdateDto(String username, String password, String email, Date dateOfBirth, String name, String surname, Integer passportNumber, Double daysRented) {
        super(username, password, email, dateOfBirth, name, surname);
        this.passportNumber = passportNumber;
        this.daysRented = daysRented;
    }
}
