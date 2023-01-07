package com.example.rentalservice.mapper;

import com.example.rentalservice.dto.CompanyVehicleCreateDto;
import com.example.rentalservice.dto.CompanyVehicleDto;
import com.example.rentalservice.model.CompanyVehicle;
import com.example.rentalservice.repository.CompanyRepository;
import com.example.rentalservice.repository.VehicleRepository;
import org.springframework.stereotype.Component;

@Component
public class CompanyVehicleMapper {
    private CompanyRepository companyRepository;
    private VehicleRepository vehicleRepository;

    private CompanyMapper companyMapper;

    private VehicleMapper vehicleMapper;

    public CompanyVehicleMapper(CompanyRepository companyRepository, VehicleRepository vehicleRepository, CompanyMapper companyMapper, VehicleMapper vehicleMapper) {
        this.companyRepository = companyRepository;
        this.vehicleRepository = vehicleRepository;
        this.companyMapper = companyMapper;
        this.vehicleMapper = vehicleMapper;
    }

    public CompanyVehicle companyVehicleCreateDtoToCompanyVehicle(CompanyVehicleCreateDto companyVehicleCreateDto) {
        CompanyVehicle companyVehicle = new CompanyVehicle();
        companyVehicle.setCompany(companyRepository.findById(companyVehicleCreateDto.getCompanyid()).get());
        companyVehicle.setVehicle(vehicleRepository.findById(companyVehicleCreateDto.getVehicleid()).get());
        companyVehicle.setNumberOfVehicle(companyVehicleCreateDto.getNumberOfVehicle());
        companyVehicle.setPrice(companyVehicleCreateDto.getPrice());
        return companyVehicle;
    }

    public CompanyVehicleDto companyVehicleToCompanyVehicleDto(CompanyVehicle companyVehicle) {
        CompanyVehicleDto companyVehicleDto = new CompanyVehicleDto();
        companyVehicleDto.setCompanyDto(companyMapper.companyToCompanyDto(companyVehicle.getCompany()));
        companyVehicleDto.setVehicleDto(vehicleMapper.vehicleToVehicleDto(companyVehicle.getVehicle()));
        companyVehicleDto.setNumberOfVehicle(companyVehicle.getNumberOfVehicle());
        companyVehicleDto.setPrice(companyVehicle.getPrice());
        companyVehicleDto.setId(companyVehicle.getId());
        return companyVehicleDto;
    }
}
