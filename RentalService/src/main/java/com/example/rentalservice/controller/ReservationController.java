package com.example.rentalservice.controller;

import com.example.rentalservice.dto.ReservationCreateDto;
import com.example.rentalservice.dto.ReservationDto;
import com.example.rentalservice.security.CheckSecurity;
import com.example.rentalservice.service.ReservationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private ReservationService reservationService;
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    @CheckSecurity(roles={"ROLE_ADMIN"})
    public ResponseEntity<Page<ReservationDto>> findAll(@RequestHeader("Authorization") String authorization,@ApiIgnore Pageable pageable){
        return new ResponseEntity<>(reservationService.findAll(pageable), HttpStatus.OK);
    }
    @PostMapping
    @CheckSecurity(roles = {"ROLE_CLIENT","ROLE_ADMIN"})
    public ResponseEntity<ReservationDto> addReservation(@RequestHeader("Authorization") String authorization, @RequestBody ReservationCreateDto reservationCreateDto){
        return new ResponseEntity<>(reservationService.addReservation(reservationCreateDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @CheckSecurity(roles = {"ROLE_CLIENT", "ROLE_MANAGER", "ROLE_ADMIN"})
    public ResponseEntity<?> deleteReservation(@RequestHeader("Authorization") String authorization, @PathVariable("id") Long id) {
        reservationService.deleteReservation(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
