package com.example.notificarionservice.controller;

import com.example.notificarionservice.dtos.SendNotificationDto;
import com.example.notificarionservice.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    private NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/send")
    public ResponseEntity sendNotification(@RequestBody SendNotificationDto sendNotificationDto)
    {
       return notificationService.sendNotification(sendNotificationDto);
    }

}
