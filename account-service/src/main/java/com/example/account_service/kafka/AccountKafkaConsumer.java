package com.example.account_service.kafka;

import com.example.account_service.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AccountKafkaConsumer {

    private final AccountService accountService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "account_requests", groupId = "account_service")
    public void listen(String message) {
        try {
            AccountRequestMessage accountRequest = objectMapper.readValue(message, AccountRequestMessage.class);
            processMessage(accountRequest);
        } catch (Exception e) {
            System.err.println("Failed to parse message: " + message);
            e.printStackTrace();
        }
    }

    private void processMessage(AccountRequestMessage message) {
        String action = message.getAction();
        switch (action) {
            case "CREATE_USER":
                accountService.createUser(message.getUserId(), message.getFullName());
                System.out.println("User created with ID: " + message.getUserId());
                break;
            case "CREATE_ACCOUNT":
                accountService.createAccount(
                        message.getAccountId(),
                        message.getUserId(),
                        message.getBalance()
                );
                System.out.println("Account created with ID: " + message.getAccountId());
                break;
            default:
                // Handle unknown action
                System.out.println("Unknown action: " + action);
                break;
        }
    }
}
