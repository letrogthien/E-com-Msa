package com.letrogthien.transaction.service;

import com.letrogthien.transaction.common.Status;
import com.letrogthien.transaction.dto.CartDto;
import com.letrogthien.transaction.dto.OrderDto;
import com.letrogthien.transaction.dto.TransactionEventLogDto;

import com.letrogthien.transaction.responses.ApiResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface AdminService {
    
    /**
     * Get all carts in the system.
     * @return
     */
    ApiResponse<List<CartDto>> getAllCarts();


    ApiResponse<Void> forceDeleteCart(UUID cartId);
    
    // Order management 
    /**
     * Get all orders in the system.
     * @return
     */
    ApiResponse<List<OrderDto>> getAllOrders();

    /**
     * Get orders by status.
     * @param status
     * @return
     */
    ApiResponse<List<OrderDto>> getOrdersByStatus(Status status);

    /**
     * Get orders by date range.
     * @param startDate
     * @param endDate
     * @return
     */
    ApiResponse<List<OrderDto>> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Force update the status of an order.
     * @param orderId
     * @param newStatus
     * @return
     */
    ApiResponse<OrderDto> forceUpdateOrderStatus(UUID orderId, Status newStatus);
    
    // Transaction logs

    /**
     * Get all transaction event logs.
     * @return
     */
    ApiResponse<List<TransactionEventLogDto>> getAllTransactionLogs();

    /**
     * Get transaction logs by date range.
     * @param startDate
     * @param endDate
     * @return
     */
    ApiResponse<List<TransactionEventLogDto>> getTransactionLogsByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    
    // Statistics and reports
    /**
     * Get transaction statistics including total transactions, successful transactions, and failed transactions.
     * @return
     */
    ApiResponse<Map<String, Object>> getTransactionStatistics();
    /**
     * Get daily order report.
     * @return
     */
    ApiResponse<Map<String, Object>> getDailyOrderReport();
    /**
     * Get monthly order report.
     * @return
     */
    ApiResponse<Map<String, Object>> getMonthlyOrderReport();
}
