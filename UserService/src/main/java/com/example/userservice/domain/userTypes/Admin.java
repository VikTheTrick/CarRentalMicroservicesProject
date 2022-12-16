package com.example.userservice.domain.userTypes;

import com.example.userservice.domain.User;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Date;
import java.time.LocalDateTime;

@Entity
public class Admin extends User {
    public Admin() {
        super();
    }

    public Admin(String username, String password, String email, Date dateOfBirth, String name, String surname) {
        super(username, password, email, dateOfBirth, name, surname);
    }
}
