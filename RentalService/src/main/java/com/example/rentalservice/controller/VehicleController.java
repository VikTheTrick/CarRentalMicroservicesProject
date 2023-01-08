package com.example.rentalservice.controller;

import com.example.rentalservice.dto.VehicleCreateDto;
import com.example.rentalservice.dto.VehicleDto;
import com.example.rentalservice.security.CheckSecurity;
import com.example.rentalservice.service.VehicleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    private VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping
    @CheckSecurity(roles={"ROLE_ADMIN"})
    public ResponseEntity<Page<VehicleDto>> findAll(@RequestHeader("Authorization") String authorization,@ApiIgnore Pageable pageable){
        return new ResponseEntity<>(vehicleService.findAll(pageable), HttpStatus.OK);
    }
    @PostMapping
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<VehicleDto> addVehicle(@RequestHeader("Authorization") String authorization, @RequestBody VehicleCreateDto vehicleCreateDto){
        return new ResponseEntity<>(vehicleService.addVehicle(vehicleCreateDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<?> deleteVehicle(@RequestHeader("Authorization") String authorization, @PathVariable("id") Long id) {
        vehicleService.deleteVehicle(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
