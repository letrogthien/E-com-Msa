package com.letrogthien.wallet.requests;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class WalletTransactionRequest {
    private UUID walletId;
    private String transactionType;
    private BigDecimal amount;
    private BigDecimal feeAmount;
    private BigDecimal finalAmount;
    private BigDecimal currentBalanceBefore;
    private BigDecimal currentBalanceAfter;
    private UUID referenceId;
    private String referenceType;
    private String description;
    private String externalTransactionId;
    private String idempotencyKey;
}
