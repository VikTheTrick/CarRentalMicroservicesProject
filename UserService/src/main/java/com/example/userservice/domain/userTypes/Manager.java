package com.example.userservice.domain.userTypes;

import com.example.userservice.domain.User;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Date;

@Entity
public class Manager extends User {
    private Date hireDate;
    private String companyName;
}
