package com.example.rentalservice.controller;

import com.example.rentalservice.dto.CompanyDto;
import com.example.rentalservice.dto.OverviewCreateDto;
import com.example.rentalservice.dto.OverviewDto;
import com.example.rentalservice.dto.OverviewUpdateDto;
import com.example.rentalservice.security.CheckSecurity;
import com.example.rentalservice.security.service.TokenService;
import com.example.rentalservice.service.OverviewService;
import io.jsonwebtoken.Claims;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@RequestMapping("/overview")
public class OverviewController {
    private OverviewService overviewService;
    private TokenService tokenService;

    public OverviewController(OverviewService overviewService, TokenService tokenService) {
        this.overviewService = overviewService;
        this.tokenService = tokenService;
    }

    @GetMapping
    public ResponseEntity<Page<OverviewDto>> findAll(@ApiIgnore Pageable pageable){
        return new ResponseEntity<>(overviewService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Page<OverviewDto>> findAll(@PathVariable("id") Long company_id, @ApiIgnore Pageable pageable){
        return new ResponseEntity<>(overviewService.findByCompany_id(company_id, pageable), HttpStatus.OK);
    }

    @GetMapping("/companies")
    public ResponseEntity<Page<CompanyDto>> overviewsByCompany(@ApiIgnore Pageable pageable){
        return new ResponseEntity<>(overviewService.overviewsByCompany(pageable), HttpStatus.OK);
    }
    @PostMapping
    @CheckSecurity(roles = {"ROLE_CLIENT", "ROLE_ADMIN"})
    public ResponseEntity<OverviewDto> addOverview(@RequestHeader("Authorization") String authorization, @RequestBody @Valid OverviewCreateDto overviewCreateDto){
        Claims claims = tokenService.parseToken(authorization.split(" ")[1]);
        Long id = Long.parseLong(claims.get("id").toString());
        return new ResponseEntity<>(overviewService.addOverview(overviewCreateDto, id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @CheckSecurity(roles = {"ROLE_CLIENT"})
    public ResponseEntity<OverviewDto> updateOverview(@PathVariable("id") Long id
            , @RequestBody @Valid OverviewUpdateDto overviewUpdateDto) {

        return new ResponseEntity<>(overviewService.updateOverview(id, overviewUpdateDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @CheckSecurity(roles = {"ROLE_CLIENT", "ROLE_ADMIN"})
    public ResponseEntity<?> deleteOverview(@RequestHeader("Authorization") String authorization, @PathVariable("id") Long id) {
        overviewService.deleteOverview(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
