package com.example.rentalservice.repository;

import com.example.rentalservice.model.CompanyVehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyVehicleRepository extends JpaRepository<CompanyVehicle,Long> {
    CompanyVehicle findByVehicle_idAndCompany_id(Long vehicleid, Long companyid);
    Page<CompanyVehicle> findByCompany_id(Long companyid, Pageable pageable);
}
