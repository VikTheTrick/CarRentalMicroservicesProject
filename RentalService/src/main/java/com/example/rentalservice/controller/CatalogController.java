package com.example.rentalservice.controller;

import com.example.rentalservice.dto.CompanyVehicleDto;
import com.example.rentalservice.dto.PeriodDto;
import com.example.rentalservice.service.CatalogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/catalog")
public class CatalogController {
    private CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping
    public ResponseEntity<Page<CompanyVehicleDto>> findAll(@RequestBody PeriodDto periodDto, @ApiIgnore Pageable pageable){
        return new ResponseEntity<>(catalogService.findAll(periodDto, pageable), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Page<CompanyVehicleDto>> findByCompany(@RequestBody PeriodDto periodDto, @PathVariable("id") Long companyid, Pageable pageable){
        return new ResponseEntity<>(catalogService.findByCompany(periodDto,companyid, pageable), HttpStatus.OK);
    }
}
