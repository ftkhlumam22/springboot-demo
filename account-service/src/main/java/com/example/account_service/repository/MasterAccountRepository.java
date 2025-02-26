package com.example.account_service.repository;

import com.example.account_service.entity.MasterAccount;
import com.example.account_service.entity.MasterUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MasterAccountRepository extends JpaRepository<MasterAccount, byte[]> {
    List<MasterAccount> findByUser(MasterUser user);
}
