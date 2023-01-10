package com.example.rentalservice.service;

import com.example.rentalservice.dto.ReservationCreateDto;
import com.example.rentalservice.dto.ReservationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReservationService {

    ReservationDto addReservation(ReservationCreateDto reservationCreateDto);
    Page<ReservationDto> findAll(Pageable pageable);
    void deleteReservation(Long id);
}
