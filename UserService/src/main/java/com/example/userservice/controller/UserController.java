package com.example.userservice.controller;

import com.example.userservice.dtos.ClientCreateDto;
import com.example.userservice.dtos.ManagerCreateDto;
import com.example.userservice.dtos.TokenRequestDto;
import com.example.userservice.dtos.TokenResponseDto;
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
    @PostMapping("/registerManager")
    public ResponseEntity<ClientCreateDto> add(@RequestBody ManagerCreateDto managerCreateDtoCreateDto)
    {
        return new ResponseEntity(userService.addManager(managerCreateDtoCreateDto), HttpStatus.CREATED);
    }
    @ApiOperation(value = "Login")
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> loginUser(@RequestBody TokenRequestDto tokenRequestDto) {
        return new ResponseEntity<>(userService.login(tokenRequestDto), HttpStatus.OK);
    }
    @GetMapping
    public String test()
    {
        return "sss";
    }
}
