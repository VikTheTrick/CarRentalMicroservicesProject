package com.example.userservice.dto.userUpdate;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class UserUpdateDto  {
    private Long id;
    private String username;
    private String password;
    private String email;
    private Date dateOfBirth;
    private String name;
    private String surname;
    private Integer passportNumber;
    private Double daysRented;
    private Date hireDate;
    private String companyName;
    public UserUpdateDto(String username, String password, String email, Date dateOfBirth, String name, String surname) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.name = name;
        this.surname = surname;
    }

    public UserUpdateDto(Long id, String username, String password, String email, Date dateOfBirth, String name, String surname, Integer passportNumber, Double daysRented, Date hireDate, String companyName) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.name = name;
        this.surname = surname;
        this.passportNumber = passportNumber;
        this.daysRented = daysRented;
        this.hireDate = hireDate;
        this.companyName = companyName;
    }
    public UserUpdateDto()
    {

    }
}
