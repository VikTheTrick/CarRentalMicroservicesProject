package com.example.rentalservice.controller;

import com.example.rentalservice.dto.CompanyVehicleCreateDto;
import com.example.rentalservice.dto.CompanyVehicleDto;
import com.example.rentalservice.security.CheckSecurity;
import com.example.rentalservice.service.CompanyVehicleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/companyvehicle")
public class CompanyVehicleController {
    private CompanyVehicleService companyVehicleService;

    public CompanyVehicleController(CompanyVehicleService companyVehicleService) {
        this.companyVehicleService = companyVehicleService;
    }

    @GetMapping
    @CheckSecurity(roles={"ROLE_ADMIN"})
    public ResponseEntity<Page<CompanyVehicleDto>> findAll(@RequestHeader("Authorization") String authorization,@ApiIgnore Pageable pageable){
        return new ResponseEntity<>(companyVehicleService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @CheckSecurity(roles = {"ROLE_MANAGER"})
    public ResponseEntity<Page<CompanyVehicleDto>> findByCompany(@RequestHeader("Authorization") String authorization, @PathVariable("id") Long id, @ApiIgnore Pageable pageable) {
        return new ResponseEntity<>(companyVehicleService.findByCompany_id(id,pageable),HttpStatus.OK);
    }

    @PostMapping
    @CheckSecurity(roles = {"ROLE_MANAGER"})
    public ResponseEntity<CompanyVehicleDto> addCompanyVehicle(@RequestHeader("Authorization") String authorization, @RequestBody CompanyVehicleCreateDto companyVehicleCreateDto){
        return new ResponseEntity<>(companyVehicleService.addCompanyVehicle(companyVehicleCreateDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @CheckSecurity(roles = {"ROLE_MANAGER"})
    public ResponseEntity<?> deleteCompanyVehicle(@RequestHeader("Authorization") String authorization, @PathVariable("id") Long id) {
        companyVehicleService.deleteCompanyVehicle(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
