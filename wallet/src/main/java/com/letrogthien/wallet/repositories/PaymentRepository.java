package com.letrogthien.wallet.repositories;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.letrogthien.wallet.entities.PaymentTransaction;

public interface PaymentRepository extends JpaRepository<PaymentTransaction, UUID> {

    Page<PaymentTransaction> findAllByUserId(UUID userId, Pageable pageable);

    
}