package com.example.userservice.service;

import com.example.userservice.domain.User;
import com.example.userservice.domain.userTypes.Client;
import com.example.userservice.dtos.ClientCreateDto;
import com.example.userservice.dtos.ManagerCreateDto;
import com.example.userservice.dtos.UserCreateDto;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    public UserServiceImplementation(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }


    @Override
    public boolean addClient(ClientCreateDto clientCreateDto) {
        User check = userRepository.getAllByEmail(clientCreateDto.getEmail());
        if(check != null)
        {
            return false;
        }
        userRepository.save(userMapper.CreateClientDtoToClient(clientCreateDto));
        return true;
    }

    @Override
    public boolean addManager(ManagerCreateDto managerCreateDto) {
        User check = userRepository.getAllByEmail(managerCreateDto.getEmail());
        if(check != null)
        {
            return false;
        }
        userRepository.save(userMapper.CreateManagerDtoToManager(managerCreateDto));
        return true;
    }
}
