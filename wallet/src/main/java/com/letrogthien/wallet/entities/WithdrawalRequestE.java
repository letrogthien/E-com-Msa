package com.letrogthien.wallet.entities;

import com.letrogthien.wallet.common.Status;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "withdrawal_requests")
public class WithdrawalRequestE {
    @Id
    @Column(columnDefinition = "BINARY(16)")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "wallet_id", columnDefinition = "BINARY(16)", nullable = false)
    private UUID walletId;

    @Column(precision = 18, scale = 4, nullable = false)
    private BigDecimal amount;

    @Column(name = "fee_amount", precision = 18, scale = 4)
    private BigDecimal feeAmount = BigDecimal.ZERO;

    @Column(name = "net_amount", precision = 18, scale = 4, nullable = false)
    private BigDecimal netAmount;

    @Column(name = "recipient_bank_name", length = 100, nullable = false)
    private String recipientBankName;

    @Column(name = "recipient_bank_account", length = 100, nullable = false)
    private String recipientBankAccount;

    @Column(name = "recipient_account_name", length = 100, nullable = false)
    private String recipientAccountName;

    @Column(name = "vnpay_txn_ref", length = 255, unique = true)
    private String vnpayTxnRef;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Status status = Status.PENDING;

    @Column(name = "reviewer_id", columnDefinition = "BINARY(16)")
    private UUID reviewerId;

    @Column(name = "review_notes", columnDefinition = "TEXT")
    private String reviewNotes;

    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;

    @Column(name = "wallet_transaction_id", columnDefinition = "BINARY(16)")
    private UUID walletTransactionId;

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
        this.status = Status.PENDING;
    }
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
