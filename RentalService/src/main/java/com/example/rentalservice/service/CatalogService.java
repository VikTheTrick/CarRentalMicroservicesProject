package com.example.rentalservice.service;

import com.example.rentalservice.dto.CompanyVehicleDto;
import com.example.rentalservice.dto.PeriodDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CatalogService {
    Page<CompanyVehicleDto> findAll(PeriodDto periodDto, Pageable pageable);
    Page<CompanyVehicleDto> findByCompany(PeriodDto periodDto, Long id, Pageable pageable);
}
