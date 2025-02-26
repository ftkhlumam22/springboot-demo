package com.example.account_service.service;

import com.example.account_service.entity.MasterAccount;
import com.example.account_service.entity.MasterUser;
import com.example.account_service.repository.MasterAccountRepository;
import com.example.account_service.repository.MasterUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class AccountService {

    private final MasterUserRepository masterUserRepository;
    private final MasterAccountRepository masterAccountRepository;
    private final JdbcTemplate jdbcTemplate;

    /**
     * Memeriksa apakah pengguna ada berdasarkan userId.
     *
     * @param userId UUID pengguna
     * @return true jika ada, false jika tidak
     */
    public boolean userExists(UUID userId) {
        return masterUserRepository.existsById(uuidToBytes(userId));
    }

    /**
     * Menghitung total saldo semua akun untuk userId tertentu.
     *
     * @param userId UUID pengguna
     * @return Total saldo sebagai BigDecimal
     */
    public BigDecimal calculateTotalBalance(UUID userId) {
        String sql = "SELECT SUM(balance) FROM BO_SERVICE_DATA.MASTER_ACCOUNT WHERE user_id = ?";
        BigDecimal total = jdbcTemplate.queryForObject(sql, new Object[]{uuidToBytes(userId)}, BigDecimal.class);
        return total != null ? total : BigDecimal.ZERO;
    }

    /**
     * Membuat pengguna baru.
     *
     * @param userId UUID pengguna baru
     * @param fullName Nama lengkap pengguna
     */
    @Transactional
    public void createUser(UUID userId, String fullName) {
        byte[] userIdBytes = uuidToBytes(userId);
        MasterUser user = new MasterUser(userIdBytes, fullName);
        masterUserRepository.save(user);
    }

    /**
     * Membuat akun baru untuk pengguna.
     *
     * @param accountId UUID akun
     * @param userId UUID pengguna
     * @param balance Saldo awal
     */
    @Transactional
    public void createAccount(UUID accountId, UUID userId, BigDecimal balance) {
        byte[] userIdBytes = uuidToBytes(userId);
        MasterUser user = masterUserRepository.findById(userIdBytes)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        byte[] accountIdBytes = uuidToBytes(accountId);
        MasterAccount account = new MasterAccount(accountIdBytes, user, balance);
        masterAccountRepository.save(account);
    }

    /**
     * Mengonversi UUID ke byte array (RAW(16) di Oracle)
     *
     * @param uuid UUID yang akan dikonversi
     * @return byte[] representasi UUID
     */
    private byte[] uuidToBytes(UUID uuid) {
        long mostSigBits = uuid.getMostSignificantBits();
        long leastSigBits = uuid.getLeastSignificantBits();
        byte[] bytes = new byte[16];
        for (int i = 0; i < 8; i++) {
            bytes[i] = (byte) (mostSigBits >>> 8 * (7 - i));
            bytes[8 + i] = (byte) (leastSigBits >>> 8 * (7 - i));
        }
        return bytes;
    }

    /**
     * Mengonversi byte array (RAW(16) di Oracle) ke UUID
     *
     * @param bytes byte array representasi UUID
     * @return UUID
     */
    private UUID bytesToUUID(byte[] bytes) {
        if (bytes.length != 16) {
            throw new IllegalArgumentException("Byte array harus memiliki panjang 16");
        }
        long mostSigBits = 0;
        long leastSigBits = 0;
        for (int i = 0; i < 8; i++) {
            mostSigBits = (mostSigBits << 8) | (bytes[i] & 0xff);
            leastSigBits = (leastSigBits << 8) | (bytes[8 + i] & 0xff);
        }
        return new UUID(mostSigBits, leastSigBits);
    }
}