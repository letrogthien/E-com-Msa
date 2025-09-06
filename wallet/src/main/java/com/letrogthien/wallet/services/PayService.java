package com.letrogthien.wallet.services;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import com.letrogthien.wallet.dtos.VnpayReturnDto;
import com.letrogthien.wallet.entities.DepositRequestE;
import com.letrogthien.wallet.requests.TransferRequest;
import com.letrogthien.wallet.responses.ApiResponse;

public interface PayService {
        ApiResponse<String> payUrlDeposit(String ip, int total, String detail, UUID userId)
                        throws InvalidKeyException, NoSuchAlgorithmException;

        public DepositRequestE handleReturnVnpDeposit(
                        VnpayReturnDto vnpayReturnDto);

        public String testInp(String amount,
                        String bankCode,
                        String bankTranNo,
                        String cardType,
                        String orderInfo,
                        String payDate,
                        String responseCode,
                        String tmnCode,
                        String transactionNo,
                        String txnRef,
                        String secureHash);


        ApiResponse<?> transferMonney(UUID userId, String userEmail, TransferRequest transferRequest);



        ApiResponse<String> getPaymentJwtToken(UUID userId,String password);

        ApiResponse<String> payOtp(String email,UUID userId);

}
