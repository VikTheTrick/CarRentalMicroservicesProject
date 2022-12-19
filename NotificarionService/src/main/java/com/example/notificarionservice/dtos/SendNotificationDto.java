package com.example.notificarionservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendNotificationDto {
    private String email;
    private String params;
    private String notificationType;

    public SendNotificationDto(String email, String params, String notificationType) {
        this.email = email;
        this.params = params;
        this.notificationType = notificationType;
    }
}
