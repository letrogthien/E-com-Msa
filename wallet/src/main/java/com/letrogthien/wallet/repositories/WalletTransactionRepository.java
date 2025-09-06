package com.letrogthien.wallet.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.letrogthien.wallet.entities.WalletTransaction;


@Repository
public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, UUID> {
    
}
