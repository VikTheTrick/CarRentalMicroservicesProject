package com.example.rentalservice.mapper;

import com.example.rentalservice.dto.OverviewCreateDto;
import com.example.rentalservice.dto.OverviewDto;
import com.example.rentalservice.model.Overview;
import com.example.rentalservice.repository.CompanyRepository;
import org.springframework.stereotype.Component;

@Component
public class OverviewMapper {
    private CompanyRepository companyRepository;
    private CompanyMapper companyMapper;

    public OverviewMapper(CompanyRepository companyRepository, CompanyMapper companyMapper) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }

    public OverviewDto overviewToOverviewDto(Overview overview) {
        OverviewDto overviewDto = new OverviewDto();
        overviewDto.setUserid(overview.getUserid());
        overviewDto.setCompanyDto(companyMapper.companyToCompanyDto(overview.getCompany()));
        overviewDto.setGrade(overview.getGrade());
        overviewDto.setComment(overview.getComment());
        overviewDto.setId(overview.getId());
        return overviewDto;
    }

    public Overview overviewCreateDtoToOverview(OverviewCreateDto overviewCreateDto, Long id) {
        Overview overview = new Overview();
        overview.setComment(overviewCreateDto.getComment());
        overview.setUserid(id);
        overview.setGrade(overviewCreateDto.getGrade());
        overview.setCompany(companyRepository.findById(overviewCreateDto.getCompanyid()).get());
        return overview;
    }
}
