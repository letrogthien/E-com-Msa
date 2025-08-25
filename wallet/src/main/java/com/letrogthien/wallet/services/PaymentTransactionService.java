package com.letrogthien.wallet.services;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.letrogthien.wallet.dtos.PaymentTransactionDto;
import com.letrogthien.wallet.responses.ApiResponse;

public interface PaymentTransactionService {
    ApiResponse<Page<PaymentTransactionDto>> getByUserId(UUID userId, Pageable pageable);

    ApiResponse<PaymentTransactionDto> getByTransactionId(UUID transactionId, UUID userId);
    
} 
