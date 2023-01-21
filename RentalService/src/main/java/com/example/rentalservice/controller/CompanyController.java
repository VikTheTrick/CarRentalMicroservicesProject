package com.example.rentalservice.controller;

import com.example.rentalservice.dto.CompanyCreateDto;
import com.example.rentalservice.dto.CompanyDto;
import com.example.rentalservice.security.CheckSecurity;
import com.example.rentalservice.security.service.TokenService;
import com.example.rentalservice.service.CompanyService;
import io.jsonwebtoken.Claims;
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
    private TokenService tokenService;

    public CompanyController(CompanyService companyService, TokenService tokenService) {
        this.companyService = companyService;
        this.tokenService = tokenService;
    }


    @GetMapping
    @CheckSecurity(roles={"ROLE_ADMIN"})
    public ResponseEntity<Page<CompanyDto>> findAll(@RequestHeader("Authorization") String authorization,@ApiIgnore Pageable pageable){
        return new ResponseEntity<>(companyService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @CheckSecurity(roles={"ROLE_ADMIN"})
    public ResponseEntity<CompanyDto> findById(@RequestHeader("Authorization") String authorization,@PathVariable("id") Long id){
        return new ResponseEntity<>(companyService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    @CheckSecurity(roles = {"ROLE_CLIENT", "ROLE_ADMIN"})
    public ResponseEntity<CompanyDto> addCompany(@RequestHeader("Authorization") String authorization, @RequestBody CompanyCreateDto companyCreateDto){
        Claims claims = tokenService.parseToken(authorization.split(" ")[1]);
        Long id = Long.parseLong(claims.get("id").toString());
        return new ResponseEntity<>(companyService.addCompany(companyCreateDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @CheckSecurity(roles = {"ROLE_MANAGER","ROLE_ADMIN"})
    public ResponseEntity<?> deleteCompany(@RequestHeader("Authorization") String authorization, @PathVariable("id") Long id) {
        companyService.deleteCompany(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
