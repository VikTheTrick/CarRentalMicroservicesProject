package com.example.notificarionservice.repository;

import com.example.notificarionservice.domain.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationTypeRepository extends JpaRepository<NotificationType, Long> {

    Optional<NotificationType> getByName(String name);
}
