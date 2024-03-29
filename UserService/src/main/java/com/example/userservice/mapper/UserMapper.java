package com.example.userservice.mapper;

import com.example.userservice.domain.userTypes.Client;
import com.example.userservice.domain.userTypes.Manager;
import com.example.userservice.dto.ClientCreateDto;
import com.example.userservice.dto.ManagerCreateDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public Client CreateClientDtoToClient(ClientCreateDto clientCreateDto)
    {
        return new Client(clientCreateDto.getUsername(), clientCreateDto.getPassword(), clientCreateDto.getEmail(), clientCreateDto.getDateOfBirth(),
                clientCreateDto.getName(), clientCreateDto.getSurname(), clientCreateDto.getPassportNumber(), clientCreateDto.getDaysRented(), clientCreateDto.getRank());
    }

    public Manager CreateManagerDtoToManager(ManagerCreateDto managerCreateDto)
    {
        return new Manager(managerCreateDto.getUsername(), managerCreateDto.getPassword(), managerCreateDto.getEmail(), managerCreateDto.getDateOfBirth(), managerCreateDto.getName(),
                managerCreateDto.getSurname(), managerCreateDto.getHireDate(), managerCreateDto.getCompanyName());
    }


}
