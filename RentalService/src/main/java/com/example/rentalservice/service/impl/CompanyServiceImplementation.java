package com.example.rentalservice.service.impl;

import com.example.rentalservice.dto.CompanyCreateDto;
import com.example.rentalservice.dto.CompanyDto;
import com.example.rentalservice.mapper.CompanyMapper;
import com.example.rentalservice.model.Company;
import com.example.rentalservice.repository.CompanyRepository;
import com.example.rentalservice.service.CompanyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImplementation implements CompanyService {
    private CompanyMapper companyMapper;
    private CompanyRepository companyRepository;

    public CompanyServiceImplementation(CompanyMapper companyMapper, CompanyRepository companyRepository) {
        this.companyMapper = companyMapper;
        this.companyRepository = companyRepository;
    }

    @Override
    public CompanyDto addCompany(CompanyCreateDto companyCreateDto) {
        Company company =  companyMapper.companyCreateDtoToCompany(companyCreateDto);
        companyRepository.save(company);
        return companyMapper.companyToCompanyDto(company);
    }

    @Override
    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }

    @Override
    public Page<CompanyDto> findAll(Pageable pageable) {
        return companyRepository.findAll(pageable).map(companyMapper::companyToCompanyDto);
    }

    @Override
    public CompanyDto findById(Long id) {
        return companyMapper.companyToCompanyDto(companyRepository.findById(id).get());
    }
}
