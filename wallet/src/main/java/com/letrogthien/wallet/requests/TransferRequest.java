package com.letrogthien.wallet.requests;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Data;

@Data
public class TransferRequest {
    private UUID from;
    private UUID to;
    private BigDecimal amount;
    private String description;
    private String jwt;
    private String otp;
}
