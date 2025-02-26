package com.example.account_service.repository;

import com.example.account_service.entity.MasterUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MasterUserRepository extends JpaRepository<MasterUser, byte[]> {
    boolean existsById(byte[] id);
    Optional<MasterUser> findById(byte[] id);
}