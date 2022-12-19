package com.example.notificarionservice.repository;

import com.example.notificarionservice.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
