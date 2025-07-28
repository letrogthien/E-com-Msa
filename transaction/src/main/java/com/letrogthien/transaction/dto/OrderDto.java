package com.letrogthien.transaction.dto;

import com.letrogthien.transaction.common.Currency;
import com.letrogthien.transaction.common.Status;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class OrderDto {
    private UUID id;
    private String orderCode;
    private UUID buyerId;
    private UUID sellerId;
    private BigDecimal totalProductsAmount;
    private Currency currency;
    private BigDecimal shippingCost;
    private BigDecimal discountAmount;
    private BigDecimal finalAmount;
    private Status orderStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<OrderItemDto> orderItems;
}
