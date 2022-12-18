package com.example.userservice.service;

import com.example.userservice.domain.Rank;
import com.example.userservice.dtos.*;
import com.example.userservice.dtos.userUpdate.UserUpdateDto;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<ClientCreateDto> addClient(ClientCreateDto clientCreateDto);
    boolean addManager(ManagerCreateDto managerCreateDto);
    ResponseEntity restrictUser(String email);
    TokenResponseDto login(TokenRequestDto tokenRequestDto);
    ResponseEntity updateUser(String userId, UserUpdateDto userUpdateDto);
    ResponseEntity addRank(AddRankDto addRankDto);

}
