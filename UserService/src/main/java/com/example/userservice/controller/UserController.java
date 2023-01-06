package com.example.userservice.controller;

import com.example.userservice.dto.*;
import com.example.userservice.dto.userUpdate.UserUpdateDto;
import com.example.userservice.security.CheckSecurity;
import com.example.userservice.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "Register client")
    @PostMapping("/registerClient")
    public ResponseEntity<ClientCreateDto> add(@RequestBody ClientCreateDto clientCreateDtoCreateDto)
    {
        return new ResponseEntity(userService.addClient(clientCreateDtoCreateDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Verify client email")
    @GetMapping(value="verify/{registrationKey}")
    public ResponseEntity verifyClient(@PathVariable(value = "registrationKey") String registrationKey)
    {
       return userService.verifyClient(registrationKey);
    }

    @PostMapping("/registerManager")
    public ResponseEntity<ManagerCreateDto> add(@RequestBody ManagerCreateDto managerCreateDtoCreateDto)
    {
        return userService.addManager(managerCreateDtoCreateDto);
    }

    @ApiOperation(value = "Login")
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> loginUser(@RequestBody TokenRequestDto tokenRequestDto) {
        return userService.login(tokenRequestDto);
    }

    @ApiOperation(value = "restrict")
    @GetMapping("/restrict/{email}")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity restrictUser(@RequestHeader("Authorization") String authorization,@PathVariable(value="email") String email)
    {
        return userService.restrictUser(email);
    }

    @ApiOperation(value = "update user params")
    @PostMapping("/update")
    public ResponseEntity updateUser(@RequestHeader("Authorization") String authorization, @RequestBody UserUpdateDto userUpdateDto)
    {
      return userService.updateUser(authorization, userUpdateDto);
    }

    @ApiOperation(value = "add/update rank")
    @PostMapping("/addRank")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity addRank(@RequestHeader("Authorization") String authorization, @RequestBody AddRankDto addRankDto )
    {
        return userService.addRank(addRankDto);
    }
    @ApiOperation(value = "get discount and email from user id")
    @GetMapping("/getRentalInfo/{id}")
    public ResponseEntity<RentalResponseDto> getRentalInfoById(@PathVariable (value = "id") Long id)
    {
        return userService.getRentalInfoById(id);
    }

    @ApiOperation(value =  "change rental days")
    @PostMapping("/changeRentalDays")
    public ResponseEntity changeRentalDays(@RequestBody ChangeDaysRentedDto changeDaysRentedDto)
    {
        return userService.changeDaysRented(changeDaysRentedDto);
    }

    @GetMapping("/test")
    public String test()
    {
        userService.test();
        return "sss";
    }

}
