package com.example.account_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class BalanceResponse {
    private UUID userId;
    private BigDecimal totalBalance;
    private List<AccountBalanceDetail> detail_balance;
    private String message;
}