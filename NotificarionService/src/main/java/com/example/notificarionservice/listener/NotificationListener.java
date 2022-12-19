package com.example.notificarionservice.listener;

import com.example.notificarionservice.dtos.SendNotificationDto;
import com.example.notificarionservice.service.NotificationService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;

@Getter
@Setter
@Component
public class NotificationListener {
    private NotificationService notificationService;
    private MessageHelper messageHelper;

    public NotificationListener(NotificationService notificationService, MessageHelper messageHelper) {
        this.notificationService = notificationService;
        this.messageHelper = messageHelper;
    }

    @JmsListener(destination = "${destination.sendNotification}", concurrency = "5-10")
    public void sendNotification(Message message) throws JMSException {
        SendNotificationDto sendNotificationDto = messageHelper.getMessage(message, SendNotificationDto.class);
        System.out.println(sendNotificationDto);
        notificationService.sendNotification(sendNotificationDto);
    }
}
