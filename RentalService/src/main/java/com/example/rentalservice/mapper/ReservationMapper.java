package com.example.rentalservice.mapper;

import com.example.rentalservice.dto.ReservationCreateDto;
import com.example.rentalservice.dto.ReservationDto;
import com.example.rentalservice.model.Reservation;
import com.example.rentalservice.repository.CompanyVehicleRepository;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public class ReservationMapper {
    private CompanyVehicleRepository companyVehicleRepository;
    private CompanyVehicleMapper companyVehicleMapper;

    public ReservationMapper(CompanyVehicleRepository companyVehicleRepository, CompanyVehicleMapper companyVehicleMapper) {
        this.companyVehicleRepository = companyVehicleRepository;
        this.companyVehicleMapper = companyVehicleMapper;
    }

    public ReservationDto reservationToReservationDto(Reservation reservation) {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setTo(reservation.getTo());
        reservationDto.setFrom(reservation.getFrom());
        reservationDto.setUserid(reservation.getUserid());
        reservationDto.setCompanyVehicleDto(companyVehicleMapper.companyVehicleToCompanyVehicleDto(reservation.getVehicle()));
        reservationDto.setId(reservation.getId());
        return reservationDto;
    }

    public Reservation reservationCreateDtoToReservation(ReservationCreateDto reservationCreateDto) {
        Reservation reservation = new Reservation();
        try {
            reservation.setFrom(new SimpleDateFormat("dd/MM/yyyy").parse(reservationCreateDto.getFrom()));
            reservation.setTo(new SimpleDateFormat("dd/MM/yyyy").parse(reservationCreateDto.getTo()));
            reservation.setVehicle(companyVehicleRepository.findByVehicle_idAndCompany_id(reservationCreateDto.getVehicleid(), reservationCreateDto.getCompanyid())); TODO:
            reservation.setUserid(reservationCreateDto.getUserid());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return reservation;
    }
}
