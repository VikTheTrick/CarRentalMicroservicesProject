package com.example.rentalservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String model;
    @ManyToOne
    private VehicleType type;

    public Vehicle() {
    }

    public Vehicle(Long id, String model, VehicleType type) {
        this.id = id;
        this.model = model;
        this.type = type;
    }
}
