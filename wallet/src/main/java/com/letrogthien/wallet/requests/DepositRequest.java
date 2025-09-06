package com.letrogthien.wallet.requests;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class DepositRequest {
    private UUID walletId;
    private BigDecimal amount;
    private String paymentMethod;
    private String bankCode;
    private String vnpayOrderInfo;
}
