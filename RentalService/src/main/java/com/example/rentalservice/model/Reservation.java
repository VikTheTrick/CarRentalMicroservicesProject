package com.example.rentalservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Getter
@Setter
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userid;
    @ManyToOne
    private CompanyVehicle vehicle;
    @Column(name="from_date")
    private Date from;
    @Column(name="to_date")
    private Date to;
    private double price;

    public Reservation() {
    }

    public Reservation(Long id, Long userid, CompanyVehicle vehicle, Date from, Date to, double price) {
        this.id = id;
        this.userid = userid;
        this.vehicle = vehicle;
        this.from = from;
        this.to = to;
        this.price = price;
    }
}
