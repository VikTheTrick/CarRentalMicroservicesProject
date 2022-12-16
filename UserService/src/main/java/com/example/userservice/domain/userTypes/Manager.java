package com.example.userservice.domain.userTypes;

import com.example.userservice.domain.User;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Date;

@Entity
public class Manager extends User {
    private Date hireDate;
    private String companyName;

    public Manager(String username, String password, String email, Date dateOfBirth, String name, String surname, Date hireDate, String companyName) {
        super(username, password, email, dateOfBirth, name, surname);
        this.hireDate = hireDate;
        this.companyName = companyName;
    }

    public Manager() {

    }
}
