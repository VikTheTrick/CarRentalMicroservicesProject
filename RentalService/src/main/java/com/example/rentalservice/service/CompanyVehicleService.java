package com.example.rentalservice.service;

import com.example.rentalservice.dto.CompanyVehicleCreateDto;
import com.example.rentalservice.dto.CompanyVehicleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyVehicleService {
    CompanyVehicleDto addCompanyVehicle(CompanyVehicleCreateDto companyVehicleCreateDto);
    Page<CompanyVehicleDto> findAll(Pageable pageable);
    Page<CompanyVehicleDto> findByCompany_id(Long id, Pageable pageable);
    void deleteCompanyVehicle(Long id);
}
