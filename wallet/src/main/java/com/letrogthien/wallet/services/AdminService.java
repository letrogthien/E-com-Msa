package com.letrogthien.wallet.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.letrogthien.wallet.dtos.DepositRequestDto;
import com.letrogthien.wallet.dtos.PaymentTransactionDto;
import com.letrogthien.wallet.dtos.WalletDto;
import com.letrogthien.wallet.dtos.WithdrawalRequestDto;
import com.letrogthien.wallet.responses.ApiResponse;

public interface AdminService {
    /**
     * Deletes a wallet by its unique identifier.
     *
     * @param walletId The unique identifier of the wallet to be deleted.
     * @return An ApiResponse object containing the WalletDto if the deletion was
     *         successful,
     *         or an error message if the deletion failed.
     */
    ApiResponse<WalletDto> deleteWallet(UUID walletId);

    /**
     * Retrieves wallet information for a specific user.
     *
     * @param userId The unique identifier of the user whose wallet information is
     *               being requested.
     *               Must not be null.
     * @return ApiResponse containing the WalletDto object with the user's wallet
     *         information.
     *         The response may contain additional metadata based on the
     *         implementation.
     */
    ApiResponse<WalletDto> getWalletByUser(UUID userId);

    /**
     * Retrieves all wallets.
     *
     * @return ApiResponse containing a list of WalletDto objects representing all
     *         wallets.
     */
    ApiResponse<Page<WalletDto>> getAllWallet(Pageable pageable);

    /**
     * Activates a wallet with the specified ID.
     * 
     * @param walletId The unique identifier of the wallet to be activated
     * @return ApiResponse containing the WalletDto if activation is successful
     */
    ApiResponse<WalletDto> activeWallet(UUID walletId, Boolean isKyc);

    /**
     * Retrieves a paginated list of deposit requests associated with a specific
     * wallet.
     *
     * @param walletId the unique identifier of the wallet
     * @param pageable the pagination information
     * @return an ApiResponse containing a page of DepositRequestDto objects
     */
    ApiResponse<Page<DepositRequestDto>> getDepositRequestByWallet(UUID walletId, Pageable pageable);

    /**
     * Retrieves a paginated list of withdrawal requests associated with a specific
     * wallet.
     *
     * @param walletId the unique identifier of the wallet
     * @param pageable the pagination information
     * @return an ApiResponse containing a page of WithdrawalRequestDto objects
     */
    ApiResponse<Page<WithdrawalRequestDto>> getWithdrawRequestByWallet(UUID walletId, Pageable pageable);

    /**
     * Retrieves a paginated list of payment transactions associated with a specific
     * user's wallet.
     *
     * @param walletId the unique identifier of the wallet
     * @param pageable the pagination information
     * @return an ApiResponse containing a page of PaymentTransactionDto objects
     */
    ApiResponse<Page<PaymentTransactionDto>> getPaymentTransactionByUserId(UUID walletId, Pageable pageable);

    /**
     * Retrieves a specific payment transaction by its unique identifier.
     *
     * @param id the unique identifier of the payment transaction
     * @return an ApiResponse containing the PaymentTransactionDto object
     */
    ApiResponse<PaymentTransactionDto> getPaymentTransactionById(UUID id);

}
