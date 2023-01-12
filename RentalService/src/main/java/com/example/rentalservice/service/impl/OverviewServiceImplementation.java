package com.example.rentalservice.service.impl;

import com.example.rentalservice.dto.CompanyDto;
import com.example.rentalservice.dto.OverviewCreateDto;
import com.example.rentalservice.dto.OverviewDto;
import com.example.rentalservice.dto.OverviewUpdateDto;
import com.example.rentalservice.mapper.CompanyMapper;
import com.example.rentalservice.mapper.OverviewMapper;
import com.example.rentalservice.model.Company;
import com.example.rentalservice.model.Overview;
import com.example.rentalservice.repository.CompanyRepository;
import com.example.rentalservice.repository.OverviewRepository;
import com.example.rentalservice.service.OverviewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OverviewServiceImplementation implements OverviewService {
    private OverviewMapper overviewMapper;
    private OverviewRepository overviewRepository;
    private CompanyRepository companyRepository;
    private CompanyMapper companyMapper;

    public OverviewServiceImplementation(OverviewMapper overviewMapper, OverviewRepository overviewRepository, CompanyRepository companyRepository, CompanyMapper companyMapper) {
        this.overviewMapper = overviewMapper;
        this.overviewRepository = overviewRepository;
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }

    @Override
    public OverviewDto addOverview(OverviewCreateDto overviewCreateDto) {
        Overview overview = overviewMapper.overviewCreateDtoToOverview(overviewCreateDto);
        Company company = companyRepository.findById(overviewCreateDto.getCompanyid()).get();
        company.setNumberOfGrades(company.getNumberOfGrades() + 1);
        company.setSumOfGrades(company.getSumOfGrades() + overviewCreateDto.getGrade());
        companyRepository.save(company);
        return overviewMapper.overviewToOverviewDto(overviewRepository.save(overview));
    }

    @Override
    public OverviewDto updateOverview(Long id, OverviewUpdateDto overviewUpdateDto) {
        Overview overview = overviewRepository.findById(id).get();
        Company company = companyRepository.findById(overview.getCompany().getId()).get();
        company.setSumOfGrades(company.getSumOfGrades() + overviewUpdateDto.getGrade() - overview.getGrade());
        overview.setComment(overviewUpdateDto.getComment());
        overview.setGrade(overviewUpdateDto.getGrade());
        overviewRepository.save(overview);
        companyRepository.save(company);
        return overviewMapper.overviewToOverviewDto(overview);
    }

    @Override
    public Page<OverviewDto> findAll(Pageable pageable) {
        return overviewRepository.findAll(pageable).map(overviewMapper::overviewToOverviewDto);
    }

    @Override
    public Page<OverviewDto> findByCompany_id(Long company_id, Pageable pageable) {
        return overviewRepository.findByCompany_id(company_id, pageable).map(overviewMapper::overviewToOverviewDto);
    }

    @Override
    public Page<CompanyDto> overviewsByCompany(Pageable pageable) {
        List<CompanyDto> companyDtos = companyRepository.findAll().stream().filter(c -> c.getNumberOfGrades() > 0).sorted(new Comparator<Company>() {
            @Override
            public int compare(Company o1, Company o2) {
                if(o1.getSumOfGrades() / o1.getNumberOfGrades() > o2.getSumOfGrades() / o2.getNumberOfGrades()) return -1;
                if(o1.getSumOfGrades() / o1.getNumberOfGrades() == o2.getSumOfGrades() / o2.getNumberOfGrades()) return 0;
                return 1;
            }
        }).map(companyMapper::companyToCompanyDto).collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = (int) ((start + pageable.getPageSize()) > companyDtos.size() ? companyDtos.size()
                : (start + pageable.getPageSize()));

        return new PageImpl<>(companyDtos.subList(start,end),pageable,companyDtos.size());
    }

    @Override
    public void deleteOverview(Long id) {
        Overview overview = overviewRepository.findById(id).get();
        Company company = companyRepository.findById(overview.getCompany().getId()).get();
        company.setSumOfGrades(company.getSumOfGrades() + overview.getGrade());
        company.setNumberOfGrades(company.getNumberOfGrades() - 1);
        companyRepository.save(company);
        //overviewRepository.delete(overview);
        overviewRepository.deleteById(id);
    }
}
