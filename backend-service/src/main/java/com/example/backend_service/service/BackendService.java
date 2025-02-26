package com.example.backend_service.service;

import com.example.backend_service.dto.BalanceResponse;
import com.example.backend_service.kafka.AccountRequestMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BackendService {
    private final KafkaTemplate<String, AccountRequestMessage> kafkaTemplate;

    @Value("account_requests") // Gunakan properti yang tepat
    private String accountRequestsTopic;

    @Value("http://localhost:8081") // Gunakan properti yang tepat
    private String accountServiceUrl;

    /**
     * Mengirimkan permintaan pembuatan pengguna ke Kafka.
     */
    public void sendCreateUserRequest(UUID userId, String fullName) {
        AccountRequestMessage message = new AccountRequestMessage();
        message.setAction("CREATE_USER");
        message.setUserId(userId);
        message.setFullName(fullName);
        kafkaTemplate.send(accountRequestsTopic, message);
    }

    /**
     * Mengirimkan permintaan pembuatan akun ke Kafka.
     */
    public void sendCreateAccountRequest(UUID accountId, UUID userId, BigDecimal balance) {
        AccountRequestMessage message = new AccountRequestMessage();
        message.setAction("CREATE_ACCOUNT");
        message.setAccountId(accountId);
        message.setUserId(userId);
        message.setBalance(balance);
        kafkaTemplate.send(accountRequestsTopic, message);
    }

    public BalanceResponse getTotalBalance(UUID userId) {
        String url = accountServiceUrl + "/api/users/" + userId + "/balance";
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<BalanceResponse> response = restTemplate.getForEntity(url, BalanceResponse.class);
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return response.getBody();
            }
        } catch (Exception e) {
            // Tangani exception sesuai kebutuhan, misalnya log error
            System.err.println("Error fetching balance: " + e.getMessage());
        }

        // Return default response jika terjadi error
        BalanceResponse defaultResponse = new BalanceResponse();
        defaultResponse.setUserId(userId);
        defaultResponse.setTotalBalance(BigDecimal.ZERO);
        defaultResponse.setDetail_balance(null);
        return defaultResponse;
    }
}