package com.example.userservice.repository;

import com.example.userservice.domain.Rank;
import com.example.userservice.dto.DiscountResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface RankRepository extends JpaRepository<Rank, Long> {
    Optional<Rank> getByName(String name);

    Optional<DiscountResponseDto> getDiscountById(Long id);
}
