package com.example.rentalservice.service.impl;

import com.example.rentalservice.client.notificationservice.dto.SendNotificationDto;
import com.example.rentalservice.client.userservice.dto.ChangeDaysRentedDto;
import com.example.rentalservice.client.userservice.dto.RentalResponseDto;
import com.example.rentalservice.dto.ReservationCreateDto;
import com.example.rentalservice.dto.ReservationDto;
import com.example.rentalservice.helper.MessageHelper;
import com.example.rentalservice.mapper.ReservationMapper;
import com.example.rentalservice.model.Reservation;
import com.example.rentalservice.repository.CompanyVehicleRepository;
import com.example.rentalservice.repository.ReservationRepository;
import com.example.rentalservice.service.ReservationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ReservationServiceImplementation implements ReservationService {

    private ReservationMapper reservationMapper;
    private ReservationRepository reservationRepository;
    private CompanyVehicleRepository companyVehicleRepository;
    private RestTemplate userServiceRestTemplate;
    private JmsTemplate jmsTemplate;
    private MessageHelper messageHelper;
    private String destination;

    public ReservationServiceImplementation(ReservationMapper reservationMapper, ReservationRepository reservationRepository, CompanyVehicleRepository companyVehicleRepository, RestTemplate userServiceRestTemplate,
                                            JmsTemplate jmsTemplate, MessageHelper messageHelper, @Value("${destination.send_notification}") String destination) {
        this.reservationMapper = reservationMapper;
        this.reservationRepository = reservationRepository;
        this.companyVehicleRepository = companyVehicleRepository;
        this.userServiceRestTemplate = userServiceRestTemplate;
        this.jmsTemplate = jmsTemplate;
        this.messageHelper = messageHelper;
        this.destination = destination;
    }

    @Override
    public ReservationDto addReservation(ReservationCreateDto reservationCreateDto) {
        // discount from user service
        ResponseEntity<RentalResponseDto> discountDtoResponseEntity = userServiceRestTemplate.exchange("/user/getRentalInfo/" + reservationCreateDto.getUserid(), HttpMethod.GET,null, RentalResponseDto.class);
        // calculating price
        double price = companyVehicleRepository.findByVehicle_idAndCompany_id(reservationCreateDto.getVehicleid(), reservationCreateDto.getCompanyid()).getPrice() / 100 * (100 - discountDtoResponseEntity.getBody().getDiscount());
        Reservation reservation = reservationMapper.reservationCreateDtoToReservation(reservationCreateDto);
        reservation.setPrice(price);
        reservationRepository.save(reservation);
        // sending notification
        jmsTemplate.convertAndSend(destination,messageHelper.createTextMessage(new SendNotificationDto(discountDtoResponseEntity.getBody().getEmail(),"","activateReservation")));
        // increasing rental days
        HttpEntity<ChangeDaysRentedDto> updateDaysRented = new HttpEntity<>(new ChangeDaysRentedDto(reservation.getTo().getTime() - reservation.getFrom().getTime(), reservation.getUserid()));
        ResponseEntity<ChangeDaysRentedDto> update = userServiceRestTemplate.exchange("/user/changeRentalDays",HttpMethod.POST,updateDaysRented,ChangeDaysRentedDto.class);
        return reservationMapper.reservationToReservationDto(reservation);
    }

    @Override
    public Page<ReservationDto> findAll(Pageable pageable) {
        return reservationRepository.findAll(pageable).map(reservationMapper::reservationToReservationDto);
    }

    @Override
    public void deleteReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id).get();
        reservationRepository.deleteById(id);

        // send notification
        jmsTemplate.convertAndSend(destination,messageHelper.createTextMessage(new SendNotificationDto("","","cancelReservation")));

        // descreasing rental days
        HttpEntity<ChangeDaysRentedDto> updateDaysRented = new HttpEntity<>(new ChangeDaysRentedDto(reservation.getFrom().getTime() - reservation.getTo().getTime(), reservation.getUserid()));
        ResponseEntity<ChangeDaysRentedDto> update = userServiceRestTemplate.exchange("/user/changeRentalDays",HttpMethod.POST,updateDaysRented,ChangeDaysRentedDto.class);
    }
}
