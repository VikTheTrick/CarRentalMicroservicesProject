package com.example.userservice.domain;

import com.example.userservice.domain.userTypes.Client;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Getter
@Setter

@Entity
@Table(name = "ranks")
public class Rank {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique=true)
    private String name;

    @OneToMany(mappedBy = "rank", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<Client> clients;

    @Column(unique=true)
    private Integer threshold;

    private Double discount;
    public Rank(String name, Integer threshold, Double discount) {
        this.name = name;
        this.threshold = threshold;
        this.discount = discount;
    }

    public Rank() {

    }
}
