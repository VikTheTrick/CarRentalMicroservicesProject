package com.example.userservice.domain.userTypes;

import com.example.userservice.domain.User;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Date;

@Entity
public class Client extends User {
    private Integer passportNumber;
    private Double daysRented;

    public Client(String username, String password, String email, Date dateOfBirth, String name, String surname, Integer passportNumber
    , Double daysRented) {
        super(username, password, email, dateOfBirth, name, surname);
        this.passportNumber = passportNumber;
        this.daysRented = daysRented;
    }

    public Client() {
        super();
    }
}
