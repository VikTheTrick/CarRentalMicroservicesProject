package com.example.userservice.dtos.userUpdate;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
@Getter
@Setter
public class ManagerUpdateDto extends UserUpdateDto{
    private Date hireDate;
    private String companyName;
    public ManagerUpdateDto(String username, String password, String email, Date dateOfBirth, String name, String surname) {
        super(username, password, email, dateOfBirth, name, surname);
    }
}
