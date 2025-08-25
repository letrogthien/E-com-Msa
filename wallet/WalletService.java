package com.msa.wallet.service;

import com.msa.wallet.dto.*;
import com.msa.wallet.entity.Wallet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Wallet Service Interface
 * Provides wallet management operations including balance management,
 * status updates, and wallet information retrieval
 */
public interface WalletService {

    /**
     * Create a new wallet for a user
     * @param userId User ID to create wallet for
     * @param currency Currency for the wallet
     * @return Created wallet DTO
     */
    WalletDto createWallet(UUID userId, String currency);

    /**
     * Get wallet by user ID
     * @param userId User ID
     * @return Wallet DTO
     */
    WalletDto getWalletByUserId(UUID userId);

    /**
     * Get wallet by wallet ID
     * @param walletId Wallet ID
     * @return Wallet DTO
     */
    WalletDto getWalletById(UUID walletId);

    /**
     * Update wallet status
     * @param walletId Wallet ID
     * @param status New status (ACTIVE, INACTIVE, SUSPENDED, BLOCKED)
     * @return Updated wallet DTO
     */
    WalletDto updateWalletStatus(UUID walletId, String status);

    /**
     * Get wallet balance
     * @param walletId Wallet ID
     * @return Current balance
     */
    BigDecimal getBalance(UUID walletId);

    /**
     * Check if wallet has sufficient balance
     * @param walletId Wallet ID
     * @param amount Amount to check
     * @return true if sufficient balance exists
     */
    boolean hasSufficientBalance(UUID walletId, BigDecimal amount);

    /**
     * Get all wallets with pagination
     * @param pageable Pagination information
     * @return Page of wallet DTOs
     */
    Page<WalletDto> getAllWallets(Pageable pageable);

    /**
     * Get wallets by status
     * @param status Wallet status
     * @param pageable Pagination information
     * @return Page of wallet DTOs
     */
    Page<WalletDto> getWalletsByStatus(String status, Pageable pageable);

    /**
     * Search wallets by user ID pattern
     * @param userIdPattern User ID pattern
     * @param pageable Pagination information
     * @return Page of wallet DTOs
     */
    Page<WalletDto> searchWalletsByUserId(String userIdPattern, Pageable pageable);

    /**
     * Deactivate wallet
     * @param walletId Wallet ID
     * @return Updated wallet DTO
     */
    WalletDto deactivateWallet(UUID walletId);

    /**
     * Activate wallet
     * @param walletId Wallet ID
     * @return Updated wallet DTO
     */
    WalletDto activateWallet(UUID walletId);

    /**
     * Block wallet
     * @param walletId Wallet ID
     * @param reason Reason for blocking
     * @return Updated wallet DTO
     */
    WalletDto blockWallet(UUID walletId, String reason);

    /**
     * Unblock wallet
     * @param walletId Wallet ID
     * @return Updated wallet DTO
     */
    WalletDto unblockWallet(UUID walletId);
}