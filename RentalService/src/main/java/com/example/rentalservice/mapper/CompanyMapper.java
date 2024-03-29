package com.example.rentalservice.mapper;

import com.example.rentalservice.dto.CompanyCreateDto;
import com.example.rentalservice.dto.CompanyDto;
import com.example.rentalservice.model.Company;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {
    public CompanyDto companyToCompanyDto(Company company) {
        CompanyDto companyDto = new CompanyDto();
        companyDto.setDescription(company.getDescription());
        companyDto.setTitle(company.getTitle());
        companyDto.setId(company.getId());
        companyDto.setProsek(company.getSumOfGrades() * 1.0 / company.getNumberOfGrades());
        return companyDto;
    }

    public Company companyCreateDtoToCompany(CompanyCreateDto companyCreateDto, Long id) {
        Company company = new Company();
        company.setDescription(companyCreateDto.getDescription());
        company.setTitle(companyCreateDto.getTitle());
        company.setManagerId(id);
        return company;
    }
}
