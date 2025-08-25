package com.letrogthien.wallet.requests;

import lombok.Data;

import java.util.UUID;

@Data
public class WalletRequest {
    private UUID userId;
    private String currency;
}
