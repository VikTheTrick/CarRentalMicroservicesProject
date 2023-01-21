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
import io.github.resilience4j.retry.Retry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.util.concurrent.TimeUnit;

@Service
public class ReservationServiceImplementation implements ReservationService {

    private ReservationMapper reservationMapper;
    private ReservationRepository reservationRepository;
    private CompanyVehicleRepository companyVehicleRepository;
    private RestTemplate userServiceRestTemplate;
    private JmsTemplate jmsTemplate;
    private MessageHelper messageHelper;
    private String destination;

    private Retry reservationRetry;

    public ReservationServiceImplementation(ReservationMapper reservationMapper, ReservationRepository reservationRepository, CompanyVehicleRepository companyVehicleRepository, RestTemplate userServiceRestTemplate,
                                            JmsTemplate jmsTemplate, MessageHelper messageHelper, @Value("${destination.send_notification}") String destination, Retry reservationRetry) {
        this.reservationMapper = reservationMapper;
        this.reservationRepository = reservationRepository;
        this.companyVehicleRepository = companyVehicleRepository;
        this.userServiceRestTemplate = userServiceRestTemplate;
        this.jmsTemplate = jmsTemplate;
        this.messageHelper = messageHelper;
        this.destination = destination;
        this.reservationRetry = reservationRetry;
    }

    @Override
    public ReservationDto addReservation(ReservationCreateDto reservationCreateDto, Long id) {
        // discount from user service
        System.out.println(id + " id u add");
        ResponseEntity<RentalResponseDto> discountDtoResponseEntity = Retry.decorateSupplier(reservationRetry,()->userServiceRestTemplate.exchange("/user/getRentalInfo/" + id, HttpMethod.GET,null, RentalResponseDto.class)).get();
        // calculating price
        double price = companyVehicleRepository.findByVehicle_idAndCompany_id(reservationCreateDto.getVehicleid(), reservationCreateDto.getCompanyid()).getPrice() / 100 * (100 - discountDtoResponseEntity.getBody().getDiscount());
        Reservation reservation = reservationMapper.reservationCreateDtoToReservation(reservationCreateDto, id);
        reservation.setPrice(price);
        reservationRepository.save(reservation);
        // sending notification
        String notification = reservationCreateDto.getVehicleid()+","+reservationCreateDto.getFrom()+","+reservationCreateDto.getTo()+","+ reservationCreateDto.getCompanyid();
        jmsTemplate.convertAndSend(destination,messageHelper.createTextMessage(new SendNotificationDto(discountDtoResponseEntity.getBody().getEmail(),notification,"activateReservation")));
        // increasing rental days
        HttpEntity<ChangeDaysRentedDto> updateDaysRented = new HttpEntity<>(new ChangeDaysRentedDto(TimeUnit.DAYS.convert(reservation.getTo().getTime() - reservation.getFrom().getTime(),TimeUnit.MILLISECONDS),id));
        userServiceRestTemplate.exchange("/user/changeRentalDays",HttpMethod.POST,updateDaysRented,Object.class);
        return reservationMapper.reservationToReservationDto(reservation);
    }

    @Override
    public Page<ReservationDto> findAll(Pageable pageable) {
        return reservationRepository.findAll(pageable).map(reservationMapper::reservationToReservationDto);
    }

    @Override
    public Page<ReservationDto> findByUserid(Long userid, Pageable pageable) {
        return reservationRepository.findByUserid(userid,pageable).map(reservationMapper::reservationToReservationDto);
    }

    @Override
    public Page<ReservationDto> findByCompanyId(Long companyid, Pageable pageable) {
        System.out.println(companyid);
        return reservationRepository.findReservationsForCompany(companyid,pageable).map(reservationMapper::reservationToReservationDto);
    }

    @Override
    public void deleteReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id).get();

        reservationRepository.deleteById(id);
        String notification = reservation.getId() +","+reservation.getFrom();
        ResponseEntity<RentalResponseDto> rentalResponseEntity = userServiceRestTemplate.exchange("/user/getRentalInfo/" + reservation.getUserid(), HttpMethod.GET,null, RentalResponseDto.class);
        // send notification
        jmsTemplate.convertAndSend(destination,messageHelper.createTextMessage(new SendNotificationDto(rentalResponseEntity.getBody().getEmail(),notification,"cancelReservation")));

        // descreasing rental days
        HttpEntity<ChangeDaysRentedDto> updateDaysRented = new HttpEntity<>(new ChangeDaysRentedDto(TimeUnit.DAYS.convert(reservation.getFrom().getTime() - reservation.getTo().getTime(),TimeUnit.MILLISECONDS), reservation.getUserid()));
        userServiceRestTemplate.exchange("/user/changeRentalDays",HttpMethod.POST,updateDaysRented,Object.class);
    }
}
