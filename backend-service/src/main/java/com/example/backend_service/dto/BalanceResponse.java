package com.example.backend_service.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class BalanceResponse {
    private UUID userId;
    private BigDecimal totalBalance;
    private List<DetailBalance> detail_balance;

    @Data
    public static class DetailBalance {
        private String accountId;
        private BigDecimal balance;
    }
}
