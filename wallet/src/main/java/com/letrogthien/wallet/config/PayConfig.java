package com.letrogthien.wallet.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "vnpay")
@Getter
@Setter
public class PayConfig {
    private String vnpVersion;
    private String vnpCommand;
    private String vnpTmnCode;
    private String vnpHashSecret;
    private String vnpHashType;
    private String vnpReturnUrl;
    private String vnpBankCode;
    private String vnpPayUrl;

    public String generateTxnRef(UUID walletId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String timestamp = LocalDateTime.now().format(formatter);
        String random = UUID.randomUUID().toString().substring(0, 3).toUpperCase();
        return timestamp + random + "_" + walletId.toString();
    }

    public String getUUID(String txnRef) {
        return txnRef.split("_")[1];
    }
}