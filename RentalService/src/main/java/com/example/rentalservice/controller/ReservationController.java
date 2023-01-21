package com.example.rentalservice.controller;

import com.example.rentalservice.dto.ReservationCreateDto;
import com.example.rentalservice.dto.ReservationDto;
import com.example.rentalservice.security.CheckSecurity;
import com.example.rentalservice.security.service.TokenService;
import com.example.rentalservice.service.ReservationService;
import io.jsonwebtoken.Claims;
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
    private TokenService tokenService;
    public ReservationController(ReservationService reservationService, TokenService tokenService) {
        this.reservationService = reservationService;
        this.tokenService = tokenService;
    }

    @GetMapping
    @CheckSecurity(roles={"ROLE_ADMIN"})
    public ResponseEntity<Page<ReservationDto>> findAll(@RequestHeader("Authorization") String authorization,@ApiIgnore Pageable pageable){
        return new ResponseEntity<>(reservationService.findAll(pageable), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    @CheckSecurity(roles={"ROLE_ADMIN"})
    public ResponseEntity<Page<ReservationDto>> findByUserid(@RequestHeader("Authorization") String authorization,@PathVariable("id") Long id, @ApiIgnore Pageable pageable){
        return new ResponseEntity<>(reservationService.findByUserid(id, pageable), HttpStatus.OK);
    }

    @GetMapping("/company/{id}")
    @CheckSecurity(roles={"ROLE_ADMIN"})
    public ResponseEntity<Page<ReservationDto>> findByCompanyId(@RequestHeader("Authorization") String authorization,@PathVariable("id") Long id, @ApiIgnore Pageable pageable){
        return new ResponseEntity<>(reservationService.findByCompanyId(id, pageable), HttpStatus.OK);
    }
    @PostMapping
    @CheckSecurity(roles = {"ROLE_CLIENT","ROLE_ADMIN"})
    public ResponseEntity<ReservationDto> addReservation(@RequestHeader("Authorization") String authorization, @RequestBody ReservationCreateDto reservationCreateDto){
        Claims claims = tokenService.parseToken(authorization.split(" ")[1]);
        Long id = Long.parseLong(claims.get("id").toString());
        return new ResponseEntity<>(reservationService.addReservation(reservationCreateDto,id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @CheckSecurity(roles = {"ROLE_CLIENT", "ROLE_MANAGER", "ROLE_ADMIN"})
    public ResponseEntity<?> deleteReservation(@RequestHeader("Authorization") String authorization, @PathVariable("id") Long id) {
        reservationService.deleteReservation(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
