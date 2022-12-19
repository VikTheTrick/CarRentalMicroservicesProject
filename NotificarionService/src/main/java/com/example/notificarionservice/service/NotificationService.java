package com.example.notificarionservice.service;

import com.example.notificarionservice.client.emailservice.EmailService;
import com.example.notificarionservice.dtos.SendNotificationDto;
import com.example.notificarionservice.repository.NotificationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class NotificationService implements INotificationService {
    private NotificationRepository iNotificationRepository;
    private EmailService emailService;

    public NotificationService(NotificationRepository iNotificationRepository) {
        this.iNotificationRepository = iNotificationRepository;
        this.emailService = new EmailService();
    }

    public ResponseEntity sendNotification(SendNotificationDto sendNotificationDto)
    {
        emailService.send( "viktor@gmail.com", "blabla", "Test");
        int a = 5;
        return new ResponseEntity("Notifikacija je poslata",HttpStatus.CREATED);
    }
}
