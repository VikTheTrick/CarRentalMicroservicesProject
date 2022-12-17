package com.example.userservice.service;

import com.example.userservice.domain.Rank;
import com.example.userservice.domain.User;
import com.example.userservice.domain.userTypes.Admin;
import com.example.userservice.domain.userTypes.Client;
import com.example.userservice.domain.userTypes.Manager;
import com.example.userservice.dtos.*;
import com.example.userservice.dtos.userUpdate.UserUpdateDto;
import com.example.userservice.exception.NotFoundException;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.repository.RankRepository;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.security.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.Locale;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final RankRepository rankRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private TokenService tokenService;


    public UserServiceImplementation(UserRepository userRepository, RankRepository rankRepository, UserMapper userMapper, BCryptPasswordEncoder passwordEncoder, TokenService tokenService) {
        this.userRepository = userRepository;
        this.rankRepository = rankRepository;
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
        userRepository.setUserRestrictedByEmail(email);
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

    @Override
    public ResponseEntity updateUser(String authToken, UserUpdateDto userUpdateDto) {
        Claims claims = tokenService.parseToken(authToken.split(" ")[1]);
        String userRole = claims.get("role", String.class);
        userUpdateDto.setId(Long.valueOf(claims.get("id",Integer.class)));
        if(userRole.equals("ROLE_ADMIN"))
        {
            UserUpdateDto adminUpdateObject = userUpdateDto;
            Admin updatedAdmin = new Admin(adminUpdateObject.getUsername(), adminUpdateObject.getPassword(),
                    adminUpdateObject.getEmail(), adminUpdateObject.getDateOfBirth()
                    , adminUpdateObject.getName(), adminUpdateObject.getSurname());
            return new ResponseEntity(userRepository.save(updatedAdmin), HttpStatus.ACCEPTED);
        }
        else if(userRole.equals("ROLE_CLIENT") )
        {
            UserUpdateDto clientUpdateObject = userUpdateDto;
            Optional<User> updatedClient = userRepository.findById(userUpdateDto.getId());
            Client c = (Client) updatedClient.get();
            c.setPassword(userUpdateDto.getPassword());
            c.setUsername((userUpdateDto.getUsername()));
            // OVO RADI SAMO DORADI KASNIJE


            return new ResponseEntity(userRepository.save(c), HttpStatus.ACCEPTED);
        }
        else if(userRole.equals("ROLE_MANAGER"))
        {
            UserUpdateDto managerUpdateObject = userUpdateDto;
            Manager updatedManager = new Manager(managerUpdateObject.getUsername(), managerUpdateObject.getPassword(),
                    managerUpdateObject.getEmail(), managerUpdateObject.getDateOfBirth()
                    , managerUpdateObject.getName(), managerUpdateObject.getSurname(),
                    managerUpdateObject.getHireDate(),managerUpdateObject.getCompanyName());
          return new ResponseEntity(userRepository.save(updatedManager), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity addRank(AddRankDto addRankDto) {
        Optional<Rank> rank = rankRepository.getByName(addRankDto.getName());

        if(rank.isPresent())
        {
            Rank update = rank.get();
            update.setThreshold(addRankDto.getThreshold());
            update.setDiscount(addRankDto.getDiscount());
           return new ResponseEntity (rankRepository.save(update),HttpStatus.OK);
        }
        else
        {
            Rank newRank = new Rank(addRankDto.getName(), addRankDto.getThreshold(), addRankDto.getDiscount());
            return  new ResponseEntity(rankRepository.save(newRank),HttpStatus.CREATED);
        }

    }


}
