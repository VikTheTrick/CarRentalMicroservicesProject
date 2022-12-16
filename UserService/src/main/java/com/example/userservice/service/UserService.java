package com.example.userservice.service;

import com.example.userservice.dtos.ClientCreateDto;
import com.example.userservice.dtos.ManagerCreateDto;
import com.example.userservice.dtos.TokenRequestDto;
import com.example.userservice.dtos.TokenResponseDto;

public interface UserService {
    ClientCreateDto addClient(ClientCreateDto clientCreateDto);
    boolean addManager(ManagerCreateDto managerCreateDto);
    boolean restrictUser(String email);
    TokenResponseDto login(TokenRequestDto tokenRequestDto);

}
