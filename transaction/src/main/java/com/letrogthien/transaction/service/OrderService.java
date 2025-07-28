package com.letrogthien.transaction.service;

import com.letrogthien.transaction.common.Status;
import com.letrogthien.transaction.dto.OrderDto;
import com.letrogthien.transaction.dto.OrderItemDto;
import com.letrogthien.transaction.request.OrderRequest;
import com.letrogthien.transaction.responses.ApiResponse;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    ApiResponse<OrderDto> createOrder(OrderRequest request);
    ApiResponse<OrderDto> getOrderById(UUID orderId);
    ApiResponse<List<OrderDto>> getMyOrders(UUID buyerId);
    ApiResponse<List<OrderDto>> getMySellerOrders(UUID sellerId);
    
    ApiResponse<OrderDto> updateOrderStatus(UUID orderId, Status newStatus);
    ApiResponse<OrderDto> updatePaymentStatus(UUID orderId, Status paymentStatus);
    
    ApiResponse<List<OrderItemDto>> getOrderItems(UUID orderId);
}
