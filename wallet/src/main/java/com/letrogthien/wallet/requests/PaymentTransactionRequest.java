package com.letrogthien.wallet.requests;

import com.letrogthien.wallet.common.Currency;
import com.letrogthien.wallet.common.PaymentMethod;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class PaymentTransactionRequest {
    private UUID orderId;
    private UUID userId;
    private BigDecimal amount;
    private Currency currency;
    private PaymentMethod paymentMethod;
    private String gatewayTransactionId;
    private String errorMessage;
    private String ipAddress;
    private String userAgent;
}
