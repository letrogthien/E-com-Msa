package com.letrogthien.transaction.request;

import com.letrogthien.transaction.common.Currency;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class OrderRequest {
    private UUID buyerId;
    private UUID sellerId;
    private Currency currency;
    private BigDecimal shippingCost;
    private BigDecimal discountAmount;
    private List<OrderItemRequest> orderItems;
}
