package com.example.notificarionservice.service;

import com.example.notificarionservice.client.emailservice.EmailService;
import com.example.notificarionservice.dtos.SendNotificationDto;
import com.example.notificarionservice.repository.NotificationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

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
        ArrayList<String> recipients = new ArrayList<>();
        String subject = "Test";
        String body = "Body";
        try {
            if(sendNotificationDto.getNotificationType().equals("activation"))
            {
                //registration notification
            }
            else if(sendNotificationDto.getNotificationType().equals("changePassword"))
            {
                    // changed password notification
            }
            else if(sendNotificationDto.getNotificationType().equals("cancelReservation"))
            {
                //cancel reservation notification
            }
            else if(sendNotificationDto.getNotificationType().equals("reminder"))
            {
                //reminder notification
            }

            emailService.sendEmail(recipients, subject, body);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return new ResponseEntity("Notifikacija je poslata",HttpStatus.CREATED);
    }
}
