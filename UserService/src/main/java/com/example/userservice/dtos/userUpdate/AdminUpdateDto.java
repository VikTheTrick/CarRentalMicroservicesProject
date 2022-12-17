package com.example.userservice.dtos.userUpdate;

import java.sql.Date;

public class AdminUpdateDto extends UserUpdateDto {
    public AdminUpdateDto(String username, String password, String email, Date dateOfBirth, String name, String surname) {
        super(username, password, email, dateOfBirth, name, surname);
    }
}
