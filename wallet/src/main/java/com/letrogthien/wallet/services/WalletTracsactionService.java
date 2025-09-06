package com.letrogthien.wallet.services;

import java.util.UUID;

import org.springframework.data.domain.Page;

import com.letrogthien.wallet.dtos.WalletTransactionDto;
import com.letrogthien.wallet.responses.ApiResponse;

public interface WalletTracsactionService {
    ApiResponse<Page<WalletTransactionDto>> getAllOwnerTransaction(UUID userId);

    ApiResponse<WalletTransactionDto> getTransaction(UUID transactionId, UUID userId);

    
} 
