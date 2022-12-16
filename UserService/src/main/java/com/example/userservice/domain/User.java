package com.example.userservice.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User{
    @Id
    @GeneratedValue
    private Long id;

    private String username;
    private String password;

    @Column(unique=true)
    private String email;
    private Date dateOfBirth;
    private String name;
    private String surname;


    public User(String username, String password, String email, Date dateOfBirth, String name, String surname) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.name = name;
        this.surname = surname;
    }

    public User() {

    }
}
