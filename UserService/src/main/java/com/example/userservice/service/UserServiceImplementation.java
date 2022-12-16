package com.example.userservice.service;

import com.example.userservice.domain.User;
import com.example.userservice.domain.userTypes.Admin;
import com.example.userservice.dtos.*;
import com.example.userservice.exception.NotFoundException;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.security.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.Locale;

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
        User a = userRepository.getAllByEmail("m@gmail.com");
        if(a == null)
        userRepository.save(new Admin("mare","123","m@gmail.com",Date.valueOf("2001-2-22"),"marko", "lekic"));
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
    public ResponseEntity restrictUser(String email) {
        userRepository.setUserInfoByEmail(email);
        return new ResponseEntity(HttpStatus.ACCEPTED);

    }

    @Override
    public TokenResponseDto login(TokenRequestDto tokenRequestDto) {
        //Try to find active user for specified credentials
        User user = userRepository
                .getUserByEmailAndPassword(tokenRequestDto.getEmail(), tokenRequestDto.getPassword())
                .orElseThrow(() -> new NotFoundException(String
                        .format("User with username: %s and password: %s not found.", tokenRequestDto.getEmail(),
                                tokenRequestDto.getPassword())));
        if(user.getRestricted())
            return new TokenResponseDto("This account has been restricted");
        //Create token payload
        Claims claims = Jwts.claims();
        claims.put("id", user.getId());
        String userRole = "ROLE_" + user.getClass().getSimpleName().toUpperCase(Locale.ROOT);
        claims.put("role", userRole);
        //Generate token
        return new TokenResponseDto(tokenService.generate(claims));
    }


}
