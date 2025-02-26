package com.example.account_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class AccountBalanceDetail {
    private String accountId;
    private BigDecimal balance;
}
