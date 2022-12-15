package com.example.userservice.controller;

import com.example.userservice.domain.User;
import com.example.userservice.domain.userTypes.Admin;
import com.example.userservice.domain.userTypes.Client;
import com.example.userservice.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<User> add()
    {
        return new ResponseEntity<>(userRepository.save(new Client("aki","baki","sss", new Date(25112006),"sss","kusi",111111,0.8)), HttpStatus.CREATED);
    }

    @GetMapping
    public String test()
    {
        return "sss";
    }
}
