package com.example.rentalservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class CompanyVehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Vehicle vehicle;
    @ManyToOne
    private Company company;
    private int numberOfVehicle;
    private double price;

    public CompanyVehicle() {
    }

    public CompanyVehicle(Long id, Vehicle vehicle, Company company, int numberOfVehicle, double price) {
        this.id = id;
        this.vehicle = vehicle;
        this.company = company;
        this.numberOfVehicle = numberOfVehicle;
        this.price = price;
    }
}
