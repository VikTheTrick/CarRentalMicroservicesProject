package com.example.userservice.service;

import com.example.userservice.dtos.*;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ClientCreateDto addClient(ClientCreateDto clientCreateDto);
    boolean addManager(ManagerCreateDto managerCreateDto);
    ResponseEntity restrictUser(String email);
    TokenResponseDto login(TokenRequestDto tokenRequestDto);

}
