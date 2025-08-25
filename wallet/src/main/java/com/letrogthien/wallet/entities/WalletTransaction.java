package com.letrogthien.wallet.entities;

import com.letrogthien.wallet.common.Status;
import com.letrogthien.wallet.common.TransactionType;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Builder
@Table(name = "wallet_transactions")
public class WalletTransaction {
    @Id
    @Column(columnDefinition = "BINARY(16)")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "wallet_id", columnDefinition = "BINARY(16)", nullable = false)
    private UUID walletId;

    @Column(name = "transaction_type", length = 50, nullable = false)
    private String transactionType;

    @Column(precision = 18, scale = 4, nullable = false)
    private BigDecimal amount;

    @Column(name = "fee_amount", precision = 18, scale = 4)
    private BigDecimal feeAmount;

    @Column(name = "final_amount", precision = 18, scale = 4, nullable = false)
    private BigDecimal finalAmount;

    @Column(name = "current_balance_before", precision = 18, scale = 4, nullable = false)
    private BigDecimal currentBalanceBefore;

    @Column(name = "current_balance_after", precision = 18, scale = 4, nullable = false)
    private BigDecimal currentBalanceAfter;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Status status;

    @Column(name = "reference_id", columnDefinition = "BINARY(16)")
    private UUID referenceId;

    @Column(name = "reference_type", length = 50)
    @Enumerated(EnumType.STRING)
    private TransactionType referenceType;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "external_transaction_id", length = 255)
    private String externalTransactionId;

    @Column(name = "idempotency_key", length = 255, unique = true)
    private String idempotencyKey;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();        
    }
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
