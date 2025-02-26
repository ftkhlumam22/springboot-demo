package com.example.backend_service.kafka;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class AccountRequestMessage {
    private String action;
    private UUID userId;
    private String fullName;
    private UUID accountId;
    private BigDecimal balance;
}
