package com.example.userservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BooleanResponse {
    private Boolean success;

    public BooleanResponse(Boolean success) {
        this.success = success;
    }
}
