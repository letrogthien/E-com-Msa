package com.letrogthien.transaction.service.impl;

import com.letrogthien.transaction.common.Status;
import com.letrogthien.transaction.dto.CartDto;
import com.letrogthien.transaction.dto.OrderDto;
import com.letrogthien.transaction.dto.TransactionEventLogDto;
import com.letrogthien.transaction.entity.Cart;
import com.letrogthien.transaction.entity.Order;
import com.letrogthien.transaction.entity.TransactionEventLog;
import com.letrogthien.transaction.exceptions.CustomException;
import com.letrogthien.transaction.exceptions.ErrorCode;
import com.letrogthien.transaction.mapper.CartMapper;
import com.letrogthien.transaction.mapper.OrderMapper;
import com.letrogthien.transaction.mapper.TransactionEventLogMapper;
import com.letrogthien.transaction.repositories.CartRepository;
import com.letrogthien.transaction.repositories.OrderRepository;
import com.letrogthien.transaction.repositories.TransactionEventLogRepository;
import com.letrogthien.transaction.responses.ApiResponse;
import com.letrogthien.transaction.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final TransactionEventLogRepository eventLogRepository;
    private final CartMapper cartMapper;
    private final OrderMapper orderMapper;
    private final TransactionEventLogMapper eventLogMapper;

    @Override
    public ApiResponse<List<CartDto>> getAllCarts() {
        try {
            List<Cart> carts = cartRepository.findAll();
            List<CartDto> cartDtos = cartMapper.toCartDtoList(carts);
            
            return ApiResponse.<List<CartDto>>builder()
                    .status(HttpStatus.OK)
                    .message("All carts retrieved successfully")
                    .data(cartDtos)
                    .build();
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ApiResponse<Void> forceDeleteCart(UUID cartId) {
        try {
            cartRepository.deleteById(cartId);
            return ApiResponse.<Void>builder()
                    .status(HttpStatus.OK)
                    .message("Cart forcefully deleted")
                    .build();
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ApiResponse<List<OrderDto>> getAllOrders() {
        try {
            List<Order> orders = orderRepository.findAll();
            List<OrderDto> orderDtos = orderMapper.toOrderDtoList(orders);

            return ApiResponse.<List<OrderDto>>builder()
                    .status(HttpStatus.OK)
                    .message("All orders retrieved successfully")
                    .data(orderDtos)
                    .build();
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ApiResponse<List<OrderDto>> getOrdersByStatus(Status status) {
        try {
            List<Order> orders = orderRepository.findByStatus(status);
            List<OrderDto> orderDtos = orderMapper.toOrderDtoList(orders);

            return ApiResponse.<List<OrderDto>>builder()
                    .status(HttpStatus.OK)
                    .message("Orders with status " + status + " retrieved successfully")
                    .data(orderDtos)
                    .build();
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ApiResponse<List<OrderDto>> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        try {
            List<Order> orders = orderRepository.findByCreatedAtBetween(startDate, endDate);
            List<OrderDto> orderDtos = orderMapper.toOrderDtoList(orders);

            return ApiResponse.<List<OrderDto>>builder()
                    .status(HttpStatus.OK)
                    .message("Orders within date range retrieved successfully")
                    .data(orderDtos)
                    .build();
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ApiResponse<OrderDto> forceUpdateOrderStatus(UUID orderId, Status newStatus) {
        try {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));
            
            order.setOrderStatus(newStatus);
            Order savedOrder = orderRepository.save(order);
            OrderDto orderDto = orderMapper.toOrderDto(savedOrder);

            return ApiResponse.<OrderDto>builder()
                    .status(HttpStatus.OK)
                    .message("Order status forcefully updated")
                    .data(orderDto)
                    .build();
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }               

    @Override
    public ApiResponse<List<TransactionEventLogDto>> getAllTransactionLogs() {
        try {
            List<TransactionEventLog> logs = eventLogRepository.findAll();

            List<TransactionEventLogDto> logDtos = eventLogMapper.toDtoList(logs);
            
            return ApiResponse.<List<TransactionEventLogDto>>builder()
                    .status(HttpStatus.OK)
                    .message("All transaction logs retrieved successfully")
                    .data(logDtos)
                    .build();
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ApiResponse<List<TransactionEventLogDto>> getTransactionLogsByDateRange(
            LocalDateTime startDate, LocalDateTime endDate) {
        try {
            List<TransactionEventLog> logs = eventLogRepository.findByCreatedAtBetween(startDate, endDate);
            List<TransactionEventLogDto> logDtos = eventLogMapper.toDtoList(logs);
            
            return ApiResponse.<List<TransactionEventLogDto>>builder()
                    .status(HttpStatus.OK)
                    .message("Transaction logs within date range retrieved successfully")
                    .data(logDtos)
                    .build();
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public ApiResponse<Map<String, Object>> getTransactionStatistics() {
        try {
            Map<String, Object> statistics = new HashMap<>();
            
            // Add total orders
            long totalOrders = orderRepository.count();
            statistics.put("totalOrders", totalOrders);
            
            // Add orders by status
            Map<Status, Long> ordersByStatus = orderRepository.countByStatus();
            statistics.put("ordersByStatus", ordersByStatus);
            
            // Add total carts
            long totalCarts = cartRepository.count();
            statistics.put("totalCarts", totalCarts);
            
            // Add total transaction logs
            long totalTransactionLogs = eventLogRepository.count();
            statistics.put("totalTransactionLogs", totalTransactionLogs);
            
            return ApiResponse.<Map<String, Object>>builder()
                    .status(HttpStatus.OK)
                    .message("Transaction statistics retrieved successfully")
                    .data(statistics)
                    .build();
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ApiResponse<Map<String, Object>> getDailyOrderReport() {
        try {
            LocalDateTime today = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
            LocalDateTime tomorrow = today.plusDays(1);
            
            Map<String, Object> report = new HashMap<>();
            report.put("totalOrders", orderRepository.countByCreatedAtBetween(today, tomorrow));
            report.put("ordersByStatus", orderRepository.countByStatusAndCreatedAtBetween(today, tomorrow));
            
            return ApiResponse.<Map<String, Object>>builder()
                    .status(HttpStatus.OK)
                    .message("Daily order report retrieved successfully")
                    .data(report)
                    .build();
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ApiResponse<Map<String, Object>> getMonthlyOrderReport() {
        try {
            LocalDateTime firstDayOfMonth = LocalDateTime.now()
                    .withDayOfMonth(1)
                    .withHour(0)
                    .withMinute(0)
                    .withSecond(0);
            LocalDateTime firstDayOfNextMonth = firstDayOfMonth.plusMonths(1);
            
            Map<String, Object> report = new HashMap<>();
            report.put("totalOrders", orderRepository.countByCreatedAtBetween(firstDayOfMonth, firstDayOfNextMonth));
            report.put("ordersByStatus", orderRepository.countByStatusAndCreatedAtBetween(firstDayOfMonth, firstDayOfNextMonth));
            report.put("dailyOrderCounts", orderRepository.getDailyOrderCounts(firstDayOfMonth, firstDayOfNextMonth));
            
            return ApiResponse.<Map<String, Object>>builder()
                    .status(HttpStatus.OK)
                    .message("Monthly order report retrieved successfully")
                    .data(report)
                    .build();
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
