
package com.letrogthien.transaction.controllers;

import com.letrogthien.transaction.common.Status;
import com.letrogthien.transaction.dto.OrderDto;
import com.letrogthien.transaction.dto.OrderItemDto;
import com.letrogthien.transaction.request.OrderRequest;
import com.letrogthien.transaction.responses.ApiResponse;
import com.letrogthien.transaction.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ApiResponse<OrderDto> createOrder(@RequestBody OrderRequest request) {
        return orderService.createOrder(request);
    }

    @GetMapping("/{orderId}")
    public ApiResponse<OrderDto> getOrderById(@PathVariable UUID orderId) {
        return orderService.getOrderById(orderId);
    }

    @GetMapping("/buyer/{buyerId}")
    public ApiResponse<List<OrderDto>> getMyOrders(@PathVariable UUID buyerId) {
        return orderService.getMyOrders(buyerId);
    }

    @GetMapping("/seller/{sellerId}")
    public ApiResponse<List<OrderDto>> getMySellerOrders(@PathVariable UUID sellerId) {
        return orderService.getMySellerOrders(sellerId);
    }

    @PutMapping("/{orderId}/status")
    public ApiResponse<OrderDto> updateOrderStatus(
            @PathVariable UUID orderId,
            @RequestParam Status newStatus) {
        return orderService.updateOrderStatus(orderId, newStatus);
    }

    @PutMapping("/{orderId}/payment")
    public ApiResponse<OrderDto> updatePaymentStatus(
            @PathVariable UUID orderId,
            @RequestParam Status paymentStatus) {
        return orderService.updatePaymentStatus(orderId, paymentStatus);
    }

    @GetMapping("/{orderId}/items")
    public ApiResponse<List<OrderItemDto>> getOrderItems(@PathVariable UUID orderId) {
        return orderService.getOrderItems(orderId);
    }
}
