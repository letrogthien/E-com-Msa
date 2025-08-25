package com.letrogthien.wallet.services.impl;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class PayJwtService {

    @Value("${payment.jwt.secret}")
    private String secret;

    private static final long EXPIRATION = 600000;

    public String generatedJwtToken(UUID userId, UUID walletId) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("wallet_id", walletId);
        extraClaims.put("user_id", userId);
        extraClaims.put("jti", UUID.randomUUID().toString());

        SecretKey key = this.getSecretKey();
        long expiration = this.getExpiration();
        return Jwts.builder()
                .setClaims(extraClaims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

    }

    public boolean isTokenValid(String token) {
        try {
            SecretKey key = this.getSecretKey();
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception var4) {
            return false;
        }
    }

    private SecretKey getSecretKey() {

        byte[] keyBytes = Base64.getDecoder().decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private long getExpiration() {
        return EXPIRATION;
    }
}
