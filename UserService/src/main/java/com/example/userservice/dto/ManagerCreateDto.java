package com.example.userservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class ManagerCreateDto extends UserCreateDto {
    private Date hireDate;
    private String companyName;

    public ManagerCreateDto(String username, String password, String email, Date dateOfBirth, String name, String surname, Date hireDate, String companyName) {
        super(username, password, email, dateOfBirth, name, surname);
        this.hireDate = hireDate;
        this.companyName = companyName;
    }
}
