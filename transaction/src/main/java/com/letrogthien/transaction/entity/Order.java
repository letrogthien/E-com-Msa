package com.letrogthien.transaction.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.letrogthien.transaction.common.Currency;
import com.letrogthien.transaction.common.Status;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "order_code", unique = true, nullable = false)
    private String orderCode;

    @Column(name = "buyer_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID buyerId;

    @Column(name = "seller_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID sellerId;

    @Column(name = "total_products_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalProductsAmount;

    @Column(name = "currency", nullable = false, length = 5)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(name = "shipping_cost", nullable = false, precision = 10, scale = 2)
    private BigDecimal shippingCost;

    @Column(name = "discount_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal discountAmount ;

    @Column(name = "final_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal finalAmount;

    @Column(name = "order_status", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private Status orderStatus;

    @Column(name = "payment_method", length = 50)
    private String paymentMethod;

    @Column(name = "payment_ref_id", columnDefinition = "BINARY(16)")
    private UUID paymentRefId;

    @Column(name = "payment_gateway_txn_id")
    private String paymentGatewayTxnId;

    @Column(name = "payment_status_detail", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private Status paymentStatusDetail;

    @Column(name = "order_notes", columnDefinition = "TEXT")
    private String orderNotes;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate ;

    @Column(name = "last_updated_at", nullable = false)
    private LocalDateTime lastUpdatedAt ;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<TransactionEventLog> transactionEvents;

    @PrePersist
    protected void onCreate() {
        currency = Currency.VND;
        shippingCost = BigDecimal.ZERO;
        discountAmount = BigDecimal.ZERO;
        orderStatus = Status.PENDING;
        paymentStatusDetail = Status.PENDING;
        finalAmount = totalProductsAmount.add(shippingCost).subtract(discountAmount);

        orderDate = LocalDateTime.now();
        lastUpdatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdatedAt = LocalDateTime.now();
    }

}
