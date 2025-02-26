package com.example.account_service.service;

import com.example.account_service.dto.AccountBalanceDetail;
import com.example.account_service.dto.BalanceResponse;
import com.example.account_service.entity.MasterAccount;
import com.example.account_service.entity.MasterUser;
import com.example.account_service.repository.MasterAccountRepository;
import com.example.account_service.repository.MasterUserRepository;
import com.example.account_service.util.UUIDConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BalanceService {

    private final MasterUserRepository userRepository;
    private final MasterAccountRepository accountRepository;

    /**
     * Menghitung total saldo dan mengumpulkan detail saldo untuk pengguna berdasarkan userId.
     *
     * @param userIdBytes ID pengguna dalam format byte[].
     * @return BalanceResponse yang berisi total saldo dan detail saldo per akun.
     */
    public BalanceResponse getBalanceResponse(byte[] userIdBytes) {
        Optional<MasterUser> userOpt = userRepository.findById(userIdBytes);
        if (userOpt.isEmpty()) {
            return new BalanceResponse(null, BigDecimal.ZERO, new ArrayList<>(), "User not found.");
        }

        List<MasterAccount> accounts = accountRepository.findByUser(userOpt.get());
        List<AccountBalanceDetail> details = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (MasterAccount account : accounts) {
            UUID accountId = UUIDConverter.bytesToUUID(account.getId());
            BigDecimal balance = account.getBalance();
            details.add(new AccountBalanceDetail(accountId.toString(), balance));
            total = total.add(balance);
        }

        UUID userId = UUIDConverter.bytesToUUID(userIdBytes);
        return new BalanceResponse(userId, total, details, "Success");
    }
}
