package com.example.rentalservice.repository;

import com.example.rentalservice.model.Overview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OverviewRepository extends JpaRepository<Overview,Long> {
    Page<Overview> findByCompany_id(Long companyId, Pageable pageable);
}
