package com.example.account_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "MASTER_ACCOUNT", schema = "BO_SERVICE_DATA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MasterAccount {
    @Id
    @Column(name = "ID", length = 16, updatable = false, nullable = false)
    private byte[] id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private MasterUser user;

    @Column(name = "BALANCE", nullable = false)
    private BigDecimal balance;
}
