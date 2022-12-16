package com.example.userservice.service;

import com.example.userservice.domain.User;
import com.example.userservice.dtos.ClientCreateDto;
import com.example.userservice.dtos.ManagerCreateDto;
import com.example.userservice.dtos.TokenRequestDto;
import com.example.userservice.dtos.TokenResponseDto;
import com.example.userservice.exception.NotFoundException;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.security.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private TokenService tokenService;

    public UserServiceImplementation(UserRepository userRepository, UserMapper userMapper, BCryptPasswordEncoder passwordEncoder, TokenService tokenService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }


    @Override
    public ClientCreateDto addClient(ClientCreateDto clientCreateDto) {
       /* User check = userRepository.getAllByEmail(clientCreateDto.getEmail());
        if(check != null)
        {
            return false;
        }
        //posalji mejl preko notification servisa
        */
        clientCreateDto.setPassword(clientCreateDto.getPassword());


        try {
            userRepository.save(userMapper.CreateClientDtoToClient(clientCreateDto));
            return clientCreateDto;
        }catch (Exception e)
        {
            return null;
        }
        }

    @Override
    public boolean addManager(ManagerCreateDto managerCreateDto) {
        User check = userRepository.getAllByEmail(managerCreateDto.getEmail());
        if(check != null)
        {
            return false;
        }
        managerCreateDto.setPassword(managerCreateDto.getPassword());
        userRepository.save(userMapper.CreateManagerDtoToManager(managerCreateDto));
        return true;
    }

    @Override
    public boolean restrictUser(String email) {
        return false;
    }

    @Override
    public TokenResponseDto login(TokenRequestDto tokenRequestDto) {
        //Try to find active user for specified credentials
        User user = userRepository
                .getUserByEmailAndPassword(tokenRequestDto.getEmail(), tokenRequestDto.getPassword())
                .orElseThrow(() -> new NotFoundException(String
                        .format("User with username: %s and password: %s not found.", tokenRequestDto.getEmail(),
                                tokenRequestDto.getPassword())));
        //Create token payload
        Claims claims = Jwts.claims();
        claims.put("id", user.getId());
        claims.put("role", "Admin");
        //Generate token
        return new TokenResponseDto(tokenService.generate(claims));
    }


}
