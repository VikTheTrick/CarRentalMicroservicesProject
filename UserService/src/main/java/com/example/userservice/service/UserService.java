package com.example.userservice.service;

import com.example.userservice.dto.*;
import com.example.userservice.dto.userUpdate.UserUpdateDto;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<ClientCreateDto> addClient(ClientCreateDto clientCreateDto);
    ResponseEntity<ManagerCreateDto> addManager(ManagerCreateDto managerCreateDto);
    ResponseEntity restrictUser(String email);
    ResponseEntity<TokenResponseDto> login(TokenRequestDto tokenRequestDto);
    ResponseEntity updateUser(String userId, UserUpdateDto userUpdateDto);
    ResponseEntity addRank(AddRankDto addRankDto);
    ResponseEntity verifyClient(String clientToken);
    ResponseEntity<RentalResponseDto> getRentalInfoById(Long id);
    ResponseEntity changeDaysRented (ChangeDaysRentedDto changeDaysRentedDto);
    void test();

}
