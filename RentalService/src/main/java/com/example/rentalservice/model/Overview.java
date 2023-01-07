package com.example.rentalservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Overview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Company company;
    private Long userid;
    private int grade;
    private String comment;

    public Overview() {
    }

    public Overview(Long id, Company company, Long userid, int grade, String comment) {
        this.id = id;
        this.company = company;
        this.userid = userid;
        this.grade = grade;
        this.comment = comment;
    }
}
