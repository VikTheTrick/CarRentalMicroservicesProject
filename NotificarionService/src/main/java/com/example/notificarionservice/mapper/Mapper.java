package com.example.notificarionservice.mapper;

import com.example.notificarionservice.domain.Notification;
import com.example.notificarionservice.domain.NotificationType;
import com.example.notificarionservice.dtos.SendNotificationDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;



@Component
public class Mapper {

    public Mapper() {
    }

    public String createNotificationString(String[] params, String text)
    {
        String result = "";
        Boolean foundParam = false;
        int curParamIndex = 0;
        int curLen = 0;
        for(char c : text.toCharArray())
        {
            curLen ++;
            if(c == '%') {
                foundParam = true;
                continue;
            }
            if(foundParam == true && (c == ' ' || curLen == text.length()-1))
            {
                foundParam = false;
                result += ' ';
                result += params[curParamIndex];
                result += ' ';

                curParamIndex ++;
                continue;
            }
            if(!foundParam)
            {
                result += c;
            }
        }
        return result;
    }

    public Notification sendNotificationDtoToNotification(SendNotificationDto sendNotificationDto, NotificationType notificationType)
    {
        return new Notification(sendNotificationDto.getEmail(), sendNotificationDto.getParams(), notificationType);
    }
}
