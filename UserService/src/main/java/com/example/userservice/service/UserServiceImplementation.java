package com.example.userservice.service;

import com.example.userservice.client.notificationservice.dto.SendNotificationDto;
import com.example.userservice.domain.Rank;
import com.example.userservice.domain.User;
import com.example.userservice.domain.userTypes.Admin;
import com.example.userservice.domain.userTypes.Client;
import com.example.userservice.domain.userTypes.Manager;
import com.example.userservice.dto.*;
import com.example.userservice.dto.userUpdate.UserUpdateDto;
import com.example.userservice.exception.NotFoundException;
import com.example.userservice.listener.MessageHelper;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.repository.RankRepository;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.security.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.*;

@Service
@Transactional
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final RankRepository rankRepository;
    private final UserMapper userMapper;
    private MessageHelper messageHelper;
    private RestTemplate notificationServiceRestTemplate;
    private String sendNotificationDestination;
    private TokenService tokenService;
    private JmsTemplate jmsTemplate;
    private HashMap<String,ClientCreateDto> pendingRegistrations;
    private String serverBaseUrl = "http://localhost:8081/api/user/";


    public UserServiceImplementation(UserRepository userRepository, RankRepository rankRepository, UserMapper userMapper,
                                     TokenService tokenService, RestTemplate notificationServiceRestTemplate,
                                     @Value("${destination.sendNotification}") String sendNotificationDestination,
                                     JmsTemplate jmsTemplate, MessageHelper messageHelper
                                     ) {
        this.userRepository = userRepository;
        this.rankRepository = rankRepository;
        this.userMapper = userMapper;
        this.tokenService = tokenService;
        this.messageHelper = messageHelper;
        this.pendingRegistrations = new HashMap<>();
        this.notificationServiceRestTemplate = notificationServiceRestTemplate;
        this.sendNotificationDestination = sendNotificationDestination;
        this.jmsTemplate = jmsTemplate;
        User a = userRepository.getAllByEmail("m@gmail.com");
        if(a == null)
        userRepository.save(new Admin("mare","123","m@gmail.com",Date.valueOf("2001-2-22"),"marko", "lekic"));
    }


    @Override
    public ResponseEntity addClient(ClientCreateDto clientCreateDto) {

        clientCreateDto.setRank(rankRepository.getByName("rank0").get());
        // Generate a random UUID
        UUID uuid = UUID.randomUUID();
        String uniqueKey = uuid.toString();
        pendingRegistrations.put(uniqueKey,clientCreateDto);
        // send email to notification service
        String params ="";
        params+=serverBaseUrl+"verify/"+uniqueKey;
        jmsTemplate.convertAndSend(sendNotificationDestination, messageHelper.createTextMessage(
                new SendNotificationDto(clientCreateDto.getEmail(), params,"activation")
        ));

        return new ResponseEntity("Account conformation email has been sent",HttpStatus.OK);
        }

    @Override
    public ResponseEntity verifyClient(String clientToken) {
        try {
            if(!(pendingRegistrations.containsKey(clientToken)))
                return new ResponseEntity("Please send the registration request",HttpStatus.NOT_FOUND);
            Client client = userMapper.CreateClientDtoToClient(pendingRegistrations.get(clientToken));
            userRepository.save(client);
            ClientCreateDto cDto = pendingRegistrations.get(clientToken);
            pendingRegistrations.remove(clientToken);
            return new ResponseEntity<>("You have verified you account email-address "+ cDto.getEmail(), HttpStatus.CREATED);
        }catch (Exception e)
        {
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void test() {
        JmsTemplate s = jmsTemplate;
        int a = 4;
    }

    @Override
    public ResponseEntity<ManagerCreateDto> addManager(ManagerCreateDto managerCreateDto) {
        User check = userRepository.getAllByEmail(managerCreateDto.getEmail());
        if(check != null)
        {
            new ResponseEntity<>("err", HttpStatus.BAD_REQUEST);
        }
        managerCreateDto.setPassword(managerCreateDto.getPassword());
        userRepository.save(userMapper.CreateManagerDtoToManager(managerCreateDto));
        return new ResponseEntity<>(managerCreateDto, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity restrictUser(String email) {
        userRepository.setUserRestrictedByEmail(email);
        return new ResponseEntity(HttpStatus.ACCEPTED);

    }

    @Override
    public ResponseEntity<TokenResponseDto> login(TokenRequestDto tokenRequestDto) {
        //Try to find active user for specified credentials
        User user = userRepository
                .getUserByEmailAndPassword(tokenRequestDto.getEmail(), tokenRequestDto.getPassword())
                .orElseThrow(() -> new NotFoundException(String
                        .format("User with username: %s and password: %s not found.", tokenRequestDto.getEmail(),
                                tokenRequestDto.getPassword())));
        if(user.getRestricted())
            return new ResponseEntity<>(new TokenResponseDto("err"), HttpStatus.BAD_REQUEST);
        //Create token payload
        Claims claims = Jwts.claims();
        claims.put("id", user.getId());
        String userRole = "ROLE_" + user.getClass().getSimpleName().toUpperCase(Locale.ROOT);
        claims.put("role", userRole);
        //Generate token
        return new ResponseEntity<>(new TokenResponseDto(tokenService.generate(claims)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity updateUser(String authToken, UserUpdateDto userUpdateDto) {
        Claims claims = tokenService.parseToken(authToken.split(" ")[1]);
        String userRole = claims.get("role", String.class);
        userUpdateDto.setId(Long.valueOf(claims.get("id",Integer.class)));
        if(!(userRole.equals("ROLE_ADMIN") || userRole.equals("ROLE_CLIENT")|| userRole.equals("ROLE_MANAGER")))
            return new ResponseEntity("Error, you do not have proper permissions",HttpStatus.UNAUTHORIZED);

        Optional<User> updatedClient = userRepository.findById(userUpdateDto.getId());


        if(userRole.equals("ROLE_ADMIN"))
        {
            Admin c = (Admin) updatedClient.get();
            c.setPassword(userUpdateDto.getPassword());
            c.setUsername((userUpdateDto.getUsername()));
            c.setEmail(userUpdateDto.getEmail());
            c.setDateOfBirth(userUpdateDto.getDateOfBirth());
            c.setSurname(userUpdateDto.getSurname());
            return new ResponseEntity(userRepository.save(c), HttpStatus.ACCEPTED);
        }
        else if(userRole.equals("ROLE_CLIENT") )
        {
            Client c = (Client) updatedClient.get();
            c.setPassword(userUpdateDto.getPassword());
            c.setUsername((userUpdateDto.getUsername()));
            c.setEmail(userUpdateDto.getEmail());
            c.setDateOfBirth(userUpdateDto.getDateOfBirth());
            c.setSurname(userUpdateDto.getSurname());

            c.setPassportNumber(userUpdateDto.getPassportNumber());

            return new ResponseEntity(userRepository.save(c), HttpStatus.ACCEPTED);
        }
        else if(userRole.equals("ROLE_MANAGER"))
        {
           Manager c = (Manager) updatedClient.get();
            c.setPassword(userUpdateDto.getPassword());
            c.setUsername((userUpdateDto.getUsername()));
            c.setEmail(userUpdateDto.getEmail());
            c.setDateOfBirth(userUpdateDto.getDateOfBirth());
            c.setSurname(userUpdateDto.getSurname());

            c.setCompanyName(userUpdateDto.getCompanyName());

          return new ResponseEntity(userRepository.save(c), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity("error",HttpStatus.BAD_REQUEST);
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
