package com.letrogthien.wallet.services;

import java.util.UUID;

import com.letrogthien.wallet.dtos.WalletDto;
import com.letrogthien.wallet.requests.WalletRequest;
import com.letrogthien.wallet.responses.ApiResponse;

public interface WalletService {
    /**
     * Creates a new wallet for the specified user.
     *
     * @param userId the unique identifier of the user for whom the wallet is being
     *               created
     * @return ApiResponse containing the created WalletDto
     */
    ApiResponse<WalletDto> createWallet(UUID userId);

    /**
     * Retrieves the wallet information for the specified user.
     *
     * @param userId the unique identifier of the user whose wallet is to be
     *               retrieved
     * @return ApiResponse containing the WalletDto of the specified user
     */
    ApiResponse<WalletDto> getWallet(UUID userId);

    /**
     * Updates the wallet information for the specified user with new details.
     *
     * @param userId        the unique identifier of the user whose wallet is to be
     *                      updated
     * @param walletRequest the request object containing updated wallet details
     * @return ApiResponse containing the updated WalletDto
     */
    ApiResponse<WalletDto> updateWallet(UUID userId, WalletRequest walletRequest);

    

}
