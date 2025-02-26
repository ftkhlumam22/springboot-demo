package com.example.account_service.controller;

import com.example.account_service.dto.BalanceResponse;
import com.example.account_service.service.BalanceService;
import com.example.account_service.util.UUIDConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AccountController {

    private final BalanceService accountService;

    @GetMapping("/users/{userId}/balance")
    public ResponseEntity<BalanceResponse> getUserTotalBalance(@PathVariable String userId) {
        // Konversi UUID ke byte[]
        UUID uuid;
        try {
            uuid = UUID.fromString(userId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                    new BalanceResponse(null, BigDecimal.ZERO, null, "Invalid UUID format.")
            );
        }

        byte[] userIdBytes = UUIDConverter.uuidToBytes(uuid);

        BalanceResponse response = accountService.getBalanceResponse(userIdBytes);

        return ResponseEntity.ok(response);
    }
}
