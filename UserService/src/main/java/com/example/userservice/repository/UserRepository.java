package com.example.userservice.repository;

import com.example.userservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User getAllByEmail(String email);
    Optional<User> getUserByEmailAndPassword(String email, String password);

    @Modifying
    @Query("update User u set u.restricted = true")
    void setUserRestrictedByEmail(String email);

    Optional<User> findById(Long id);




}
