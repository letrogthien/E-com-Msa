package com.letrogthien.wallet.repositories;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.letrogthien.wallet.entities.DepositRequestE;

@Repository
public interface DepositRepository extends JpaRepository<DepositRequestE, UUID>{

    Page<DepositRequestE> findAllByWalletId(UUID walletId, Pageable pageable);

    
} 
