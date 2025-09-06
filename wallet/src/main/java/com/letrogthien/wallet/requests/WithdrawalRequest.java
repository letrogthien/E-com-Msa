package com.letrogthien.wallet.requests;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class WithdrawalRequest {
    private UUID walletId;
    private BigDecimal amount;
    private String recipientBankName;
    private String recipientBankAccount;
    private String recipientAccountName;
}
