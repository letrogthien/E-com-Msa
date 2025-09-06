package com.letrogthien.wallet.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.letrogthien.wallet.entities.WalletSecure;


public interface WalletSecureRepository extends JpaRepository<WalletSecure, UUID> {
    Optional<WalletSecure> findByWalletId(UUID walletId);
    
} 
    

