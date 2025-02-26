package com.example.backend_service.controller;

import com.example.backend_service.dto.BalanceResponse;
import com.example.backend_service.dto.CreateAccountRequest;
import com.example.backend_service.dto.CreateUserRequest;
import com.example.backend_service.service.BackendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BackendController {

    private final BackendService backendService;

    /**
     * Endpoint untuk membuat pengguna baru.
     * Menghasilkan UUID secara otomatis untuk userId.
     * Mengembalikan respons JSON dengan pesan dan userId.
     */
    @PostMapping("/users")
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody CreateUserRequest request) {
        // Generate UUID secara otomatis
        UUID userId = UUID.randomUUID();

        // Kirim permintaan pembuatan pengguna ke Kafka
        backendService.sendCreateUserRequest(userId, request.getFullName());

        // Siapkan respons
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User creation request sent.");
        response.put("userId", userId);

        // Kembalikan respons dengan status 200 OK
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint untuk membuat akun baru.
     * Menghasilkan UUID secara otomatis untuk accountId.
     * Mengembalikan respons JSON dengan pesan dan accountId.
     */
    @PostMapping("/accounts")
    public ResponseEntity<Map<String, Object>> createAccount(@RequestBody CreateAccountRequest request) {
        // Generate UUID secara otomatis
        UUID accountId = UUID.randomUUID();

        // Kirim permintaan pembuatan akun ke Kafka
        backendService.sendCreateAccountRequest(accountId, request.getUserId(), request.getBalance());

        // Siapkan respons
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Account creation request sent.");
        response.put("accountId", accountId);

        // Kembalikan respons dengan status 200 OK
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint untuk mendapatkan total saldo pengguna berdasarkan userId.
     * Mengembalikan respons JSON dengan userId dan totalBalance.
     */
    @GetMapping("/users/{userId}/balance")
    public ResponseEntity<BalanceResponse> getUserBalance(@PathVariable UUID userId) {
        BalanceResponse totalBalance = backendService.getTotalBalance(userId);
        return ResponseEntity.ok(totalBalance);
    }
}