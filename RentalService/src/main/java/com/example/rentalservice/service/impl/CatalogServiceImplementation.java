package com.example.rentalservice.service.impl;

import com.example.rentalservice.dto.CompanyVehicleDto;
import com.example.rentalservice.dto.PeriodDto;
import com.example.rentalservice.mapper.CompanyVehicleMapper;
import com.example.rentalservice.model.CompanyVehicle;
import com.example.rentalservice.model.Reservation;
import com.example.rentalservice.repository.CompanyVehicleRepository;
import com.example.rentalservice.repository.ReservationRepository;
import com.example.rentalservice.service.CatalogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CatalogServiceImplementation implements CatalogService {
    private CompanyVehicleRepository companyVehicleRepository;
    private ReservationRepository reservationRepository;
    private CompanyVehicleMapper companyVehicleMapper;

    public CatalogServiceImplementation(CompanyVehicleRepository companyVehicleRepository, ReservationRepository reservationRepository,
                                        CompanyVehicleMapper companyVehicleMapper) {
        this.companyVehicleRepository = companyVehicleRepository;
        this.reservationRepository = reservationRepository;
        this.companyVehicleMapper = companyVehicleMapper;
    }

    @Override
    public Page<CompanyVehicleDto> findAll(PeriodDto periodDto, Pageable pageable) {
        List<CompanyVehicle> companyVehicles = companyVehicleRepository.findAll();
        List<Reservation> reservations = reservationRepository.findAll();
        List<CompanyVehicleDto> companyVehicleDtos = new ArrayList<>();
        Date fromDate = Date.valueOf(periodDto.getFrom());
        Date toDate = Date.valueOf(periodDto.getTo());

        Map<CompanyVehicle,Integer> numberOfReservations = new HashMap<>();
        for(Reservation reservation: reservations) {
            if(((reservation.getFrom().after(fromDate) || reservation.getFrom().equals(fromDate)) && (reservation.getFrom().before(toDate) || reservation.getFrom().equals(toDate)))
                    || ((reservation.getTo().after(fromDate) || reservation.getTo().equals(fromDate)) && (reservation.getTo().before(toDate) || reservation.getTo().equals(toDate))))
            {
                if(numberOfReservations.containsKey(reservation.getVehicle()))
                    numberOfReservations.replace(reservation.getVehicle(),numberOfReservations.get(reservation.getVehicle()) + 1);
                else numberOfReservations.put(reservation.getVehicle(),1);
            }
        }
        for(CompanyVehicle companyVehicle: companyVehicles) {
            if(!numberOfReservations.containsKey(companyVehicle) || companyVehicle.getNumberOfVehicle() > numberOfReservations.get(companyVehicle)) {
                companyVehicleDtos.add(companyVehicleMapper.companyVehicleToCompanyVehicleDto(companyVehicle));
            }
        }
        int start = (int) pageable.getOffset();
        int end = (int) ((start + pageable.getPageSize()) > companyVehicleDtos.size() ? companyVehicleDtos.size()
                : (start + pageable.getPageSize()));
        return new PageImpl<>(companyVehicleDtos.subList(start,end),pageable,companyVehicleDtos.size());
    }

    @Override
    public Page<CompanyVehicleDto> findByCompany(PeriodDto periodDto, Long companyid, Pageable pageable) {
        List<CompanyVehicleDto> companyVehicleDtos = findAll(periodDto,pageable).stream().filter(companyVehicleDto -> companyVehicleDto.getCompanyDto().getId() == companyid).collect(Collectors.toList());
        int start = (int) pageable.getOffset();
        int end = (int) ((start + pageable.getPageSize()) > companyVehicleDtos.size() ? companyVehicleDtos.size()
                : (start + pageable.getPageSize()));
        return new PageImpl<>(companyVehicleDtos.subList(start,end),pageable,companyVehicleDtos.size());
    }
}
