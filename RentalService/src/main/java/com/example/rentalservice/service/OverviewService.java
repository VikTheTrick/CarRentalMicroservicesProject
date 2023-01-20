package com.example.rentalservice.service;

import com.example.rentalservice.dto.CompanyDto;
import com.example.rentalservice.dto.OverviewCreateDto;
import com.example.rentalservice.dto.OverviewDto;
import com.example.rentalservice.dto.OverviewUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OverviewService {
    OverviewDto addOverview(OverviewCreateDto overviewCreateDto, Long id);

    OverviewDto updateOverview(Long id, OverviewUpdateDto overviewUpdateDto);

    Page<OverviewDto> findAll(Pageable pageable);
    Page<OverviewDto> findByCompany_id(Long company_id, Pageable pageable);

    Page<CompanyDto> overviewsByCompany(Pageable pageable);


    void deleteOverview(Long id);
}
