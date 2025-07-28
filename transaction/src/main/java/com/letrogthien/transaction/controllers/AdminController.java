package com.letrogthien.transaction.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.letrogthien.transaction.common.Status;
import com.letrogthien.transaction.dto.CartDto;
import com.letrogthien.transaction.dto.OrderDto;
import com.letrogthien.transaction.dto.TransactionEventLogDto;
import com.letrogthien.transaction.responses.ApiResponse;
import com.letrogthien.transaction.service.AdminService;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/admin")
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/carts")
    public ApiResponse<List<CartDto>> getAllCart() {
        return adminService.getAllCarts();
    }

    @DeleteMapping("/carts") 
    public ApiResponse<Void> forceDeleteCart(UUID cartId) {
        return adminService.forceDeleteCart(cartId);
    }

    @GetMapping("/orders")
    public ApiResponse<List<OrderDto>> getAllOrders() {
        return adminService.getAllOrders();
    }

    @GetMapping("/orders/status")
    public ApiResponse<List<OrderDto>> getOrdersByStatus(Status status) {
        return adminService.getOrdersByStatus(status);
    }

    @GetMapping("/orders/date-range")
    public ApiResponse<List<OrderDto>> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return adminService.getOrdersByDateRange(startDate, endDate);
    }

    @PutMapping("/orders/status")
    public ApiResponse<OrderDto> forceUpdateOrderStatus(UUID orderId, Status newStatus) {
        return adminService.forceUpdateOrderStatus(orderId, newStatus);
    }

    @GetMapping("/transaction-logs")
    public ApiResponse<List<TransactionEventLogDto>> getAllTransactionLogs() {
        return adminService.getAllTransactionLogs();
    }

    @GetMapping("/transaction-logs/date-range")
    public ApiResponse<List<TransactionEventLogDto>> getTransactionLogsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return adminService.getTransactionLogsByDateRange(startDate, endDate);
    }

    @GetMapping("/statistics")
    public ApiResponse<Map<String, Object>> getTransactionStatistics() {
        return adminService.getTransactionStatistics();
    }

    @GetMapping("/reports/daily")
    public ApiResponse<Map<String, Object>> getDailyOrderReport() {
        return adminService.getDailyOrderReport();
    }

    @GetMapping("/reports/monthly")
    public ApiResponse<Map<String, Object>> getMonthlyOrderReport() {
        return adminService.getMonthlyOrderReport();
    }
}
