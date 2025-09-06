package com.letrogthien.wallet.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.letrogthien.wallet.entities.Wallet;

public interface WalletRepository  extends JpaRepository<Wallet, UUID> {

    Optional<Wallet> findByUserId(UUID userId);
    
}
