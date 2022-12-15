package com.example.userservice.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter

@Entity
public class User {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;
    private String surname;


}
