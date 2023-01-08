package com.example.rentalservice.service.impl;

import com.example.rentalservice.dto.CompanyVehicleCreateDto;
import com.example.rentalservice.dto.CompanyVehicleDto;
import com.example.rentalservice.mapper.CompanyVehicleMapper;
import com.example.rentalservice.model.CompanyVehicle;
import com.example.rentalservice.repository.CompanyVehicleRepository;
import com.example.rentalservice.service.CompanyVehicleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CompanyVehicleServiceImplementation implements CompanyVehicleService {
    private CompanyVehicleMapper companyVehicleMapper;
    private CompanyVehicleRepository companyVehicleRepository;

    public CompanyVehicleServiceImplementation(CompanyVehicleMapper companyVehicleMapper, CompanyVehicleRepository companyVehicleRepository) {
        this.companyVehicleMapper = companyVehicleMapper;
        this.companyVehicleRepository = companyVehicleRepository;
    }

    @Override
    public CompanyVehicleDto addCompanyVehicle(CompanyVehicleCreateDto companyVehicleCreateDto) {
        CompanyVehicle companyVehicle = companyVehicleMapper.companyVehicleCreateDtoToCompanyVehicle(companyVehicleCreateDto);
        return companyVehicleMapper.companyVehicleToCompanyVehicleDto(companyVehicleRepository.save(companyVehicle));
    }

    @Override
    public Page<CompanyVehicleDto> findAll(Pageable pageable) {
        return companyVehicleRepository.findAll(pageable).map(companyVehicleMapper::companyVehicleToCompanyVehicleDto);
    }

    @Override
    public Page<CompanyVehicleDto> findByCompany_id(Long id, Pageable pageable) {
        return companyVehicleRepository.findByCompany_id(id, pageable).map(companyVehicleMapper::companyVehicleToCompanyVehicleDto);
    }

    @Override
    public void deleteCompanyVehicle(Long id) {
        companyVehicleRepository.deleteById(id);
    }
}
