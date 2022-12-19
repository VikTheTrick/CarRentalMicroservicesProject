package com.example.notificarionservice.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter

@Entity
public class Notification {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    private String params;

    @ManyToOne
    @JoinColumn(name = "notification_type_id")
    private NotificationType notificationType;


}
