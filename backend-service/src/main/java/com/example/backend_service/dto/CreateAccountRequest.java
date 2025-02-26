package com.example.backend_service.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CreateAccountRequest {
    private UUID userId;
    private BigDecimal balance;
}
