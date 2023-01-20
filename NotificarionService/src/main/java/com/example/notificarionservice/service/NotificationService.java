package com.example.notificarionservice.service;

import com.example.notificarionservice.client.emailservice.EmailService;
import com.example.notificarionservice.domain.Notification;
import com.example.notificarionservice.domain.NotificationType;
import com.example.notificarionservice.dtos.SendNotificationDto;
import com.example.notificarionservice.mapper.Mapper;
import com.example.notificarionservice.repository.NotificationRepository;
import com.example.notificarionservice.repository.NotificationTypeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Service
@Transactional
public class NotificationService implements INotificationService {
    private NotificationRepository notificationRepository;
    private NotificationTypeRepository notificationTypeRepository;
    private EmailService emailService;
    private Mapper mapper;
    public NotificationService(NotificationRepository notificationRepository, Mapper mapper, NotificationTypeRepository notificationTypeRepository) {
        this.notificationRepository = notificationRepository;
        this.notificationTypeRepository = notificationTypeRepository;
        this.emailService = new EmailService();
        this.mapper = mapper;
    }

    public ResponseEntity sendNotification(SendNotificationDto sendNotificationDto)
    {
        ArrayList<String> recipients = new ArrayList<>();
        String subject = "Test";
        String body = "Body";
        String [] params = sendNotificationDto.getParams().split(",");
        NotificationType curType = notificationTypeRepository.getByName(sendNotificationDto.getNotificationType()).get();
        String curText = curType.getText();

        try {
            if(sendNotificationDto.getNotificationType().equals("activation"))
            {
                recipients.add(sendNotificationDto.getEmail());
                subject = "Account activation email";
                body = mapper.createNotificationString(params, curText);
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
            else if(sendNotificationDto.getNotificationType().equals("activateReservation"))
            {
                recipients.add(sendNotificationDto.getEmail());
                subject = "Reservation email";
                body = mapper.createNotificationString(params, curText);
            }
            else if(sendNotificationDto.getNotificationType().equals("reminder"))
            {
                //reminder notification
            }
            Notification n = mapper.sendNotificationDtoToNotification(sendNotificationDto, curType);
            notificationRepository.save(n);
            emailService.sendEmail(recipients, subject, body);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return new ResponseEntity("Notifikacija je poslata",HttpStatus.CREATED);
    }
}
