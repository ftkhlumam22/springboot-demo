package com.example.backend_service.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateUserRequest {
    private String fullName;
}
