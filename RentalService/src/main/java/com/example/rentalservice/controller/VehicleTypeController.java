package com.example.rentalservice.controller;

import com.example.rentalservice.dto.VehicleTypeCreateDto;
import com.example.rentalservice.dto.VehicleTypeDto;
import com.example.rentalservice.security.CheckSecurity;
import com.example.rentalservice.service.VehicleTypeService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/vehicletype")
public class VehicleTypeController {
    private VehicleTypeService vehicleTypeService;

    public VehicleTypeController(VehicleTypeService vehicleTypeService) {
        this.vehicleTypeService = vehicleTypeService;
    }

    @GetMapping
    @CheckSecurity(roles={"ROLE_ADMIN"})
    public ResponseEntity<Page<VehicleTypeDto>> findAll(@RequestHeader("Authorization") String authorization,@ApiIgnore Pageable pageable){
        return new ResponseEntity<>(vehicleTypeService.findAll(pageable), HttpStatus.OK);
    }

    @PostMapping
    @CheckSecurity(roles={"ROLE_ADMIN"})
    public ResponseEntity<VehicleTypeDto> addVehicleType(@RequestHeader("Authorization") String authorization,@RequestBody VehicleTypeCreateDto vehicleTypeCreateDto){
        return new ResponseEntity<>(vehicleTypeService.addVehicleType(vehicleTypeCreateDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @CheckSecurity(roles={"ROLE_ADMIN"})
    public ResponseEntity<?> deleteVehicleType(@RequestHeader("Authorization") String authorization,@PathVariable("id") Long id) {
        vehicleTypeService.deleteVehicleType(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
