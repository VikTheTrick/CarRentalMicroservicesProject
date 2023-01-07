package com.example.rentalservice.repository;

import com.example.rentalservice.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company,Long> {
}
