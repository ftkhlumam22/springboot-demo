package com.example.account_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MASTER_USER", schema = "BO_SERVICE_DATA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MasterUser {
    @Id
    @Column(name = "ID", length = 16, updatable = false, nullable = false)
    private byte[] id;

    @Column(name = "FULL_NAME", nullable = false)
    private String fullName;
}
