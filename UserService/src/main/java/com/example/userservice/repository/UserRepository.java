package com.example.userservice.repository;

import com.example.userservice.domain.User;
import com.example.userservice.dto.EmailIdRentalResponseDto;
import com.example.userservice.dto.RentalResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User getAllByEmail(String email);
    Optional<User> getUserByEmailAndPassword(String email, String password);

    @Modifying
    @Query("update User u set u.restricted = true")
    void setUserRestrictedByEmail(String email);

    Optional<User> findById(Long id);

    @Query("SELECT u FROM User u WHERE u.id = :id")
    User custumFindById(@Param("id") Long id);





}
