package com.example.rentalservice.controller;

import com.example.rentalservice.dto.CompanyCreateDto;
import com.example.rentalservice.dto.CompanyDto;
import com.example.rentalservice.security.CheckSecurity;
import com.example.rentalservice.service.CompanyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/company")
public class CompanyController {

    private CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }


    @GetMapping
    @CheckSecurity(roles={"ROLE_ADMIN"})
    public ResponseEntity<Page<CompanyDto>> findAll(@RequestHeader("Authorization") String authorization,@ApiIgnore Pageable pageable){
        return new ResponseEntity<>(companyService.findAll(pageable), HttpStatus.OK);
    }

    @PostMapping
    @CheckSecurity(roles = {"ROLE_CLIENT", "ROLE_ADMIN"})
    public ResponseEntity<CompanyDto> addCompany(@RequestHeader("Authorization") String authorization, @RequestBody CompanyCreateDto companyCreateDto){
        return new ResponseEntity<>(companyService.addCompany(companyCreateDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @CheckSecurity(roles = {"ROLE_MANAGER","ROLE_ADMIN"})
    public ResponseEntity<?> deleteCompany(@RequestHeader("Authorization") String authorization, @PathVariable("id") Long id) {
        companyService.deleteCompany(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
