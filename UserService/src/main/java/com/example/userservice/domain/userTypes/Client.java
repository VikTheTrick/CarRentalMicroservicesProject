package com.example.userservice.domain.userTypes;

import com.example.userservice.domain.Rank;
import com.example.userservice.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Getter
@Setter
@Entity
public class Client extends User {

    private Integer passportNumber;
    private Double daysRented;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "rank_id", nullable = false)
    private Rank rank;

    public Client(String username, String password, String email, Date dateOfBirth, String name, String surname, Integer passportNumber
    , Double daysRented, Rank rank) {
        super(username, password, email, dateOfBirth, name, surname);
        this.passportNumber = passportNumber;
        this.daysRented = daysRented;
        this.rank=rank;
    }

    public Client() {
        super();
    }
}
