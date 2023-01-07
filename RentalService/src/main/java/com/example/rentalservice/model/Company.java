package com.example.rentalservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private int sumOfGrades;
    private int numberOfGrades;

    public Company() {
    }

    public Company(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.sumOfGrades = 0;
        this.numberOfGrades = 0;
    }
}
