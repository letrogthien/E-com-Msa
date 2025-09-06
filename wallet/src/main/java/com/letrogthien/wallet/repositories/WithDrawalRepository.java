package com.letrogthien.wallet.repositories;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.letrogthien.wallet.entities.WithdrawalRequestE;

public interface WithDrawalRepository extends JpaRepository<WithdrawalRequestE, UUID>{

    Page<WithdrawalRequestE> findAllByWalletId(UUID walletId, Pageable pageable);
    
}
