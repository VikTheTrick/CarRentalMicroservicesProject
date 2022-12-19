package com.example.notificarionservice.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter

@Entity
public class NotificationType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "notificationType", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<Notification> notifications;

    private String name;

    private String params;
    private String text;



}
