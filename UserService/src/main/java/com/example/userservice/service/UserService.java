package com.example.userservice.service;

import com.example.userservice.domain.userTypes.Client;
import com.example.userservice.dtos.ClientCreateDto;
import com.example.userservice.dtos.ManagerCreateDto;
import com.example.userservice.dtos.UserCreateDto;

public interface UserService {
    boolean addClient(ClientCreateDto clientCreateDto);
    boolean addManager(ManagerCreateDto managerCreateDto);


}
