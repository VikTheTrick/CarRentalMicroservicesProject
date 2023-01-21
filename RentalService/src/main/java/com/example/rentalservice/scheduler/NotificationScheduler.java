package com.example.rentalservice.scheduler;

import com.example.rentalservice.client.notificationservice.dto.SendNotificationDto;
import com.example.rentalservice.client.userservice.dto.RentalResponseDto;
import com.example.rentalservice.helper.MessageHelper;
import com.example.rentalservice.mapper.ReservationMapper;
import com.example.rentalservice.model.Reservation;
import com.example.rentalservice.repository.ReservationRepository;
import io.github.resilience4j.retry.Retry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class NotificationScheduler {
    private JmsTemplate jmsTemplate;
    private String destination;
    private MessageHelper messageHelper;
    private ReservationRepository reservationRepository;
    private ReservationMapper reservationMapper;
    private RestTemplate userServiceRestTemplate;
    private Retry reservationRetry;
    public NotificationScheduler(JmsTemplate jmsTemplate, @Value("${destination.send_notification}") String destination, MessageHelper messageHelper, ReservationRepository reservationRepository, ReservationMapper reservationMapper, RestTemplate userServiceRestTemplate, Retry reservationRetry) {
        this.jmsTemplate = jmsTemplate;
        this.destination = destination;
        this.messageHelper = messageHelper;
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
        this.userServiceRestTemplate = userServiceRestTemplate;
        this.reservationRetry = reservationRetry;
    }

    @Scheduled(fixedDelay = 1000)
    public void checkReservations() {
        List<Reservation> reservationList = reservationRepository.findAll();
        for(Reservation reservation: reservationList) {
            if(!reservation.isReminder()) {
                ZonedDateTime zdt = ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault());
                if ((TimeUnit.DAYS.convert(reservation.getTo().getTime() - zdt.toInstant().toEpochMilli(), TimeUnit.MILLISECONDS) < 3)) {
                    String notification = reservation.getId() + "," + reservation.getFrom();
                    ResponseEntity<RentalResponseDto> discountDtoResponseEntity = Retry.decorateSupplier(reservationRetry,()->userServiceRestTemplate.exchange("/user/getRentalInfo/" + reservation.getUserid(), HttpMethod.GET,null, RentalResponseDto.class)).get();
                    jmsTemplate.convertAndSend(destination,messageHelper.createTextMessage(new SendNotificationDto(discountDtoResponseEntity.getBody().getEmail(),notification,"reminder")));
                    reservation.setReminder(true);
                    reservationRepository.save(reservation);
                }
            }

        }
    }
}
