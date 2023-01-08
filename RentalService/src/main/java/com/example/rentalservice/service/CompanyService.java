package com.example.rentalservice.service;

import com.example.rentalservice.dto.CompanyCreateDto;
import com.example.rentalservice.dto.CompanyDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyService {
    CompanyDto addCompany(CompanyCreateDto reservationCreateDto);
    void deleteCompany(Long id);

    Page<CompanyDto> findAll(Pageable pageable);
}
